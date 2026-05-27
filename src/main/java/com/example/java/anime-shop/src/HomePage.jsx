import { useState, useEffect, useRef } from "react";
import { fetchTrending } from "./model.js";

const ANILIST = "https://graphql.anilist.co";

async function fetchAllAnime(page = 1, perPage = 20) {
  const query = `query($p:Int $n:Int){Page(page:$p perPage:$n){pageInfo{total lastPage}media(type:ANIME sort:TRENDING_DESC status_in:[RELEASING,FINISHED]){id title{romaji native}coverImage{extraLarge large}averageScore episodes status nextAiringEpisode{episode}}}}`;
  const res = await fetch(ANILIST, { method:"POST", headers:{"Content-Type":"application/json"}, body:JSON.stringify({query,variables:{p:page,n:perPage}}) });
  const json = await res.json();
  return json.data?.Page || { media:[], pageInfo:{} };
}

async function searchAniListFull(query, page = 1, perPage = 20) {
  const q = `query($s:String $p:Int $n:Int){Page(page:$p perPage:$n){pageInfo{total lastPage}media(type:ANIME search:$s sort:POPULARITY_DESC){id title{romaji native}coverImage{extraLarge large}averageScore episodes status nextAiringEpisode{episode}}}}`;
  const res = await fetch(ANILIST, { method:"POST", headers:{"Content-Type":"application/json"}, body:JSON.stringify({query:q,variables:{s:query,p:page,n:perPage}}) });
  const json = await res.json();
  return json.data?.Page || { media:[], pageInfo:{} };
}

// ── Card ──
function AnimeCard({ a, onClick }) {
  const title = a.title?.romaji || a.animeName || "Unknown";
  const score = a.averageScore ? (a.averageScore/10).toFixed(1) : a.score || "—";
  const eps   = a.episodes || (a.nextAiringEpisode?.episode - 1) || "?";
  const img   = a.coverImage?.extraLarge || a.coverImage?.large || a.coverImg || "";
  const status= a.status==="RELEASING" ? "▶ Đang chiếu" : a.status==="FINISHED" ? "✓ Hoàn thành" : "";
  const [hov, setHov] = useState(false);
  return (
    <div onClick={onClick} onMouseEnter={()=>setHov(true)} onMouseLeave={()=>setHov(false)}
      style={{ cursor:"pointer",borderRadius:10,overflow:"hidden",background:"#12121a",border:"1px solid #2a2a3e",transition:"transform .2s,box-shadow .2s",position:"relative",transform:hov?"translateY(-4px)":"",boxShadow:hov?"0 12px 32px rgba(0,0,0,.5)":"" }}>
      {status && <div style={{ position:"absolute",top:8,right:8,background:"rgba(0,0,0,.8)",color:"#ffd60a",fontSize:10,fontWeight:700,padding:"2px 6px",borderRadius:4,zIndex:1 }}>{status}</div>}
      {img
        ? <img src={img} alt={title} loading="lazy" style={{ width:"100%",aspectRatio:"2/3",objectFit:"cover",display:"block" }} onError={e=>e.target.style.display="none"}/>
        : <div style={{ width:"100%",aspectRatio:"2/3",background:"#1a1a26",display:"flex",alignItems:"center",justifyContent:"center",fontSize:40 }}>🎌</div>
      }
      <div style={{ position:"absolute",inset:0,background:"linear-gradient(to top,rgba(230,57,70,.7) 0%,transparent 50%)",opacity:hov?1:0,transition:"opacity .2s",display:"flex",alignItems:"center",justifyContent:"center" }}>
        <div style={{ width:44,height:44,borderRadius:"50%",background:"#fff",display:"flex",alignItems:"center",justifyContent:"center",fontSize:18,marginTop:-20 }}>▶</div>
      </div>
      <div style={{ padding:"10px 12px" }}>
        <div style={{ fontSize:13,fontWeight:700,overflow:"hidden",textOverflow:"ellipsis",whiteSpace:"nowrap",color:"#e8e8f0" }} title={title}>{title}</div>
        <div style={{ display:"flex",justifyContent:"space-between",marginTop:4 }}>
          <span style={{ fontSize:12,color:"#ffd60a",fontWeight:700 }}>★ {score}</span>
          <span style={{ fontSize:11,color:"#8888aa" }}>{eps} tập</span>
        </div>
      </div>
    </div>
  );
}

