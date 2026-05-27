import { useState, useEffect, useRef } from "react";
import { PROVIDERS, fetchEpisodes, fetchStream } from "./model.js";

// ── localStorage reviews ──
function getReviews(animeId) {
  try { return JSON.parse(localStorage.getItem(`reviews_${animeId}`) || "[]"); } catch { return []; }
}
function saveReviews(animeId, reviews) {
  localStorage.setItem(`reviews_${animeId}`, JSON.stringify(reviews));
}

function StarPicker({ value, onChange, readonly=false }) {
  const [hov, setHov] = useState(0);
  return (
    <div style={{ display:"flex", gap:4 }}>
      {[1,2,3,4,5].map(s => (
        <span key={s} onClick={()=>!readonly&&onChange(s)}
          onMouseEnter={()=>!readonly&&setHov(s)} onMouseLeave={()=>!readonly&&setHov(0)}
          style={{ fontSize:readonly?16:26,cursor:readonly?"default":"pointer",transition:"transform .1s",
            transform:!readonly&&(hov>=s)?"scale(1.2)":"scale(1)",
            color:(hov||value)>=s?"#ffd60a":"#333" }}>★</span>
      ))}
    </div>
  );
}

function ReviewSection({ animeId, user }) {
  const [reviews, setReviews] = useState([]);
  const [myRating, setMyRating] = useState(0);
  const [myText, setMyText]   = useState("");
  const [myName, setMyName]   = useState(user?.email?.split("@")[0]||"");
  const [error, setError]     = useState("");

  useEffect(()=>{ setReviews(getReviews(animeId)); }, [animeId]);

  const avg = reviews.length ? (reviews.reduce((s,r)=>s+r.rating,0)/reviews.length).toFixed(1) : null;

  const submit = () => {
    if (!myName.trim()) { setError("Vui lòng nhập tên!"); return; }
    if (!myRating)      { setError("Vui lòng chọn số sao!"); return; }
    if (!myText.trim()) { setError("Vui lòng nhập nội dung!"); return; }
    const r = { id:Date.now(), name:myName.trim(), rating:myRating, text:myText.trim(),
      date:new Date().toLocaleDateString("vi-VN") };
    const updated = [r,...reviews];
    saveReviews(animeId, updated);
    setReviews(updated); setMyRating(0); setMyText(""); setError("");
  };

  return (
    <div style={{ marginTop:28 }}>
      <div style={{ display:"flex",alignItems:"center",gap:14,marginBottom:18 }}>
        <div style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:22,color:"#e8e8f0" }}>
          ĐÁNH GIÁ <span style={{ color:"#e63946" }}>& BÌNH LUẬN</span>
        </div>
        {avg && <div style={{ display:"flex",alignItems:"center",gap:6,background:"#1a1a26",border:"1px solid #2a2a3e",borderRadius:8,padding:"4px 12px" }}>
          <span style={{ color:"#ffd60a" }}>★</span>
          <span style={{ fontWeight:700,color:"#e8e8f0" }}>{avg}</span>
          <span style={{ color:"#8888aa",fontSize:12 }}>/ 5 · {reviews.length} đánh giá</span>
        </div>}
      </div>

      <div style={{ background:"#12121a",border:"1px solid #2a2a3e",borderRadius:12,padding:18,marginBottom:20 }}>
        <div style={{ fontWeight:700,fontSize:13,color:"#e8e8f0",marginBottom:14 }}>✍️ Viết đánh giá</div>
        <div style={{ display:"grid",gridTemplateColumns:"1fr 1fr",gap:12,marginBottom:12 }}>
          <div>
            <div style={{ fontSize:10,color:"#8888aa",fontWeight:700,letterSpacing:.5,marginBottom:5 }}>TÊN / NICKNAME</div>
            <input value={myName} onChange={e=>setMyName(e.target.value)} placeholder="Tên của bạn..."
              style={{ width:"100%",padding:"9px 12px",background:"#0a0a0f",border:"1px solid #2a2a3e",borderRadius:8,color:"#e8e8f0",fontSize:13,outline:"none",fontFamily:"'Noto Sans JP',sans-serif" }}
              onFocus={e=>e.target.style.borderColor="#e63946"} onBlur={e=>e.target.style.borderColor="#2a2a3e"}/>
          </div>
          <div>
            <div style={{ fontSize:10,color:"#8888aa",fontWeight:700,letterSpacing:.5,marginBottom:5 }}>ĐÁNH GIÁ SAO</div>
            <div style={{ display:"flex",alignItems:"center",gap:8,height:38 }}>
              <StarPicker value={myRating} onChange={setMyRating}/>
              {myRating>0 && <span style={{ color:"#8888aa",fontSize:12 }}>{["","Tệ","Không hay","Bình thường","Hay","Xuất sắc!"][myRating]}</span>}
            </div>
          </div>
        </div>
        <div style={{ fontSize:10,color:"#8888aa",fontWeight:700,letterSpacing:.5,marginBottom:5 }}>NỘI DUNG</div>
        <textarea value={myText} onChange={e=>setMyText(e.target.value)} placeholder="Cảm nhận của bạn..." rows={3}
          style={{ width:"100%",padding:"9px 12px",background:"#0a0a0f",border:"1px solid #2a2a3e",borderRadius:8,color:"#e8e8f0",fontSize:13,outline:"none",fontFamily:"'Noto Sans JP',sans-serif",resize:"vertical",lineHeight:1.6 }}
          onFocus={e=>e.target.style.borderColor="#e63946"} onBlur={e=>e.target.style.borderColor="#2a2a3e"}/>
        {error && <div style={{ color:"#e63946",fontSize:12,marginTop:6 }}>⚠️ {error}</div>}
        <div style={{ display:"flex",justifyContent:"flex-end",marginTop:10 }}>
          <button onClick={submit} style={{ background:"#e63946",border:"none",borderRadius:8,color:"#fff",padding:"9px 22px",fontSize:13,fontWeight:700,cursor:"pointer",fontFamily:"'Noto Sans JP',sans-serif" }}>🎌 Gửi đánh giá</button>
        </div>
      </div>

      {reviews.length===0
        ? <div style={{ textAlign:"center",padding:"32px 0",color:"#8888aa" }}><div style={{ fontSize:40,opacity:.3,marginBottom:10 }}>💬</div><p>Chưa có đánh giá. Hãy là người đầu tiên!</p></div>
        : <div style={{ display:"flex",flexDirection:"column",gap:10 }}>
            {reviews.map(r=>(
              <div key={r.id} style={{ background:"#12121a",border:"1px solid #2a2a3e",borderRadius:10,padding:14 }}>
                <div style={{ display:"flex",alignItems:"flex-start",justifyContent:"space-between",marginBottom:8 }}>
                  <div style={{ display:"flex",alignItems:"center",gap:10 }}>
                    <div style={{ width:34,height:34,borderRadius:"50%",background:"#e63946",display:"flex",alignItems:"center",justifyContent:"center",fontSize:13,fontWeight:800,color:"#fff",flexShrink:0 }}>{r.name[0]?.toUpperCase()}</div>
                    <div><div style={{ fontWeight:700,fontSize:13,color:"#e8e8f0" }}>{r.name}</div><StarPicker value={r.rating} readonly/></div>
                  </div>
                  <span style={{ fontSize:11,color:"#555" }}>{r.date}</span>
                </div>
                <p style={{ fontSize:13,color:"#aaa",lineHeight:1.7,marginLeft:44 }}>{r.text}</p>
              </div>
            ))}
          </div>
      }
    </div>
  );
}

