export default function OrdersPage({ orders, user, onLogin }) {
  if (!user) return (
    <div style={{ display:"flex",flexDirection:"column",alignItems:"center",justifyContent:"center",padding:"100px 20px",textAlign:"center" }}>
      <div style={{ fontSize:64,marginBottom:16,opacity:.4 }}>🔐</div>
      <h2 style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:28,marginBottom:10,color:"#e8e8f0" }}>YÊU CẦU ĐĂNG NHẬP</h2>
      <button onClick={onLogin} style={{ background:"#e63946",border:"none",borderRadius:10,color:"#fff",padding:"12px 32px",fontSize:14,fontWeight:700,cursor:"pointer",marginTop:16 }}>Đăng nhập ngay</button>
    </div>
  );
  if (!orders.length) return (
    <div style={{ textAlign:"center",padding:"80px 20px",color:"#8888aa" }}>
      <div style={{ fontSize:64,marginBottom:16,opacity:.4 }}>📦</div>
      <h3 style={{ color:"#e8e8f0",marginBottom:8 }}>Chưa có đơn hàng</h3>
    </div>
  );
  return (
    <div style={{ padding:"32px 48px" }}>
      <div style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:28,letterSpacing:1,marginBottom:24,color:"#e8e8f0" }}>LỊCH SỬ <span style={{ color:"#e63946" }}>ĐƠN HÀNG</span></div>
      {orders.map(o=>(
        <div key={o.orderId} style={{ background:"#12121a",border:"1px solid #2a2a3e",borderRadius:14,padding:22,marginBottom:14 }}>
          <div style={{ display:"flex",justifyContent:"space-between",alignItems:"start",marginBottom:14 }}>
            <div>
              <div style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:20,color:"#e63946" }}>#{String(o.orderId).slice(-6)}</div>
              <div style={{ fontSize:12,color:"#8888aa",marginTop:2 }}>{new Date(o.createdAt).toLocaleString("vi-VN")}</div>
              <div style={{ fontSize:12,color:"#8888aa",marginTop:2 }}>📍 {o.address}</div>
            </div>
            <span style={{ padding:"5px 13px",borderRadius:99,fontSize:11,fontWeight:700,background:o.status?"#10b98122":"#f59e0b22",color:o.status?"#34d399":"#fbbf24",border:`1px solid ${o.status?"#10b98144":"#f59e0b44"}` }}>
              {o.status?"✅ Đã xác nhận":"⏳ Chờ xác nhận"}
            </span>
          </div>
          <div style={{ borderTop:"1px solid #2a2a3e",paddingTop:14 }}>
            {o.orderDetail?.map((d,i)=>(
              <div key={i} style={{ display:"flex",justifyContent:"space-between",fontSize:13,color:"#8888aa",marginBottom:4 }}>
                <span>{d.productName} × {d.quantity}</span>
                <span style={{ color:"#e8e8f0" }}>{(d.price*d.quantity).toLocaleString("vi-VN")}đ</span>
              </div>
            ))}
            <div style={{ display:"flex",justifyContent:"space-between",marginTop:12,paddingTop:12,borderTop:"1px solid #2a2a3e" }}>
              <span style={{ color:"#8888aa",fontSize:14 }}>Tổng cộng</span>
              <span style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:20,color:"#ffd60a" }}>{o.totalPrice.toLocaleString("vi-VN")}đ</span>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}