function Skeleton() {
  return (
    <div style={{ background:"#1a1a26",borderRadius:10,overflow:"hidden",border:"1px solid #2a2a3e",animation:"shimmer 1.5s infinite" }}>
      <div style={{ width:"100%",aspectRatio:"2/3",background:"#2a2a3e" }}/>
      <div style={{ margin:12,height:12,background:"#2a2a3e",borderRadius:4 }}/>
      <div style={{ margin:"8px 12px 12px",height:10,width:"60%",background:"#2a2a3e",borderRadius:4 }}/>
    </div>
  );
}

function Pagination({ page, totalPages, onChange }) {
  if (totalPages <= 1) return null;
  const getPages = () => {
    if (totalPages <= 7) return Array.from({length:totalPages},(_,i)=>i+1);
    if (page <= 4) return [1,2,3,4,5,"...",totalPages];
    if (page >= totalPages-3) return [1,"...",totalPages-4,totalPages-3,totalPages-2,totalPages-1,totalPages];
    return [1,"...",page-1,page,page+1,"...",totalPages];
  };
  const btn = (key,label,onClick,active=false,disabled=false) => (
    <button key={key} onClick={onClick} disabled={disabled} style={{
      minWidth:36,height:36,borderRadius:8,padding:"0 10px",
      border:active?"none":"1px solid #2a2a3e",
      background:active?"#e63946":"transparent",
      color:active?"#fff":disabled?"#333":"#8888aa",
      fontWeight:active?700:400,fontSize:14,cursor:disabled?"not-allowed":"pointer",
      fontFamily:"'Noto Sans JP',sans-serif",transition:"all .15s"
    }}
      onMouseEnter={e=>{ if(!active&&!disabled){e.currentTarget.style.borderColor="#e63946";e.currentTarget.style.color="#e63946";}}}
      onMouseLeave={e=>{ if(!active&&!disabled){e.currentTarget.style.borderColor="#2a2a3e";e.currentTarget.style.color="#8888aa";}}}
    >{label}</button>
  );
  return (
    <div style={{ display:"flex",alignItems:"center",justifyContent:"center",gap:6,marginTop:32 }}>
      {btn("prev","‹",()=>onChange(page-1),false,page===1)}
      {getPages().map((p,i)=>p==="..."
        ? <span key={`d${i}`} style={{ color:"#444",padding:"0 4px" }}>…</span>
        : btn(`p${p}`,p,()=>onChange(p),p===page)
      )}
      {btn("next","›",()=>onChange(page+1),false,page===totalPages)}
    </div>
  );
}

// ── Trang browse / tìm kiếm ──
function BrowsePage({ searchQuery, onDetail, onBack }) {
  const [animes, setAnimes]     = useState([]);
  const [page, setPage]         = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [total, setTotal]       = useState(0);
  const [loading, setLoading]   = useState(true);
  const PER_PAGE = 20;
  const isSearch = !!(searchQuery && searchQuery.trim());

  const loadPage = (p, q) => {
    setLoading(true);
    setAnimes([]);
    const fetcher = (q && q.trim())
      ? searchAniListFull(q, p, PER_PAGE)
      : fetchAllAnime(p, PER_PAGE);
    fetcher.then(data => {
      setAnimes(data.media || []);
      setTotalPages(data.pageInfo?.lastPage || 1);
      setTotal(data.pageInfo?.total || 0);
      setLoading(false);
      window.scrollTo({ top: 0, behavior: "smooth" });
    }).catch(() => setLoading(false));
  };

  useEffect(() => {
    setPage(1);
    loadPage(1, searchQuery);
  }, [searchQuery]);

  const handlePageChange = (p) => {
    setPage(p);
    loadPage(p, searchQuery);
  };

  return (
    <div style={{ padding:"32px 48px" }}>
      <div style={{ display:"flex",alignItems:"center",gap:16,marginBottom:24 }}>
        <button onClick={onBack} style={{ display:"flex",alignItems:"center",gap:6,background:"#1a1a26",border:"1px solid #2a2a3e",color:"#e8e8f0",borderRadius:8,padding:"8px 16px",fontSize:14,cursor:"pointer",fontFamily:"'Noto Sans JP',sans-serif",transition:"all .15s" }}
          onMouseEnter={e=>{e.currentTarget.style.borderColor="#e63946";e.currentTarget.style.color="#e63946";}}
          onMouseLeave={e=>{e.currentTarget.style.borderColor="#2a2a3e";e.currentTarget.style.color="#e8e8f0";}}>
          ← Quay lại
        </button>
        <div>
          <div style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:32,letterSpacing:1,color:"#e8e8f0",lineHeight:1 }}>
            {isSearch
              ? <>KẾT QUẢ <span style={{ color:"#e63946" }}>"{searchQuery}"</span></>
              : <>TẤT CẢ <span style={{ color:"#e63946" }}>ANIME</span></>
            }
          </div>
          {!loading && (
            <div style={{ fontSize:13,color:"#8888aa",marginTop:4 }}>
              {total.toLocaleString()} bộ · Trang {page}/{totalPages}
            </div>
          )}
        </div>
      </div>

      <div style={{ display:"grid",gridTemplateColumns:"repeat(auto-fill,minmax(160px,1fr))",gap:16 }}>
        {loading
          ? Array(PER_PAGE).fill(0).map((_,i)=><Skeleton key={i}/>)
          : animes.length === 0
            ? <div style={{ gridColumn:"1/-1",textAlign:"center",padding:"80px 0",color:"#8888aa" }}>
                <div style={{ fontSize:64,marginBottom:16,opacity:.4 }}>🔍</div>
                <h3 style={{ color:"#e8e8f0",marginBottom:8 }}>Không tìm thấy</h3>
                <p>Thử từ khóa khác</p>
              </div>
            : animes.map((a,i)=><AnimeCard key={a.id||i} a={a} onClick={()=>onDetail(a)}/>)
        }
      </div>

      {!loading && animes.length > 0 && (
        <Pagination page={page} totalPages={totalPages} onChange={handlePageChange}/>
      )}
    </div>
  );
}