// ── HLS / Video player component ──
function VideoRenderer({ url, token }) {
  const videoRef = useRef(null);
  const hlsRef   = useRef(null);
  const isM3u8   = url?.includes(".m3u8") || url?.includes("m3u8");
  const isMp4    = url?.toLowerCase().includes(".mp4");

  useEffect(() => {
    if (!url || !isM3u8) return;
    const script = document.createElement("script");
    script.src = "https://cdnjs.cloudflare.com/ajax/libs/hls.js/1.4.12/hls.min.js";
    script.onload = () => {
      if (!window.Hls || !videoRef.current) return;
      if (window.Hls.isSupported()) {
        hlsRef.current?.destroy();
        // Gửi Authorization header cho mọi request của hls.js
        const hls = new window.Hls({
          xhrSetup: (xhr, reqUrl) => {
            if (token) xhr.setRequestHeader("Authorization", "Bearer " + token);
          }
        });
        hlsRef.current = hls;
        hls.loadSource(url);
        hls.attachMedia(videoRef.current);
        hls.on(window.Hls.Events.MANIFEST_PARSED, () => videoRef.current?.play().catch(()=>{}));
        hls.on(window.Hls.Events.ERROR, (_, data) => {
          console.error("HLS error:", data.type, data.details, data.response?.code);
        });
      } else if (videoRef.current.canPlayType("application/vnd.apple.mpegurl")) {
        videoRef.current.src = url;
        videoRef.current.play().catch(()=>{});
      }
    };
    if (!window.Hls) document.head.appendChild(script);
    else script.onload();
    return () => hlsRef.current?.destroy();
  }, [url, token]);

  if (!url) return null;

  if (isM3u8 || isMp4) {
    return (
      <video ref={videoRef} controls autoPlay
        src={!isM3u8 ? url : undefined}
        style={{ width:"100%",height:"100%",background:"#000" }}>
        Trình duyệt không hỗ trợ video.
      </video>
    );
  }
  // Default: iframe (embed page)
  return <iframe src={url} style={{ width:"100%",height:"100%",border:"none" }} allowFullScreen allow="autoplay"/>;
}

