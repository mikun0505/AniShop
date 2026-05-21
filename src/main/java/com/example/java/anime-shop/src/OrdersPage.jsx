import Ico from "../components/Ico";
import { IC } from "../constants";

export default function OrdersPage({ orders, user, onLogin }) {
  if (!user) return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", padding: "100px 20px", textAlign: "center" }}>
      <div style={{ width: 90, height: 90, borderRadius: "50%", background: "linear-gradient(135deg,#7c3aed33,#ec489933)", border: "2px solid #7c3aed44", display: "flex", alignItems: "center", justifyContent: "center", fontSize: 36, marginBottom: 22 }}>🔐</div>
      <h2 style={{ fontFamily: "var(--fd)", fontSize: 26, letterSpacing: 1, marginBottom: 10 }}>YÊU CẦU ĐĂNG NHẬP</h2>
      <p style={{ color: "var(--mut)", fontSize: 14, lineHeight: 1.7, marginBottom: 28, maxWidth: 320 }}>Bạn cần đăng nhập để xem đơn hàng.</p>
      <button className="btn-p" style={{ maxWidth: 220 }} onClick={onLogin}>
        <Ico d={IC.login} size={16} /> Đăng nhập ngay
      </button>
    </div>
  );

  if (orders.length === 0) return (
    <div className="empty">
      <span className="em">📦</span>
      <h3>Chưa có đơn hàng</h3>
      <p>Đặt hàng để xem lịch sử tại đây!</p>
    </div>
  );

  return (
    <>
      <div className="sh" style={{ marginBottom: 20 }}>
        <div className="st">LỊCH SỬ <em>ĐƠN HÀNG</em></div>
        <span style={{ fontSize: 13, color: "var(--mut)" }}>{orders.length} đơn</span>
      </div>
      {orders.map((o) => (
        <div key={o.orderId} className="oc">
          <div className="oh">
            <div>
              <div className="oid">#{String(o.orderId).slice(-6)}</div>
              <div style={{ fontSize: 12, color: "var(--mut)", marginTop: 3 }}>{new Date(o.createdAt).toLocaleString("vi-VN")}</div>
              <div style={{ fontSize: 12, color: "var(--mut)", marginTop: 2 }}>📍 {o.address}</div>
            </div>
            <div className="ob" style={{
              background: o.status ? "#10b98122" : "#f59e0b22",
              color: o.status ? "#34d399" : "#fbbf24",
              border: `1px solid ${o.status ? "#10b98144" : "#f59e0b44"}`,
            }}>
              {o.status ? "✅ Đã xác nhận" : "⏳ Chờ xác nhận"}
            </div>
          </div>
          <div style={{ borderTop: "1px solid var(--brd)", paddingTop: 14 }}>
            {o.orderDetail?.map((d, i) => (
              <div key={i} className="ol">
                <span>{d.productName} × {d.quantity}</span>
                <span style={{ color: "var(--txt)" }}>{(d.price * d.quantity).toLocaleString("vi-VN")}₫</span>
              </div>
            ))}
            <div style={{ display: "flex", justifyContent: "space-between", marginTop: 12, borderTop: "1px solid var(--brd)", paddingTop: 12 }}>
              <span style={{ fontSize: 14, color: "var(--mut)" }}>Tổng cộng</span>
              <span style={{ fontFamily: "var(--fd)", fontSize: 20, color: "#fbbf24" }}>{o.totalPrice.toLocaleString("vi-VN")}₫</span>
            </div>
          </div>
        </div>
      ))}
    </>
  );
}
