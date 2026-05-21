import Ico from "../components/Ico";
import { IC, PROD_COLS, PROD_EMJS } from "../constants";

export default function ShopPage({ products, user, onAddToCart, onLogin }) {
  return (
    <>
      {!user && (
        <div className="auth-banner">
          <span style={{ fontSize: 24 }}>🔐</span>
          <div style={{ flex: 1 }}>
            <div style={{ fontWeight: 800, fontSize: 14, marginBottom: 3 }}>Đăng nhập để mua hàng</div>
            <div style={{ fontSize: 12, color: "var(--mut)" }}>Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng</div>
          </div>
          <button className="btn-p" style={{ width: "auto", padding: "9px 20px", fontSize: 13 }} onClick={onLogin}>
            <Ico d={IC.login} size={14} /> Đăng nhập
          </button>
        </div>
      )}

      <div className="sh" style={{ marginBottom: 22 }}>
        <div className="st">SẢN PHẨM <em>ANIME</em></div>
        <span style={{ fontSize: 13, color: "var(--mut)" }}>{products.length} sản phẩm</span>
      </div>

      {products.length === 0 ? (
        <div className="empty">
          <span className="em">🔍</span>
          <h3>Không tìm thấy sản phẩm</h3>
          <p>Thử từ khóa khác nhé!</p>
        </div>
      ) : (
        <div className="pg">
          {products.map((p, i) => {
            const ci = (p.productId - 1) % 6;
            return (
              <div key={p.productId} className="pc" style={{ animationDelay: `${i * 0.04}s` }}>
                <div className="pi" style={{ background: `linear-gradient(160deg,${PROD_COLS[ci]}33,#0d111f)`, borderBottom: `2px solid ${PROD_COLS[ci]}44` }}>
                  {PROD_EMJS[ci]}
                </div>
                <div className="pb">
                  <div className="pcat">{p.categoryName}</div>
                  <div className="pnm">{p.productName}</div>
                  <div className="psh">{p.shopName}</div>
                  <div className="pr">
                    <span className="ppr">{p.price.toLocaleString("vi-VN")}₫</span>
                    <span className="pst">Còn {p.stock}</span>
                  </div>
                  {user ? (
                    <button className="badd"
                      style={{ background: `linear-gradient(135deg,${PROD_COLS[ci]}cc,${PROD_COLS[ci]}88)` }}
                      onClick={() => onAddToCart(p)}>
                      + Thêm vào giỏ
                    </button>
                  ) : (
                    <button className="badd-lock" onClick={onLogin}>
                      <Ico d={IC.lock} size={12} /> Đăng nhập để mua
                    </button>
                  )}
                </div>
              </div>
            );
          })}
        </div>
      )}
    </>
  );
}
