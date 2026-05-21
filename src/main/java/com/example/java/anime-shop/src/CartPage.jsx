import Ico from "../components/Ico";
import { IC, PROD_EMJS } from "../constants";

export default function CartPage({ cart, cartTotal, cartCount, user, onLogin, onUpdateQty, onRemove, onCheckout }) {
  if (!user) return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", padding: "100px 20px", textAlign: "center" }}>
      <div style={{ width: 90, height: 90, borderRadius: "50%", background: "linear-gradient(135deg,#7c3aed33,#ec489933)", border: "2px solid #7c3aed44", display: "flex", alignItems: "center", justifyContent: "center", fontSize: 36, marginBottom: 22 }}>🔐</div>
      <h2 style={{ fontFamily: "var(--fd)", fontSize: 26, letterSpacing: 1, marginBottom: 10 }}>YÊU CẦU ĐĂNG NHẬP</h2>
      <p style={{ color: "var(--mut)", fontSize: 14, lineHeight: 1.7, marginBottom: 28, maxWidth: 320 }}>Bạn cần đăng nhập để sử dụng tính năng này.</p>
      <button className="btn-p" style={{ maxWidth: 220 }} onClick={onLogin}>
        <Ico d={IC.login} size={16} /> Đăng nhập ngay
      </button>
    </div>
  );

  if (cart.length === 0) return (
    <div className="empty">
      <span className="em">🛒</span>
      <h3>Giỏ hàng trống</h3>
      <p>Sang <strong style={{ color: "var(--acc)", cursor: "pointer" }}>Shop</strong> để chọn đồ nhé!</p>
    </div>
  );

  return (
    <>
      {cart.map((item) => (
        <div key={item.productId} className="ci">
          <div className="ce">{PROD_EMJS[(item.productId - 1) % 6]}</div>
          <div style={{ flex: 1 }}>
            <div style={{ fontSize: 14, fontWeight: 700 }}>{item.productName}</div>
            <div style={{ fontSize: 12, color: "var(--mut)", marginTop: 2 }}>{item.shopName}</div>
            <div style={{ fontSize: 15, color: "#fbbf24", fontFamily: "var(--fd)", marginTop: 4 }}>
              {(item.price * item.qty).toLocaleString("vi-VN")}₫
            </div>
          </div>
          <div className="qc">
            <button className="qb" onClick={() => onUpdateQty(item.productId, -1)}><Ico d={IC.minus} size={12} /></button>
            <span className="qn">{item.qty}</span>
            <button className="qb" onClick={() => onUpdateQty(item.productId, 1)}><Ico d={IC.plus} size={12} /></button>
            <button className="qb" style={{ marginLeft: 6, borderColor: "#ef444444" }} onClick={() => onRemove(item.productId)}>
              <Ico d={IC.trash} size={12} />
            </button>
          </div>
        </div>
      ))}
      <div className="sm">
        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 18 }}>
          <span style={{ fontSize: 15, color: "var(--mut)" }}>Tổng ({cartCount} sản phẩm)</span>
          <span style={{ fontFamily: "var(--fd)", fontSize: 24, color: "#fbbf24" }}>{cartTotal.toLocaleString("vi-VN")}₫</span>
        </div>
        <button className="btn-p" onClick={onCheckout}>🎌 Đặt hàng ngay</button>
      </div>
    </>
  );
}