// ── Main VideoPlayer ──
export default function VideoPlayer({ anime, initEpisode, token, onClose, user }) {
  const [provider, setProvider] = useState("ANIMEVIETSUB");
  const [episodes, setEpisodes] = useState([]);
  const [curEp, setCurEp]       = useState(null);
  const [streamUrl, setStream]  = useState("");
  const [streamInfo, setStreamInfo] = useState(null); // raw response for debugging
  const [loadingEps, setLoadingEps] = useState(true);
  const [loadingStream, setLoadingStream] = useState(false);
  const [offset, setOffset]     = useState(0);
  const [hasMore, setHasMore]   = useState(false);

  const animeId = anime.id || anime.anilistId || anime.animeId;
  const title   = anime.title?.romaji || anime.animeName || "Unknown";
  const totalEps= anime.episodes || anime.totalEps || 12;

  useEffect(() => { loadEps(true); }, [provider]);

  async function loadEps(reset=false) {
    setLoadingEps(true);
    const off = reset ? 0 : offset;
    try {
      const json = await fetchEpisodes(animeId, provider, off, token);
      const eps  = json.data?.episodes || json.data?.results || json.data || [];
      if (reset) setEpisodes(eps); else setEpisodes(p=>[...p,...eps]);
      setOffset(off+eps.length); setHasMore(eps.length>=20);
    } catch {
      const total = totalEps; const start = reset?0:offset;
      const fake = Array.from({length:Math.min(20,total-start)},(_,i)=>({ number:start+i+1,title:`Tập ${start+i+1}`,id:`ep-${animeId}-${start+i+1}`,isFake:true }));
      if (reset) setEpisodes(fake); else setEpisodes(p=>[...p,...fake]);
      setOffset(start+fake.length); setHasMore(start+fake.length<total);
    } finally { setLoadingEps(false); }
  }

  async function playEp(ep) {
    console.log("Episode object from backend:", JSON.stringify(ep));
    setCurEp(ep); setLoadingStream(false); setStream(""); setStreamInfo(null);
    if (ep.isFake) return;
    setLoadingStream(true);
    const epId = ep.episodeId ?? ep.id ?? ep.slug ?? ep.animeEpisodeId
              ?? ep.episodeData ?? ep.number ?? ep.episode ?? ep.episodeNumber;
    console.log("Episode ID sent to stream:", epId);
    try {
      const json = await fetchStream(epId, provider, token);
      setStreamInfo(json); // lưu raw để debug
      // Thử tất cả các field có thể chứa URL
      const url = json.data?.url
        || json.data?.sources?.[0]?.url
        || json.data?.sources?.[0]?.file
        || json.data?.m3u8
        || json.data?.link
        || json.data?.stream
        || json.url
        || json.link
        || json.stream
        || "";
      setStream(url);
    } catch(e) { console.error("Stream error:", e); setStream(""); }
    finally { setLoadingStream(false); }
  }

  return (
    <div style={{ position:"fixed",inset:0,zIndex:600,background:"#0a0a0f",display:"flex",flexDirection:"column" }}>
      {/* Header */}
      <div style={{ display:"flex",alignItems:"center",gap:16,padding:"14px 24px",borderBottom:"1px solid #2a2a3e",flexShrink:0,background:"rgba(10,10,15,.97)" }}>
        <button onClick={onClose} style={{ background:"#1a1a26",border:"1px solid #2a2a3e",borderRadius:8,color:"#e8e8f0",padding:"7px 16px",cursor:"pointer",fontSize:14,fontFamily:"'Noto Sans JP',sans-serif",display:"flex",alignItems:"center",gap:6 }}
          onMouseEnter={e=>{e.currentTarget.style.borderColor="#e63946";e.currentTarget.style.color="#e63946";}}
          onMouseLeave={e=>{e.currentTarget.style.borderColor="#2a2a3e";e.currentTarget.style.color="#e8e8f0";}}>
          ← Quay lại
        </button>
        <h2 style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:22,color:"#e8e8f0",flex:1,overflow:"hidden",textOverflow:"ellipsis",whiteSpace:"nowrap" }}>{title}</h2>
        {curEp && <span style={{ color:"#8888aa",fontSize:13 }}>Tập {curEp.number||curEp.episode}</span>}
      </div>

      <div style={{ display:"grid",gridTemplateColumns:"1fr 320px",flex:1,overflow:"hidden" }}>
        {/* Cột trái: video + providers + reviews */}
        <div style={{ overflowY:"auto",padding:20 }}>
          {/* Video box */}
          <div style={{ background:"#000",borderRadius:12,overflow:"hidden",aspectRatio:"16/9",display:"flex",alignItems:"center",justifyContent:"center" }}>
            {loadingStream ? (
              <div style={{ textAlign:"center",color:"#8888aa" }}>
                <div style={{ width:44,height:44,border:"3px solid #2a2a3e",borderTopColor:"#e63946",borderRadius:"50%",animation:"spin .8s linear infinite",margin:"0 auto 10px" }}/>
                <p style={{ fontSize:13 }}>Đang tải stream...</p>
              </div>
            ) : streamUrl ? (
              <VideoRenderer url={streamUrl} token={token}/>
            ) : curEp ? (
              <div style={{ textAlign:"center",color:"#8888aa",padding:"0 32px" }}>
                <div style={{ fontSize:44,marginBottom:10,opacity:.3 }}>⚠️</div>
                {curEp.isFake ? (
                  <>
                    <p style={{ fontSize:14,color:"#e8e8f0",marginBottom:8 }}>Backend lỗi 500 với provider này</p>
                    <p style={{ fontSize:12,lineHeight:1.8,color:"#8888aa" }}>
                      Server không lấy được tập phim từ <strong style={{ color:"#ffd60a" }}>{provider}</strong>.<br/>
                      Thử đổi nguồn phim khác hoặc kiểm tra log Spring Boot.
                    </p>
                  </>
                ) : (
                  <>
                    <p style={{ fontSize:13,marginBottom:8 }}>Backend không trả về URL stream.</p>
                    {streamInfo && <details style={{ fontSize:11,textAlign:"left",background:"#111",padding:8,borderRadius:6,marginTop:8 }}>
                      <summary style={{ cursor:"pointer",color:"#888" }}>Xem raw response</summary>
                      <pre style={{ overflow:"auto",maxHeight:150,marginTop:6,color:"#aaa" }}>{JSON.stringify(streamInfo,null,2)}</pre>
                    </details>}
                  </>
                )}
              </div>
            ) : (
              <div style={{ textAlign:"center",color:"#8888aa" }}>
                <div style={{ fontSize:56,marginBottom:10,opacity:.3 }}>🎬</div>
                <p>Chọn tập để xem</p>
              </div>
            )}
          </div>

          {/* Providers */}
          <div style={{ marginTop:14,marginBottom:4 }}>
            <div style={{ fontSize:10,fontWeight:700,letterSpacing:1.5,color:"#8888aa",marginBottom:8,textTransform:"uppercase" }}>Nguồn phim</div>
            <div style={{ display:"flex",gap:8,flexWrap:"wrap" }}>
              {PROVIDERS.map(p=>(
                <button key={p} onClick={()=>setProvider(p)} style={{ padding:"6px 14px",borderRadius:6,border:"1px solid",borderColor:provider===p?"#e63946":"#2a2a3e",background:provider===p?"#e63946":"#12121a",color:provider===p?"#fff":"#8888aa",fontSize:12,cursor:"pointer",fontFamily:"'Noto Sans JP',sans-serif",fontWeight:provider===p?700:400,transition:"all .15s" }}>{p}</button>
              ))}
            </div>
          </div>

          <ReviewSection animeId={animeId} user={user}/>
        </div>

        {/* Cột phải: danh sách tập */}
        <div style={{ borderLeft:"1px solid #2a2a3e",display:"flex",flexDirection:"column",overflow:"hidden" }}>
          <div style={{ padding:"14px 16px",borderBottom:"1px solid #2a2a3e",display:"flex",justifyContent:"space-between",alignItems:"center",flexShrink:0 }}>
            <span style={{ fontWeight:700,fontSize:14,color:"#e8e8f0" }}>Tập phim</span>
            <span style={{ fontSize:12,color:"#8888aa" }}>{episodes.length} tập</span>
          </div>
          <div style={{ flex:1,overflowY:"auto" }}>
            {loadingEps
              ? <div style={{ padding:24,textAlign:"center" }}><div style={{ width:32,height:32,border:"3px solid #2a2a3e",borderTopColor:"#e63946",borderRadius:"50%",animation:"spin .8s linear infinite",margin:"0 auto" }}/></div>
              : episodes.map((ep,i)=>(
                <div key={ep.id||i} onClick={()=>playEp(ep)}
                  style={{ display:"flex",alignItems:"center",gap:12,padding:"10px 16px",cursor:"pointer",borderBottom:"1px solid #2a2a3e",background:curEp?.id===ep.id?"rgba(230,57,70,.15)":"",transition:"background .15s" }}
                  onMouseEnter={e=>{ if(curEp?.id!==ep.id) e.currentTarget.style.background="#1a1a26"; }}
                  onMouseLeave={e=>{ if(curEp?.id!==ep.id) e.currentTarget.style.background=""; }}>
                  <div style={{ width:36,height:36,borderRadius:8,background:curEp?.id===ep.id?"#e63946":"#1a1a26",display:"flex",alignItems:"center",justifyContent:"center",fontSize:13,fontWeight:700,color:curEp?.id===ep.id?"#fff":"#e8e8f0",flexShrink:0 }}>{ep.number||ep.episode||i+1}</div>
                  <div style={{ flex:1,minWidth:0 }}>
                    <div style={{ fontSize:13,overflow:"hidden",textOverflow:"ellipsis",whiteSpace:"nowrap",color:"#e8e8f0" }}>{ep.title||ep.name||`Tập ${ep.number||i+1}`}</div>
                    <div style={{ fontSize:11,color:"#8888aa",marginTop:2 }}>Tập {ep.number||ep.episode||i+1}</div>
                  </div>
                </div>
              ))
            }
            {hasMore && <button onClick={()=>loadEps(false)} style={{ width:"100%",padding:12,border:"none",background:"#1a1a26",color:"#e63946",fontSize:13,fontWeight:700,cursor:"pointer",borderTop:"1px solid #2a2a3e",fontFamily:"'Noto Sans JP',sans-serif" }}>↓ Tải thêm tập</button>}
          </div>
        </div>
      </div>
      <style>{`@keyframes spin{to{transform:rotate(360deg)}}`}</style>
    </div>
  );
}