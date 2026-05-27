import Ico from "./Ico";
import { IC } from "./model.js";
export default function CartPage({ cart, cartTotal, user, onLogin, onUpdateQty, onRemove, onCheckout }) {
  if (!user) return (
    <div style={{ display:"flex",flexDirection:"column",alignItems:"center",justifyContent:"center",padding:"100px 20px",textAlign:"center" }}>
      <div style={{ fontSize:64,marginBottom:16,opacity:.4 }}>🔐</div>
      <h2 style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:28,marginBottom:10,color:"#e8e8f0" }}>YÊU CẦU ĐĂNG NHẬP</h2>
      <p style={{ color:"#8888aa",marginBottom:24 }}>Đăng nhập để xem giỏ hàng</p>
      <button onClick={onLogin} style={{ background:"#e63946",border:"none",borderRadius:10,color:"#fff",padding:"12px 32px",fontSize:14,fontWeight:700,cursor:"pointer" }}>Đăng nhập ngay</button>
    </div>
  );
  if (!cart.length) return (
    <div style={{ textAlign:"center",padding:"80px 20px",color:"#8888aa" }}>
      <div style={{ fontSize:64,marginBottom:16,opacity:.4 }}>🛒</div>
      <h3 style={{ color:"#e8e8f0",marginBottom:8 }}>Giỏ hàng trống</h3>
      <p>Hãy thêm sản phẩm vào giỏ!</p>
    </div>
  );
  return (
    <div style={{ padding:"32px 48px",maxWidth:800,margin:"0 auto" }}>
      <div style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:28,letterSpacing:1,marginBottom:24,color:"#e8e8f0" }}>GIỎ <span style={{ color:"#e63946" }}>HÀNG</span></div>
      {cart.map(i=>(
        <div key={i.productId} style={{ display:"flex",alignItems:"center",gap:16,padding:"16px 20px",background:"#12121a",border:"1px solid #2a2a3e",borderRadius:14,marginBottom:10 }}>
          <img src={i.img||""} alt={i.productName} style={{ width:60,height:60,objectFit:"cover",borderRadius:8,flexShrink:0 }} onError={e=>e.target.style.display="none"}/>
          <div style={{ flex:1,minWidth:0 }}>
            <div style={{ fontSize:14,fontWeight:700,color:"#e8e8f0",marginBottom:4 }}>{i.productName}</div>
            <div style={{ fontSize:13,color:"#e63946",fontWeight:700 }}>{i.price.toLocaleString("vi-VN")}đ</div>
          </div>
          <div style={{ display:"flex",alignItems:"center",gap:8 }}>
            <button onClick={()=>onUpdateQty(i.productId,-1)} style={{ width:28,height:28,borderRadius:8,border:"1px solid #2a2a3e",background:"#1a1a26",color:"#e8e8f0",cursor:"pointer",display:"flex",alignItems:"center",justifyContent:"center" }}><Ico d={IC.minus} size={14}/></button>
            <span style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:16,width:24,textAlign:"center" }}>{i.qty}</span>
            <button onClick={()=>onUpdateQty(i.productId,1)} style={{ width:28,height:28,borderRadius:8,border:"1px solid #2a2a3e",background:"#1a1a26",color:"#e8e8f0",cursor:"pointer",display:"flex",alignItems:"center",justifyContent:"center" }}><Ico d={IC.plus} size={14}/></button>
          </div>
          <div style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:16,color:"#ffd60a",minWidth:80,textAlign:"right" }}>{(i.price*i.qty).toLocaleString("vi-VN")}đ</div>
          <button onClick={()=>onRemove(i.productId)} style={{ background:"none",border:"none",color:"#555",cursor:"pointer",padding:4 }}><Ico d={IC.trash} size={16}/></button>
        </div>
      ))}
      <div style={{ display:"flex",justifyContent:"space-between",alignItems:"center",padding:"20px 0",borderTop:"1px solid #2a2a3e",marginTop:8 }}>
        <span style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:22,color:"#e8e8f0" }}>TỔNG CỘNG</span>
        <span style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:28,color:"#ffd60a" }}>{cartTotal.toLocaleString("vi-VN")}đ</span>
      </div>
      <button onClick={onCheckout} style={{ width:"100%",padding:14,background:"#e63946",border:"none",borderRadius:12,color:"#fff",fontSize:15,fontWeight:700,cursor:"pointer",fontFamily:"'Noto Sans JP',sans-serif" }}>🎌 Đặt hàng ngay</button>
    </div>
  );
}