// ── Trang chủ ──
export default function HomePage({ onWatch, onDetail, onClearSearch, searchQuery }) {
  const [trending, setTrending] = useState([]);
  const [newAnimes, setNew]     = useState([]);
  const [loading, setLoading]   = useState(true);
  const [heroIdx, setHeroIdx]   = useState(0);
  const [heroes, setHeroes]     = useState([]);
  const [showAll, setShowAll]   = useState(false);
  const timer = useRef(null);

  useEffect(() => {
    (async () => {
      try {
        const [t,n] = await Promise.all([fetchTrending(1), fetchTrending(2)]);
        setTrending(t); setNew(n);
        setHeroes(t.filter(a=>a.bannerImage||a.coverImage?.extraLarge).slice(0,5));
      } finally { setLoading(false); }
    })();
  }, []);

  useEffect(() => {
    if (!heroes.length) return;
    clearInterval(timer.current);
    timer.current = setInterval(()=>setHeroIdx(i=>(i+1)%heroes.length), 5000);
    return ()=>clearInterval(timer.current);
  }, [heroes]);

  // ── Trang tìm kiếm ──
  if (searchQuery && searchQuery.trim()) {
    return (
      <>
        <style>{`@keyframes shimmer{0%,100%{opacity:.6}50%{opacity:1}}`}</style>
        <BrowsePage
          searchQuery={searchQuery}
          onDetail={onDetail}
          onBack={() => { onClearSearch?.(); window.scrollTo({top:0}); }}
        />
      </>
    );
  }

  // ── Trang xem tất cả anime ──
  if (showAll) {
    return (
      <>
        <style>{`@keyframes shimmer{0%,100%{opacity:.6}50%{opacity:1}}`}</style>
        <BrowsePage
          searchQuery=""
          onDetail={onDetail}
          onBack={() => { setShowAll(false); window.scrollTo({top:0}); }}
        />
      </>
    );
  }

  const hero = heroes[heroIdx];
  const grid = (list) => (
    <div style={{ display:"grid",gridTemplateColumns:"repeat(auto-fill,minmax(160px,1fr))",gap:16 }}>
      {loading ? Array(12).fill(0).map((_,i)=><Skeleton key={i}/>)
               : list.map((a,i)=><AnimeCard key={a.id||i} a={a} onClick={()=>onDetail(a)}/>)}
    </div>
  );

  return (
    <>
      <style>{`@keyframes shimmer{0%,100%{opacity:.6}50%{opacity:1}}`}</style>

      {/* Hero */}
      {hero && (
        <div style={{ position:"relative",height:420,overflow:"hidden",background:"#12121a" }}>
          <div style={{ position:"absolute",inset:0,backgroundImage:`url(${hero.bannerImage||hero.coverImage?.extraLarge||""})`,backgroundSize:"cover",backgroundPosition:"center",filter:"brightness(.35) blur(2px)",transition:"all .8s" }}/>
          <div style={{ position:"absolute",inset:0,display:"flex",alignItems:"flex-end",padding:"40px 48px",background:"linear-gradient(to top,rgba(10,10,15,.95) 0%,transparent 60%)" }}>
            <div style={{ maxWidth:520 }}>
              <span style={{ display:"inline-block",background:"#e63946",color:"#fff",fontSize:11,fontWeight:700,letterSpacing:1.5,padding:"3px 10px",borderRadius:4,marginBottom:10 }}>🔥 NỔI BẬT</span>
              <h1 style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:48,lineHeight:1,color:"#fff",marginBottom:10 }}>{hero.title?.romaji||hero.title?.native}</h1>
              <div style={{ display:"flex",gap:16,color:"#8888aa",fontSize:13,marginBottom:14 }}>
                <span>★ <span style={{ color:"#ffd60a",fontWeight:700 }}>{hero.averageScore?(hero.averageScore/10).toFixed(1):"—"}</span></span>
                <span>📺 {hero.episodes||"?"} tập</span>
                <span style={{ color:hero.status==="RELEASING"?"#4ade80":"#8888aa" }}>{hero.status==="RELEASING"?"▶ Đang chiếu":"✓ Hoàn thành"}</span>
              </div>
              <p style={{ fontSize:14,color:"#bbb",lineHeight:1.6,marginBottom:20,display:"-webkit-box",WebkitLineClamp:2,WebkitBoxOrient:"vertical",overflow:"hidden" }}>
                {hero.description?.replace(/<[^>]*>/g,"").slice(0,200)}...
              </p>
              <div style={{ display:"flex",gap:12 }}>
                <button onClick={()=>onWatch(hero,1)} style={{ display:"flex",alignItems:"center",gap:8,background:"#e63946",color:"#fff",border:"none",borderRadius:8,padding:"10px 24px",fontWeight:700,fontSize:14,cursor:"pointer",fontFamily:"'Noto Sans JP',sans-serif" }}>▶ Xem ngay</button>
                <button onClick={()=>onDetail(hero)} style={{ display:"flex",alignItems:"center",gap:8,background:"rgba(255,255,255,.1)",color:"#fff",border:"1px solid rgba(255,255,255,.2)",borderRadius:8,padding:"10px 24px",fontSize:14,cursor:"pointer",fontFamily:"'Noto Sans JP',sans-serif" }}>ℹ Thông tin</button>
              </div>
            </div>
          </div>
          <div style={{ position:"absolute",bottom:16,right:48,display:"flex",gap:8 }}>
            {heroes.map((_,i)=>(
              <button key={i} onClick={()=>setHeroIdx(i)} style={{ width:8,height:8,borderRadius:"50%",background:i===heroIdx?"#e63946":"rgba(255,255,255,.3)",border:"none",cursor:"pointer",transform:i===heroIdx?"scale(1.3)":"scale(1)",transition:"all .2s" }}/>
            ))}
          </div>
        </div>
      )}

      <div style={{ padding:"32px 48px" }}>
        <div style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:28,letterSpacing:1,color:"#e8e8f0",marginBottom:20 }}>ĐANG <span style={{ color:"#e63946" }}>HOT</span></div>
        {grid(trending)}

        <div style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:28,letterSpacing:1,color:"#e8e8f0",margin:"40px 0 20px" }}>MỚI <span style={{ color:"#e63946" }}>CẬP NHẬT</span></div>
        {grid(newAnimes)}

        <div style={{ display:"flex",justifyContent:"center",marginTop:40 }}>
          <button onClick={()=>{ setShowAll(true); window.scrollTo({top:0}); }} style={{
            display:"flex",alignItems:"center",gap:10,
            background:"linear-gradient(135deg,#e63946,#c1121f)",
            color:"#fff",border:"none",borderRadius:10,padding:"14px 40px",
            fontSize:16,fontWeight:700,cursor:"pointer",
            fontFamily:"'Noto Sans JP',sans-serif",
            boxShadow:"0 4px 20px rgba(230,57,70,.35)",transition:"transform .15s,box-shadow .15s"
          }}
            onMouseEnter={e=>{ e.currentTarget.style.transform="translateY(-2px)"; e.currentTarget.style.boxShadow="0 8px 28px rgba(230,57,70,.5)"; }}
            onMouseLeave={e=>{ e.currentTarget.style.transform=""; e.currentTarget.style.boxShadow="0 4px 20px rgba(230,57,70,.35)"; }}>
            🎌 Xem tất cả anime
          </button>
        </div>
      </div>
    </>
  );
}