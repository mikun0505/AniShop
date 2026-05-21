import { useState, useEffect } from "react";
import Ico from "./Ico";
import { IC, PROVIDERS, ANIME_COLORS, ANIME_EMOJI, API_BASE } from "../constants";

export default function VideoPlayer({ anime, initEpisode, onClose }) {
  if (!anime) return null;
  const color = ANIME_COLORS[anime.animeId % ANIME_COLORS.length];
  const emoji = ANIME_EMOJI[anime.animeId % ANIME_EMOJI.length];

  const [provider,     setProvider]     = useState("ANIMEVIETSUB");
  const [episodes,     setEpisodes]     = useState([]);
  const [currentEp,    setCurrentEp]    = useState(null);
  const [streamUrl,    setStreamUrl]    = useState(null);
  const [servers,      setServers]      = useState([]);
  const [activeServer, setActiveServer] = useState(null);
  const [loadingEps,   setLoadingEps]   = useState(false);
  const [loadingStream,setLoadingStream]= useState(false);
  const [error,        setError]        = useState(null);

  useEffect(() => {
    if (!anime.anilistId) return;
    setLoadingEps(true);
    setEpisodes([]);
    setStreamUrl(null);
    setError(null);
    fetch(`${API_BASE}/api/animes/${anime.anilistId}/episodes?provider=${provider}&offset=0`)
      .then((r) => r.json())
      .then((data) => {
        const eps = data.data?.episodes || data.data?.results || data.data || [];
        setEpisodes(Array.isArray(eps) ? eps : []);
        const target = Array.isArray(eps) && eps.length > 0
          ? eps[Math.max(0, (initEpisode || 1) - 1)] || eps[0]
          : null;
        if (target) loadStream(target, provider);
      })
      .catch(() => setError("Không lấy được danh sách tập. Kiểm tra backend!"))
      .finally(() => setLoadingEps(false));
  }, [anime.anilistId, provider]);

  const loadStream = async (ep, prov = provider) => {
    setCurrentEp(ep);
    setStreamUrl(null);
    setServers([]);
    setActiveServer(null);
    setError(null);
    setLoadingStream(true);
    try {
      const epData = ep.id || ep.episodeId || JSON.stringify(ep);
      const res = await fetch(`${API_BASE}/api/animes/stream?episodes=${encodeURIComponent(epData)}&provider=${prov}`);
      const data = await res.json();
      if (!res.ok) throw new Error(data.message || "Lỗi stream");
      const d = data.data || {};
      const srcs = d.sources || d.videos || d.servers || [];
      const direct = d.url || d.embedUrl || d.iframe;
      if (srcs.length > 0) {
        setServers(srcs);
        pickServer(srcs[0]);
      } else if (direct) {
        setStreamUrl(direct);
      } else {
        setError("Nguồn này chưa có link stream cho tập này.");
      }
    } catch (e) {
      setError(e.message || "Không lấy được link stream.");
    } finally {
      setLoadingStream(false);
    }
  };

  const pickServer = (s) => {
    setActiveServer(s);
    const url = s.url || s.file || s.embedUrl || s.link;
    if (url) setStreamUrl(url);
  };

  const epNum = (ep, i) => ep?.number || ep?.episodeNumber || i + 1;

  return (
    <div style={{
      position: "fixed", inset: 0, zIndex: 3000, background: "rgba(0,0,0,.97)",
      backdropFilter: "blur(12px)", display: "flex", flexDirection: "column",
      overflow: "hidden", animation: "modalIn .25s ease",
    }}>
      {/* Header */}
      <div style={{
        display: "flex", alignItems: "center", gap: 14, padding: "12px 20px",
        borderBottom: "1px solid #1e2a4a", background: "#0f1219", flexShrink: 0,
      }}>
        <span style={{ fontSize: 24 }}>{emoji}</span>
        <div style={{ flex: 1, minWidth: 0 }}>
          <div style={{ fontFamily: "var(--fd)", fontSize: 20, letterSpacing: 1, lineHeight: 1.1 }}>{anime.animeName}</div>
          <div style={{ fontSize: 12, color: "var(--mut)", marginTop: 2 }}>
            {currentEp ? `Tập ${epNum(currentEp, episodes.indexOf(currentEp))}` : "Đang tải..."} · {provider}
          </div>
        </div>
        {/* Provider tabs */}
        <div style={{ display: "flex", gap: 6 }}>
          {PROVIDERS.map((p) => (
            <button key={p} onClick={() => setProvider(p)} style={{
              padding: "5px 12px", borderRadius: 8, border: "1px solid",
              borderColor: provider === p ? color : "#1e2a4a",
              background: provider === p ? color + "33" : "none",
              color: provider === p ? "#fff" : "var(--mut)",
              fontSize: 11, fontWeight: 800, cursor: "pointer", fontFamily: "var(--fb)", transition: "all .15s",
            }}>
              {p === "ANIMEVIETSUB" ? "AVS" : p === "ANIMEVN" ? "AVN" : "NNY"}
            </button>
          ))}
        </div>
        <button onClick={onClose} style={{
          width: 38, height: 38, borderRadius: 10, flexShrink: 0,
          background: "rgba(255,255,255,.07)", border: "1px solid rgba(255,255,255,.12)",
          color: "#fff", cursor: "pointer", display: "flex", alignItems: "center", justifyContent: "center",
        }}>
          <Ico d={IC.close} size={17} />
        </button>
      </div>

      {/* Body */}
      <div style={{ flex: 1, display: "flex", overflow: "hidden" }}>
        {/* Player */}
        <div style={{ flex: 1, display: "flex", flexDirection: "column", overflow: "hidden" }}>
          <div style={{ position: "relative", flex: 1, background: "#000", minHeight: 0 }}>
            {loadingStream && (
              <div style={{ position: "absolute", inset: 0, display: "flex", alignItems: "center", justifyContent: "center", flexDirection: "column", gap: 12, background: "#000a" }}>
                <div style={{ width: 40, height: 40, border: "3px solid #1e2a4a", borderTopColor: color, borderRadius: "50%", animation: "spin .8s linear infinite" }} />
                <div style={{ fontSize: 13, color: "var(--mut)" }}>Đang tải stream...</div>
              </div>
            )}
            {error && !loadingStream && (
              <div style={{ position: "absolute", inset: 0, display: "flex", alignItems: "center", justifyContent: "center", flexDirection: "column", gap: 12, padding: 24, textAlign: "center" }}>
                <span style={{ fontSize: 40 }}>⚠️</span>
                <div style={{ fontSize: 14, color: "var(--mut)", lineHeight: 1.6, maxWidth: 380 }}>{error}</div>
                <button className="btn-g" onClick={() => currentEp && loadStream(currentEp)}>Thử lại</button>
              </div>
            )}
            {!error && !loadingStream && !streamUrl && !loadingEps && (
              <div style={{ position: "absolute", inset: 0, display: "flex", alignItems: "center", justifyContent: "center", flexDirection: "column", gap: 10 }}>
                <span style={{ fontSize: 60, opacity: 0.2 }}>{emoji}</span>
                <div style={{ fontSize: 13, color: "var(--mut)" }}>Chọn tập để xem</div>
              </div>
            )}
            {streamUrl && (
              <iframe key={streamUrl} src={streamUrl}
                style={{ width: "100%", height: "100%", border: "none" }}
                allowFullScreen allow="autoplay; fullscreen" />
            )}
          </div>

          {/* Server tabs */}
          {servers.length > 0 && (
            <div style={{ padding: "10px 16px", borderTop: "1px solid #1e2a4a", display: "flex", alignItems: "center", gap: 8, flexWrap: "wrap", background: "#0f1219", flexShrink: 0 }}>
              <span style={{ fontSize: 11, color: "var(--mut)", fontWeight: 800, letterSpacing: 0.5 }}>SERVER:</span>
              {servers.map((s, i) => (
                <button key={i} onClick={() => pickServer(s)} style={{
                  padding: "5px 14px", borderRadius: 6, border: "1px solid",
                  borderColor: activeServer === s ? color : "#1e2a4a",
                  background: activeServer === s ? color : "var(--card)",
                  color: activeServer === s ? "#fff" : "var(--mut)",
                  fontSize: 12, cursor: "pointer", fontFamily: "var(--fb)", fontWeight: 700, transition: "all .15s",
                }}>
                  {s.name || s.quality || s.label || `Server ${i + 1}`}
                </button>
              ))}
            </div>
          )}
        </div>

        {/* Episode list */}
        <div style={{ width: 220, borderLeft: "1px solid #1e2a4a", background: "#0d1020", display: "flex", flexDirection: "column", flexShrink: 0, overflow: "hidden" }}>
          <div style={{ padding: "12px 14px", borderBottom: "1px solid #1e2a4a", flexShrink: 0, fontFamily: "var(--fd)", fontSize: 13, letterSpacing: 1, color: "var(--mut)" }}>
            DANH SÁCH TẬP {episodes.length > 0 && <span style={{ color }}> ({episodes.length})</span>}
          </div>
          <div style={{ flex: 1, overflowY: "auto" }}>
            {loadingEps && (
              <div style={{ padding: 30, textAlign: "center", color: "var(--mut)", fontSize: 13 }}>
                <div style={{ width: 28, height: 28, border: `2px solid #1e2a4a`, borderTopColor: color, borderRadius: "50%", animation: "spin .8s linear infinite", margin: "0 auto 10px" }} />
                Đang tải...
              </div>
            )}
            {!loadingEps && episodes.length === 0 && (
              <div style={{ padding: 24, textAlign: "center", color: "var(--mut)", fontSize: 13, lineHeight: 1.7 }}>
                Không có tập nào<br />từ {provider}
              </div>
            )}
            {episodes.map((ep, i) => {
              const num = epNum(ep, i);
              const isActive = currentEp === ep;
              return (
                <div key={i} onClick={() => loadStream(ep)} style={{
                  padding: "11px 14px", cursor: "pointer", borderBottom: "1px solid #1a2035",
                  background: isActive ? color + "22" : "transparent",
                  borderLeft: isActive ? `3px solid ${color}` : "3px solid transparent",
                  transition: "background .12s", display: "flex", alignItems: "center", gap: 10,
                }}>
                  <span style={{ fontFamily: "var(--fd)", fontSize: 16, color: isActive ? color : "var(--mut)", minWidth: 28, textAlign: "center" }}>{num}</span>
                  <div style={{ flex: 1, minWidth: 0 }}>
                    <div style={{ fontSize: 12, fontWeight: 700, color: isActive ? "#fff" : "var(--txt)", whiteSpace: "nowrap", overflow: "hidden", textOverflow: "ellipsis" }}>
                      {ep.title || ep.name || `Tập ${num}`}
                    </div>
                    {ep.type && <div style={{ fontSize: 10, color: "var(--mut)", marginTop: 2 }}>{ep.type}</div>}
                  </div>
                  {isActive && <Ico d={IC.play} size={12} />}
                </div>
              );
            })}
          </div>
        </div>
      </div>
      <style>{`@keyframes spin { to { transform: rotate(360deg) } }`}</style>
    </div>
  );
}
