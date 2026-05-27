import { useState } from "react";
import { MOCK_PRODUCTS, IC } from "./model.js";
import Ico from "./Ico";

const CATS = ["Tất cả","Figure","Clothing","Poster","Accessory","Manga"];

export default function ShopPage({ user, onLogin, onAddToCart }) {
  const [cat, setCat]     = useState("Tất cả");
  const [search, setSearch] = useState("");
  const filtered = MOCK_PRODUCTS.filter(p =>
    (cat==="Tất cả" || p.categoryName===cat) &&
    (!search || p.productName.toLowerCase().includes(search.toLowerCase()))
  );

  return (
    <>
      {/* Banner */}
      <div style={{ height:160,background:"linear-gradient(135deg,#1a1a26,#2a1a2a)",display:"flex",alignItems:"center",padding:"0 48px",borderBottom:"1px solid #2a2a3e",position:"relative",overflow:"hidden" }}>
        <div style={{ position:"absolute",right:-100,top:-100,width:400,height:400,borderRadius:"50%",background:"radial-gradient(circle,rgba(230,57,70,.1) 0%,transparent 70%)" }}/>
        <div>
          <h1 style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:48,color:"#e8e8f0" }}>🛍 ANI SHOP</h1>
          <p style={{ color:"#8888aa",fontSize:14,marginTop:6 }}>Merchandise anime chính hãng — figurine, poster, áo, phụ kiện</p>
        </div>
      </div>

      {/* Search + filter */}
      <div style={{ padding:"20px 48px",display:"flex",gap:12,alignItems:"center",flexWrap:"wrap",borderBottom:"1px solid #2a2a3e" }}>
        <div style={{ display:"flex",alignItems:"center",gap:8,background:"#1a1a26",border:"1px solid #2a2a3e",borderRadius:10,padding:"8px 14px",minWidth:220 }}>
          <Ico d={IC.search} size={14} color="#8888aa"/>
          <input value={search} onChange={e=>setSearch(e.target.value)} placeholder="Tìm sản phẩm..."
            style={{ background:"none",border:"none",outline:"none",color:"#e8e8f0",fontSize:14,flex:1,fontFamily:"'Noto Sans JP',sans-serif" }}/>
        </div>
        {CATS.map(c=>(
          <button key={c} onClick={()=>setCat(c)} style={{ padding:"8px 20px",borderRadius:20,border:"1px solid",borderColor:cat===c?"#e63946":"#2a2a3e",background:cat===c?"#e63946":"#12121a",color:cat===c?"#fff":"#8888aa",fontSize:13,cursor:"pointer",fontFamily:"'Noto Sans JP',sans-serif",fontWeight:cat===c?700:400 }}>{c}</button>
        ))}
      </div>

      {/* Grid */}
      <div style={{ display:"grid",gridTemplateColumns:"repeat(auto-fill,minmax(220px,1fr))",gap:20,padding:"24px 48px 48px" }}>
        {filtered.map(p=>(
          <div key={p.productId} style={{ background:"#12121a",border:"1px solid #2a2a3e",borderRadius:12,overflow:"hidden",cursor:"pointer",transition:"transform .2s,box-shadow .2s" }}
            onMouseEnter={e=>{e.currentTarget.style.transform="translateY(-4px)";e.currentTarget.style.boxShadow="0 12px 32px rgba(0,0,0,.4)";}}
            onMouseLeave={e=>{e.currentTarget.style.transform="";e.currentTarget.style.boxShadow="";}}>
            <div style={{ position:"relative",overflow:"hidden" }}>
              <img src={p.img} alt={p.productName} loading="lazy" style={{ width:"100%",aspectRatio:"1/1",objectFit:"cover",display:"block",transition:"transform .3s" }}
                onMouseEnter={e=>e.target.style.transform="scale(1.05)"}
                onMouseLeave={e=>e.target.style.transform=""}
                onError={e=>e.target.src="https://via.placeholder.com/300x300/1a1a26/888?text=AniShop"}/>
            </div>
            <div style={{ padding:14 }}>
              <div style={{ fontSize:14,fontWeight:700,marginBottom:4,overflow:"hidden",textOverflow:"ellipsis",whiteSpace:"nowrap",color:"#e8e8f0" }}>{p.productName}</div>
              <div style={{ fontSize:12,color:"#8888aa",marginBottom:10 }}>📺 {p.categoryName}</div>
              <div style={{ display:"flex",alignItems:"center",justifyContent:"space-between" }}>
                <span style={{ color:"#e63946",fontWeight:700,fontSize:15 }}>{p.price.toLocaleString("vi-VN")}đ</span>
                <button onClick={()=>user?onAddToCart(p):onLogin()}
                  style={{ width:32,height:32,borderRadius:8,background:"#e63946",border:"none",color:"#fff",fontSize:18,cursor:"pointer",display:"flex",alignItems:"center",justifyContent:"center",transition:"transform .15s" }}
                  onMouseEnter={e=>e.currentTarget.style.transform="scale(1.1)"}
                  onMouseLeave={e=>e.currentTarget.style.transform=""}>+</button>
              </div>
            </div>
          </div>
        ))}
        {filtered.length===0 && <div style={{ gridColumn:"1/-1",textAlign:"center",padding:"80px 20px",color:"#8888aa" }}><div style={{ fontSize:64,marginBottom:16,opacity:.4 }}>📦</div><h3 style={{ color:"#e8e8f0" }}>Không có sản phẩm</h3></div>}
      </div>
    </>
  );
}
