import { useState, useEffect } from "react";
import AnimeCover from "../components/AnimeCover";
import Ico from "../components/Ico";
import { IC, ANIME_COLORS, ANIME_EMOJI } from "../constants";

export default function HomePage({ animes, filteredAnimes, onWatch, onDetail }) {
  const [featured, setFeatured] = useState(0);

  useEffect(() => {
    const t = setInterval(() => setFeatured((f) => (f + 1) % Math.min(animes.length, 5)), 5000);
    return () => clearInterval(t);
  }, [animes.length]);

  const featuredAnime = animes[featured] || animes[0];
  const featColor = ANIME_COLORS[(featuredAnime?.animeId || 0) % ANIME_COLORS.length];
  const featEmoji = ANIME_EMOJI[(featuredAnime?.animeId || 0) % ANIME_EMOJI.length];

  return (
    <>
      {featuredAnime && (
        <div className="hero" style={{ background: `linear-gradient(135deg,${featColor}55 0%,#0b0d17 60%,${featColor}1a 100%)` }}>
          <div style={{ position: "absolute", inset: 0, opacity: 0.035, backgroundImage: "url(\"data:image/svg+xml,%3Csvg viewBox='0 0 200 200' xmlns='http://www.w3.org/2000/svg'%3E%3Cfilter id='n'%3E%3CfeTurbulence type='fractalNoise' baseFrequency='.75' numOctaves='4'/%3E%3C/filter%3E%3Crect width='100%25' height='100%25' filter='url(%23n)'/%3E%3C/svg%3E\")" }} />
          <div className="hero-c">
            <div className="h-genres">
              {featuredAnime.animeGenres?.map((g) => (
                <span key={g.genreId} className="h-genre">{g.genreName}</span>
              ))}
            </div>
            <h1 className="h-title">{featuredAnime.animeName}</h1>
            <p className="h-desc">{featuredAnime.synopsis}</p>
            <div className="h-meta">
              <span className="h-score"><Ico d={IC.star} size={17} /> {featuredAnime.score}</span>
              <span style={{ fontSize: 12, color: "rgba(255,255,255,.55)" }}>{featuredAnime.episodes} tập</span>
              <span style={{ fontSize: 11, padding: "3px 10px", borderRadius: 99, fontWeight: 700, background: "rgba(255,255,255,.14)", backdropFilter: "blur(4px)" }}>
                {featuredAnime.status === "RELEASING" ? "🔴 Đang chiếu" : "✅ Hoàn thành"}
              </span>
              <span style={{ fontSize: 12, color: "rgba(255,255,255,.45)" }}>👁 {featuredAnime.viewCount?.toLocaleString()}</span>
            </div>
            <div>
              <button className="btn-w" onClick={() => onWatch(featuredAnime, 1)}>
                <Ico d={IC.play} size={15} /> Xem ngay
              </button>
              <button className="btn-ol" onClick={() => onDetail(featuredAnime)}>
                <Ico d={IC.eye} size={15} /> Chi tiết
              </button>
            </div>
          </div>
          <div className="h-deco">{featEmoji}</div>
          <div className="h-dots">
            {animes.slice(0, 5).map((_, i) => (
              <div key={i} className={`h-dot${i === featured ? " on" : ""}`} onClick={() => setFeatured(i)} />
            ))}
          </div>
        </div>
      )}

      <div className="sh">
        <div className="st">TẤT CẢ <em>ANIME</em></div>
        <span style={{ fontSize: 13, color: "var(--mut)" }}>{filteredAnimes.length} bộ</span>
      </div>

      {filteredAnimes.length === 0 ? (
        <div className="empty">
          <span className="em">🔍</span>
          <h3>Không tìm thấy</h3>
          <p>Thử từ khóa khác nhé!</p>
        </div>
      ) : (
        <div className="ag">
          {filteredAnimes.map((a, i) => (
            <div key={a.animeId} className="ai" style={{ animationDelay: `${i * 0.04}s` }}>
              <AnimeCover anime={a} onClick={() => onDetail(a)} />
              <div className="an">{a.animeName}</div>
              <div className="asc">★ {a.score}</div>
              <div className="am">{a.episodes} tập · {a.status === "RELEASING" ? "Đang chiếu" : "Hoàn thành"}</div>
            </div>
          ))}
        </div>
      )}
    </>
  );
}
