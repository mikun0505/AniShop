import Ico from "./Ico";
import { IC } from "../index.js";

export default function Topbar({ page, user, searchHome, searchShop, onSearchHome, onSearchShop, onLogin, onRegister }) {
  const titles = { home: "🎌 ANISHOP", shop: "🛒 SHOP", cart: "🛍 GIỎ HÀNG", orders: "📦 ĐƠN HÀNG" };

  return (
    <div className="tb">
      <span className="tb-title">{titles[page]}</span>

      {page === "home" && (
        <div className="sbar">
          <Ico d={IC.search} size={15} />
          <input placeholder="Tìm anime..." value={searchHome}
            onChange={(e) => onSearchHome(e.target.value)} />
          {searchHome && (
            <button style={{background:"none",border:"none",cursor:"pointer",color:"var(--mut)"}} onClick={() => onSearchHome("")}>
              <Ico d={IC.close} size={14} />
            </button>
          )}
        </div>
      )}

      {page === "shop" && (
        <div className="sbar">
          <Ico d={IC.search} size={15} />
          <input placeholder="Tìm sản phẩm..." value={searchShop}
            onChange={(e) => onSearchShop(e.target.value)} />
          {searchShop && (
            <button style={{background:"none",border:"none",cursor:"pointer",color:"var(--mut)"}} onClick={() => onSearchShop("")}>
              <Ico d={IC.close} size={14} />
            </button>
          )}
        </div>
      )}

      {user ? (
        <div style={{display:"flex",alignItems:"center",gap:8,padding:"6px 14px",background:"var(--card)",border:"1px solid var(--brd)",borderRadius:12,flexShrink:0}}>
          <div style={{width:28,height:28,borderRadius:"50%",background:"linear-gradient(135deg,var(--acc),var(--acc2))",display:"flex",alignItems:"center",justifyContent:"center",fontSize:13,fontWeight:800}}>
            {(user.email||"?")[0].toUpperCase()}
          </div>
          <span style={{fontSize:13,fontWeight:700}}>{user.email}</span>
        </div>
      ) : (
        <div style={{display:"flex",gap:8,flexShrink:0}}>
          <button className="btn-g" onClick={onLogin} style={{display:"flex",alignItems:"center",gap:6,padding:"7px 16px"}}>
            <Ico d={IC.login} size={14}/> Đăng nhập
          </button>
          <button onClick={onRegister} style={{display:"flex",alignItems:"center",gap:6,padding:"7px 16px",background:"linear-gradient(135deg,var(--acc),var(--acc2))",border:"none",borderRadius:10,color:"#fff",fontSize:13,fontWeight:800,cursor:"pointer",fontFamily:"var(--fb)"}}>
            <Ico d={IC.register} size={14}/> Đăng ký
          </button>
        </div>
      )}
    </div>
  );
}
