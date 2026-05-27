import { useState, useRef, useEffect } from "react";
import Ico from "./Ico";
import { IC } from "./model.js";

const ANILIST = "https://graphql.anilist.co";

async function searchAniList(query) {
  const q = `query($s:String){Page(page:1 perPage:8){media(type:ANIME search:$s sort:POPULARITY_DESC){id title{romaji native}coverImage{large}averageScore episodes status}}}`;
  const res = await fetch(ANILIST, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ query: q, variables: { s: query } }),
  });
  const json = await res.json();
  return json.data?.Page?.media || [];
}

export default function Navbar({ page, user, cartCount, onNavigate, onLogin, onLogout, onSearch, onSearchSubmit, onRegister }) {
  const [q, setQ]           = useState("");
  const [results, setResults] = useState([]);
  const [show, setShow]     = useState(false);
  const [loading, setLoading] = useState(false);
  const timer = useRef(null);

  const handleInput = (v) => {
    setQ(v);
    clearTimeout(timer.current);
    if (v.trim().length < 2) { setResults([]); setShow(false); return; }
    setShow(true); setLoading(true);
    timer.current = setTimeout(async () => {
      try {
        const res = await searchAniList(v);
        setResults(res);
      } catch { setResults([]); }
      setLoading(false);
    }, 350);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && q.trim().length >= 2) {
      setShow(false);
      onSearchSubmit?.(q.trim());
    }
    if (e.key === "Escape") { setShow(false); }
  };

  const selectItem = (a) => {
    const title = a.title?.romaji || a.title?.native || "";
    setQ(title); setShow(false);
    onSearch?.(a);
  };

  const submitSearch = () => {
    if (q.trim().length >= 2) {
      setShow(false);
      onSearchSubmit?.(q.trim());
    }
  };

  const clearSearch = () => {
    setQ(""); setShow(false);
    onSearchSubmit?.("");
  };

  useEffect(() => {
    const close = (e) => { if (!e.target.closest(".srchwrap")) setShow(false); };
    document.addEventListener("click", close);
    return () => document.removeEventListener("click", close);
  }, []);

  return (
    <nav style={{ position:"sticky",top:0,zIndex:100,display:"flex",alignItems:"center",gap:20,padding:"0 32px",height:64,background:"rgba(10,10,15,.93)",backdropFilter:"blur(20px)",borderBottom:"1px solid #2a2a3e" }}>
      {/* Logo */}
      <a onClick={() => { onNavigate("home"); clearSearch(); }} style={{ fontFamily:"'Orbitron',monospace",fontWeight:900,fontSize:22,color:"#e63946",letterSpacing:2,cursor:"pointer",textDecoration:"none",flexShrink:0 }}>
        ANI<span style={{ color:"#ffd60a" }}>SHOP</span>
      </a>

      {/* Nav tabs */}
      <div style={{ display:"flex",gap:4,marginRight:"auto" }}>
        {[
          { id:"home",  label:"🎬 Anime" },
          { id:"shop",  label:"🛒 Shop" },
          { id:"cart",  label:`🛍 Giỏ${cartCount?" ("+cartCount+")":""}`, auth:true },
          { id:"orders",label:"📦 Đơn hàng", auth:true },
        ].map(n => (
          <button key={n.id} onClick={() => onNavigate(n.id)}
            style={{ padding:"6px 18px",borderRadius:6,border:"none",background:page===n.id?"#1a1a26":"transparent",color:page===n.id?"#e63946":"#8888aa",fontFamily:"'Noto Sans JP',sans-serif",fontSize:14,cursor:"pointer",transition:"all .2s",position:"relative" }}>
            {n.label}
            {n.auth && !user && <span style={{ position:"absolute",bottom:4,right:4,fontSize:8,color:"#555" }}>🔒</span>}
          </button>
        ))}
      </div>

      {/* Search */}
      <div className="srchwrap" style={{ position:"relative" }}>
        <div style={{ display:"flex",alignItems:"center",gap:8,background:"#1a1a26",border:"1px solid #2a2a3e",borderRadius:10,padding:"8px 14px",width:300,transition:"border-color .2s" }}
          onFocus={e=>e.currentTarget.style.borderColor="#e63946"}
          onBlur={e=>e.currentTarget.style.borderColor="#2a2a3e"}>
          <Ico d={IC.search} size={15} color="#8888aa"/>
          <input
            value={q}
            onChange={e => handleInput(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="Tìm anime... (Enter để xem tất cả)"
            style={{ background:"none",border:"none",outline:"none",color:"#e8e8f0",fontSize:14,flex:1,fontFamily:"'Noto Sans JP',sans-serif" }}
          />
          {q && (
            <button onClick={clearSearch} style={{ background:"none",border:"none",cursor:"pointer",color:"#555",padding:0,fontSize:16 }}>✕</button>
          )}
        </div>

        {/* Dropdown */}
        {show && (
          <div style={{ position:"absolute",top:"calc(100% + 8px)",left:0,right:0,background:"#1a1a26",border:"1px solid #2a2a3e",borderRadius:10,zIndex:200,boxShadow:"0 8px 32px rgba(0,0,0,.6)",overflow:"hidden" }}>
            {loading ? (
              <div style={{ padding:16,textAlign:"center",color:"#8888aa",fontSize:13 }}>
                <div style={{ width:20,height:20,border:"2px solid #2a2a3e",borderTopColor:"#e63946",borderRadius:"50%",animation:"spin .8s linear infinite",margin:"0 auto 6px" }}/>
                Đang tìm...
              </div>
            ) : results.length === 0 ? (
              <div style={{ padding:16,textAlign:"center",color:"#8888aa",fontSize:13 }}>Không tìm thấy "{q}"</div>
            ) : (
              <>
                <div style={{ maxHeight:340,overflowY:"auto" }}>
                  {results.map((a,i) => {
                    const title = a.title?.romaji || a.title?.native || "";
                    const score = a.averageScore ? (a.averageScore/10).toFixed(1) : "—";
                    const status = a.status === "RELEASING" ? "🟢 Đang chiếu" : "✓ Hoàn thành";
                    return (
                      <div key={a.id||i} onClick={() => selectItem(a)}
                        style={{ display:"flex",alignItems:"center",gap:12,padding:"10px 14px",cursor:"pointer",borderBottom:"1px solid #2a2a3e",transition:"background .15s" }}
                        onMouseEnter={e=>e.currentTarget.style.background="#0a0a0f"}
                        onMouseLeave={e=>e.currentTarget.style.background=""}>
                        {a.coverImage?.large
                          ? <img src={a.coverImage.large} alt="" style={{ width:36,height:50,objectFit:"cover",borderRadius:4,flexShrink:0 }} onError={e=>e.target.style.display="none"}/>
                          : <div style={{ width:36,height:50,background:"#2a2a3e",borderRadius:4,flexShrink:0,display:"flex",alignItems:"center",justifyContent:"center" }}>🎌</div>
                        }
                        <div style={{ flex:1,minWidth:0 }}>
                          <div style={{ fontSize:13,fontWeight:700,overflow:"hidden",textOverflow:"ellipsis",whiteSpace:"nowrap",color:"#e8e8f0" }}>{title}</div>
                          <div style={{ fontSize:11,color:"#8888aa",marginTop:2 }}>★ {score} · {a.episodes||"?"} tập · {status}</div>
                        </div>
                      </div>
                    );
                  })}
                </div>
                {/* Footer: xem tất cả */}
                <div onClick={submitSearch}
                  style={{ padding:"12px 16px",textAlign:"center",cursor:"pointer",borderTop:"1px solid #2a2a3e",background:"#12121a",color:"#e63946",fontSize:13,fontWeight:700,transition:"background .15s" }}
                  onMouseEnter={e=>e.currentTarget.style.background="#1a1a26"}
                  onMouseLeave={e=>e.currentTarget.style.background="#12121a"}>
                  🔍 Xem tất cả kết quả cho "{q}" →
                </div>
              </>
            )}
          </div>
        )}
      </div>

      {/* Auth */}
      {user ? (
        <div style={{ display:"flex",alignItems:"center",gap:8,flexShrink:0 }}>
          <div style={{ width:32,height:32,borderRadius:"50%",background:"#e63946",display:"flex",alignItems:"center",justifyContent:"center",fontSize:13,fontWeight:800,color:"#fff" }}>
            {user.email[0].toUpperCase()}
          </div>
          <button onClick={onLogout} style={{ background:"none",border:"1px solid #2a2a3e",borderRadius:8,color:"#8888aa",padding:"6px 14px",cursor:"pointer",fontSize:13,fontFamily:"'Noto Sans JP',sans-serif" }}>
            Đăng xuất
          </button>
        </div>
      ) : (
        <div style={{ display:"flex",gap:8,flexShrink:0 }}>
          <button onClick={onLogin} style={{ background:"none",border:"1px solid #2a2a3e",borderRadius:8,color:"#e8e8f0",padding:"7px 16px",cursor:"pointer",fontSize:13,fontFamily:"'Noto Sans JP',sans-serif",display:"flex",alignItems:"center",gap:6 }}>
            <Ico d={IC.login} size={14}/> Đăng nhập
          </button>
          <button onClick={onRegister} style={{ background:"#e63946",border:"none",borderRadius:8,color:"#fff",padding:"7px 16px",cursor:"pointer",fontSize:13,fontWeight:700,fontFamily:"'Noto Sans JP',sans-serif",display:"flex",alignItems:"center",gap:6 }}>
            <Ico d={IC.register} size={14}/> Đăng ký
          </button>
        </div>
      )}
      <style>{`@keyframes spin{to{transform:rotate(360deg)}}`}</style>
    </nav>
  );
}