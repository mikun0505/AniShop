import { useState } from "react";

import Navbar       from "./components/Navbar";
import Topbar       from "./components/Topbar";
import Toast        from "./components/Toast";
import Modal        from "./components/Modal";
import VideoPlayer  from "./components/VideoPlayer";
import AnimeCover   from "./components/AnimeCover";
import HomePage     from "./pages/HomePage";
import ShopPage     from "./pages/ShopPage";
import CartPage     from "./pages/CartPage";
import OrdersPage   from "./pages/OrdersPage";

import { useToast }  from "./hooks/useToast";
import { useCart }   from "./hooks/useCart";
import { api }       from "./utils/api";
import { IC, MOCK_ANIMES, MOCK_PRODUCTS, ANIME_COLORS, ANIME_EMOJI } from "./constants";
import Ico from "./components/Ico";

export default function App() {
  const [page,          setPage]          = useState("home");
  const [token,         setToken]         = useState(null);
  const [user,          setUser]          = useState(null);
  const [animes,        setAnimes]        = useState(MOCK_ANIMES);
  const [products]                        = useState(MOCK_PRODUCTS);
  const [orders,        setOrders]        = useState([]);

  const [searchHome,    setSearchHome]    = useState("");
  const [searchShop,    setSearchShop]    = useState("");

  const [loginModal,    setLoginModal]    = useState(false);
  const [regModal,      setRegModal]      = useState(false);
  const [checkoutModal, setCheckoutModal] = useState(false);
  const [detailAnime,   setDetailAnime]   = useState(null);
  const [videoPlayer,   setVideoPlayer]   = useState(null);

  const [loginForm,     setLoginForm]     = useState({ username: "", password: "" });
  const [regForm,       setRegForm]       = useState({ username: "", password: "", email: "" });
  const [address,       setAddress]       = useState("");
  const [loading,       setLoading]       = useState(false);

  const { toasts, toast } = useToast();
  const { cart, addToCart, removeCart, updateQty, cartTotal, cartCount, clearCart } =
    useCart(user, () => setLoginModal(true), toast);

  // ── navigate với auth guard ──
  const goPage = (target) => {
    if (target === "register") { setRegModal(true); return; }
    if ((target === "cart" || target === "orders") && !user) {
      setLoginModal(true);
      toast("Vui lòng đăng nhập trước! 🔐", "error");
      return;
    }
    setPage(target);
  };

  // ── search anime ──
  const doSearch = async (q) => {
    if (!q.trim()) { setAnimes(MOCK_ANIMES); return; }
    try {
      const res = await api.get(`/api/animes/search?title=${encodeURIComponent(q)}&offset=0`, token);
      if (res.data?.length) setAnimes(res.data);
    } catch { /* keep mock */ }
  };

  const handleSearchHome = (q) => { setSearchHome(q); doSearch(q); };
  const handleClearSearch = () => { setSearchHome(""); setAnimes(MOCK_ANIMES); };

  // ── auth ──
  const handleLogin = async () => {
    if (!loginForm.username.trim() || !loginForm.password.trim()) {
      toast("Vui lòng điền đầy đủ thông tin!", "error"); return;
    }
    setLoading(true);
    try {
      const res = await api.post("/login", loginForm);
      const tok = res.result?.token || "demo";
      setToken(tok); setUser({ username: loginForm.username });
    } catch {
      setToken("demo"); setUser({ username: loginForm.username });
    }
    setLoginModal(false);
    toast(`Chào mừng ${loginForm.username}! 🎌`);
    setLoginForm({ username: "", password: "" });
    setLoading(false);
  };

  const handleRegister = async () => {
    if (!regForm.username.trim() || !regForm.email.trim() || !regForm.password.trim()) {
      toast("Vui lòng điền đầy đủ thông tin!", "error"); return;
    }
    setLoading(true);
    try { await api.post("/register", regForm); } catch {}
    toast("Đăng ký thành công! Hãy đăng nhập 🌸");
    setRegForm({ username: "", password: "", email: "" });
    setRegModal(false); setLoginModal(true); setLoading(false);
  };

  const logout = () => {
    setToken(null); setUser(null);
    setPage("home");
    toast("Đã đăng xuất!");
  };

  // ── checkout ──
  const handleCheckout = () => {
    if (!address.trim()) { toast("Vui lòng nhập địa chỉ giao hàng!", "error"); return; }
    const o = {
      orderId: Date.now(), address, totalPrice: cartTotal, status: false,
      createdAt: new Date().toISOString(),
      orderDetail: cart.map((i) => ({ productName: i.productName, quantity: i.qty, price: i.price })),
    };
    setOrders((prev) => [o, ...prev]);
    clearCart(); setCheckoutModal(false); setAddress("");
    setPage("orders");
    toast("Đặt hàng thành công! 🎉");
  };

  // ── derived ──
  const filteredAnimes = animes.filter((a) =>
    a.animeName.toLowerCase().includes(searchHome.toLowerCase()) ||
    a.animeGenres?.some((g) => g.genreName.toLowerCase().includes(searchHome.toLowerCase()))
  );
  const filteredProducts = products.filter((p) =>
    !searchShop ||
    p.productName.toLowerCase().includes(searchShop.toLowerCase()) ||
    p.categoryName.toLowerCase().includes(searchShop.toLowerCase())
  );

  return (
    <>
      <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Nunito:wght@400;600;700;800&display=swap');
        :root {
          --bg:#0b0d17; --card:#111627; --card2:#161e33; --brd:#1e2a4a;
          --acc:#7c3aed; --acc2:#ec4899; --acc3:#06b6d4;
          --txt:#e2e8f0; --mut:#64748b;
          --fd:'Bebas Neue',sans-serif; --fb:'Nunito',sans-serif;
        }
        *,*::before,*::after{box-sizing:border-box;margin:0;padding:0;}
        body{background:var(--bg);color:var(--txt);font-family:var(--fb);min-height:100vh;}
        @keyframes toastIn{from{transform:translateX(60px);opacity:0}to{transform:none;opacity:1}}
        @keyframes modalIn{from{transform:scale(.94);opacity:0}to{transform:scale(1);opacity:1}}
        @keyframes fadeUp{from{transform:translateY(14px);opacity:0}to{transform:none;opacity:1}}
        @keyframes float{0%,100%{transform:translateY(0)}50%{transform:translateY(-10px)}}
        @keyframes heroIn{from{opacity:0;transform:translateY(10px)}to{opacity:1;transform:none}}
        .wrap{display:flex;height:100vh;overflow:hidden;}
        .sb{width:70px;background:var(--card);border-right:1px solid var(--brd);display:flex;flex-direction:column;align-items:center;padding:14px 0 18px;flex-shrink:0;position:relative;z-index:100;}
        .logo{width:42px;height:42px;border-radius:12px;margin-bottom:18px;background:linear-gradient(135deg,var(--acc),var(--acc2));display:flex;align-items:center;justify-content:center;font-family:var(--fd);font-size:13px;color:#fff;letter-spacing:1px;box-shadow:0 4px 18px #7c3aed55;cursor:pointer;user-select:none;}
        .nb{width:50px;height:50px;border-radius:14px;border:none;background:none;display:flex;flex-direction:column;align-items:center;justify-content:center;gap:3px;cursor:pointer;color:var(--mut);transition:all .18s;position:relative;font-family:var(--fb);}
        .nb:hover{background:var(--card2);color:var(--txt);}
        .nb.active{background:linear-gradient(135deg,#7c3aed33,#ec489922);color:#c4b5fd;border:1px solid #7c3aed44;}
        .nb span{font-size:8.5px;font-weight:800;letter-spacing:.2px;line-height:1;}
        .nbadge{position:absolute;top:7px;right:7px;background:var(--acc2);color:#fff;border-radius:99px;font-size:9px;font-weight:900;min-width:16px;height:16px;display:flex;align-items:center;justify-content:center;padding:0 3px;}
        .nb-lock{position:absolute;bottom:6px;right:6px;color:#64748b;opacity:.7;}
        .sdiv{width:28px;height:1px;background:var(--brd);margin:10px 0;}
        .sb-bot{margin-top:auto;display:flex;flex-direction:column;align-items:center;gap:4px;}
        .main{flex:1;overflow-y:auto;display:flex;flex-direction:column;}
        .main::-webkit-scrollbar{width:5px;}
        .main::-webkit-scrollbar-thumb{background:var(--brd);border-radius:99px;}
        .tb{position:sticky;top:0;z-index:50;background:rgba(11,13,23,.93);backdrop-filter:blur(16px);border-bottom:1px solid var(--brd);padding:11px 26px;display:flex;align-items:center;gap:12px;}
        .tb-title{font-family:var(--fd);font-size:22px;letter-spacing:1px;flex:1;}
        .sbar{display:flex;align-items:center;gap:8px;background:var(--card);border:1px solid var(--brd);border-radius:11px;padding:8px 14px;flex:1;max-width:360px;}
        .sbar input{background:none;border:none;outline:none;color:var(--txt);font-size:14px;flex:1;font-family:var(--fb);}
        .sbar input::placeholder{color:var(--mut);}
        .cnt{padding:26px 26px 60px;}
        .hero{border-radius:22px;padding:42px 38px;margin-bottom:30px;position:relative;overflow:hidden;min-height:250px;display:flex;align-items:center;}
        .hero-c{position:relative;z-index:1;max-width:580px;animation:heroIn .5s ease;}
        .h-genres{display:flex;gap:6px;flex-wrap:wrap;margin-bottom:10px;}
        .h-genre{padding:3px 11px;border-radius:99px;font-size:10px;font-weight:800;background:rgba(255,255,255,.13);backdrop-filter:blur(4px);letter-spacing:.5px;}
        .h-title{font-family:var(--fd);font-size:40px;line-height:1.05;margin-bottom:8px;color:#fff;letter-spacing:1px;}
        .h-desc{font-size:13px;color:rgba(255,255,255,.7);line-height:1.65;margin-bottom:18px;display:-webkit-box;-webkit-line-clamp:3;-webkit-box-orient:vertical;overflow:hidden;}
        .h-meta{display:flex;gap:18px;align-items:center;margin-bottom:20px;flex-wrap:wrap;}
        .h-score{display:flex;align-items:center;gap:5px;font-family:var(--fd);font-size:22px;color:#fbbf24;}
        .h-deco{position:absolute;right:38px;top:50%;transform:translateY(-50%);font-size:110px;opacity:.15;animation:float 5s ease infinite;pointer-events:none;filter:blur(.5px);}
        .h-dots{position:absolute;bottom:18px;left:38px;z-index:1;display:flex;gap:6px;}
        .h-dot{width:8px;height:8px;border-radius:99px;background:rgba(255,255,255,.28);cursor:pointer;transition:all .2s;}
        .h-dot.on{width:22px;background:#fff;}
        .btn-w{display:inline-flex;align-items:center;gap:7px;padding:11px 26px;background:#fff;color:#111;border:none;border-radius:11px;font-size:14px;font-weight:800;cursor:pointer;font-family:var(--fb);transition:opacity .18s;letter-spacing:.3px;}
        .btn-w:hover{opacity:.85;}
        .btn-ol{display:inline-flex;align-items:center;gap:7px;padding:11px 22px;background:rgba(255,255,255,.1);color:#fff;border:1px solid rgba(255,255,255,.22);border-radius:11px;font-size:14px;font-weight:700;cursor:pointer;font-family:var(--fb);transition:all .18s;backdrop-filter:blur(4px);margin-left:8px;}
        .btn-ol:hover{background:rgba(255,255,255,.18);}
        .btn-p{width:100%;padding:13px;background:linear-gradient(135deg,var(--acc),var(--acc2));border:none;border-radius:12px;color:#fff;font-size:14px;font-weight:800;cursor:pointer;font-family:var(--fb);letter-spacing:.4px;display:flex;align-items:center;justify-content:center;gap:8px;transition:opacity .18s;}
        .btn-p:hover{opacity:.88;}
        .btn-p:disabled{opacity:.5;cursor:not-allowed;}
        .btn-g{background:none;border:1px solid var(--brd);border-radius:10px;color:var(--mut);padding:9px 18px;cursor:pointer;font-size:13px;font-weight:700;transition:all .18s;font-family:var(--fb);}
        .btn-g:hover{border-color:var(--acc);color:var(--txt);}
        .sh{display:flex;justify-content:space-between;align-items:center;margin-bottom:18px;}
        .st{font-family:var(--fd);font-size:20px;letter-spacing:1px;}
        .st em{color:var(--acc);font-style:normal;}
        .ag{display:grid;grid-template-columns:repeat(auto-fill,minmax(148px,1fr));gap:18px;}
        .ai{animation:fadeUp .35s ease both;}
        .an{font-size:13px;font-weight:700;color:var(--txt);margin-top:9px;line-height:1.3;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;overflow:hidden;}
        .am{font-size:11px;color:var(--mut);margin-top:2px;}
        .asc{font-size:11px;color:#fbbf24;margin-top:2px;}
        .pg{display:grid;grid-template-columns:repeat(auto-fill,minmax(220px,1fr));gap:18px;}
        .pc{background:var(--card);border:1px solid var(--brd);border-radius:18px;overflow:hidden;transition:transform .2s,border-color .2s,box-shadow .2s;animation:fadeUp .35s ease both;}
        .pc:hover{transform:translateY(-4px);border-color:#7c3aed66;box-shadow:0 12px 36px #7c3aed18;}
        .pi{height:140px;display:flex;align-items:center;justify-content:center;font-size:44px;position:relative;}
        .pb{padding:14px;}
        .pcat{font-size:10px;color:var(--acc3);letter-spacing:.5px;font-weight:700;margin-bottom:4px;}
        .pnm{font-size:14px;font-weight:700;color:var(--txt);margin-bottom:4px;line-height:1.3;}
        .psh{font-size:11px;color:var(--mut);margin-bottom:10px;}
        .pr{display:flex;justify-content:space-between;align-items:center;margin-bottom:10px;}
        .ppr{font-family:var(--fd);font-size:18px;color:#fbbf24;}
        .pst{font-size:11px;color:var(--mut);}
        .badd{width:100%;padding:9px;border:none;border-radius:10px;cursor:pointer;font-size:12px;font-weight:800;color:#fff;font-family:var(--fb);letter-spacing:.5px;transition:opacity .18s;display:flex;align-items:center;justify-content:center;gap:6px;}
        .badd:hover{opacity:.84;}
        .badd-lock{width:100%;padding:9px;border:1px solid var(--brd);border-radius:10px;cursor:pointer;font-size:12px;font-weight:800;color:var(--mut);font-family:var(--fb);background:var(--card2);letter-spacing:.5px;transition:all .18s;display:flex;align-items:center;justify-content:center;gap:6px;}
        .badd-lock:hover{border-color:var(--acc);color:var(--txt);}
        .ci{display:flex;align-items:center;gap:14px;padding:14px 18px;background:var(--card);border:1px solid var(--brd);border-radius:16px;margin-bottom:10px;animation:fadeUp .3s ease;}
        .ce{font-size:32px;width:46px;text-align:center;flex-shrink:0;}
        .qc{display:flex;align-items:center;gap:6px;}
        .qb{width:28px;height:28px;border-radius:8px;border:1px solid var(--brd);background:var(--card2);color:var(--txt);cursor:pointer;display:flex;align-items:center;justify-content:center;transition:background .15s;}
        .qb:hover{background:var(--acc);border-color:var(--acc);}
        .qn{font-family:var(--fd);font-size:15px;width:24px;text-align:center;}
        .sm{background:var(--card);border:1px solid var(--brd);border-radius:18px;padding:22px;margin-top:18px;}
        .oc{background:var(--card);border:1px solid var(--brd);border-radius:18px;padding:22px;margin-bottom:14px;animation:fadeUp .3s ease;}
        .oh{display:flex;justify-content:space-between;align-items:start;margin-bottom:14px;}
        .oid{font-family:var(--fd);font-size:18px;color:var(--acc);}
        .ob{padding:5px 13px;border-radius:99px;font-size:11px;font-weight:700;}
        .ol{display:flex;justify-content:space-between;font-size:13px;color:var(--mut);padding:3px 0;}
        .fl{font-size:11px;color:var(--mut);margin-bottom:6px;display:block;letter-spacing:.5px;font-weight:800;}
        .fi{width:100%;padding:12px 14px;background:var(--bg);border:1px solid var(--brd);border-radius:11px;color:var(--txt);font-size:14px;outline:none;font-family:var(--fb);transition:border-color .18s;margin-bottom:14px;}
        .fi:focus{border-color:var(--acc);}
        .dp{position:fixed;inset:0;z-index:500;background:rgba(0,0,0,.9);backdrop-filter:blur(8px);display:flex;align-items:center;justify-content:center;padding:20px;animation:toastIn .2s ease;}
        .db{background:var(--card);border:1px solid var(--brd);border-radius:24px;max-width:620px;width:100%;max-height:90vh;overflow-y:auto;padding:30px;position:relative;}
        .db::-webkit-scrollbar{width:4px;}
        .db::-webkit-scrollbar-thumb{background:var(--brd);border-radius:99px;}
        .empty{text-align:center;padding:80px 20px;color:var(--mut);}
        .empty .em{font-size:60px;margin-bottom:14px;animation:float 3s ease infinite;display:block;}
        .empty h3{font-size:18px;color:var(--txt);margin-bottom:6px;}
        .auth-banner{background:linear-gradient(135deg,#7c3aed22,#ec489922);border:1px solid #7c3aed44;border-radius:14px;padding:14px 20px;display:flex;align-items:center;gap:14px;margin-bottom:22px;animation:fadeUp .3s ease;}
      `}</style>

      <div className="wrap">
        <Navbar
          page={page} user={user} cartCount={cartCount}
          onNavigate={goPage}
          onLogin={() => setLoginModal(true)}
          onLogout={logout}
        />

        <main className="main">
          <Topbar
            page={page} user={user}
            searchHome={searchHome} searchShop={searchShop}
            onSearchHome={handleSearchHome}
            onSearchShop={setSearchShop}
            onLogin={() => setLoginModal(true)}
          />

          <div className="cnt">
            {page === "home" && (
              <HomePage
                animes={animes}
                filteredAnimes={filteredAnimes}
                onWatch={(anime, ep) => setVideoPlayer({ anime, episode: ep })}
                onDetail={setDetailAnime}
              />
            )}
            {page === "shop" && (
              <ShopPage
                products={filteredProducts}
                user={user}
                onAddToCart={addToCart}
                onLogin={() => setLoginModal(true)}
              />
            )}
            {page === "cart" && (
              <CartPage
                cart={cart} cartTotal={cartTotal} cartCount={cartCount}
                user={user}
                onLogin={() => setLoginModal(true)}
                onUpdateQty={updateQty}
                onRemove={removeCart}
                onCheckout={() => setCheckoutModal(true)}
              />
            )}
            {page === "orders" && (
              <OrdersPage orders={orders} user={user} onLogin={() => setLoginModal(true)} />
            )}
          </div>
        </main>
      </div>

      {/* Anime Detail */}
      {detailAnime && (
        <div className="dp" onClick={() => setDetailAnime(null)}>
          <div className="db" onClick={(e) => e.stopPropagation()}>
            <button onClick={() => setDetailAnime(null)} style={{ position: "absolute", top: 16, right: 16, background: "var(--card2)", border: "1px solid var(--brd)", borderRadius: 10, color: "var(--mut)", cursor: "pointer", width: 36, height: 36, display: "flex", alignItems: "center", justifyContent: "center" }}>
              <Ico d={IC.close} size={16} />
            </button>
            <div style={{ display: "flex", gap: 22, marginBottom: 22 }}>
              <div style={{ width: 120, flexShrink: 0 }}>
                <AnimeCover anime={detailAnime} />
              </div>
              <div style={{ flex: 1 }}>
                <div style={{ display: "flex", gap: 5, flexWrap: "wrap", marginBottom: 10 }}>
                  {detailAnime.animeGenres?.map((g) => (
                    <span key={g.genreId} style={{ padding: "3px 10px", borderRadius: 99, fontSize: 10, background: "var(--card2)", color: "var(--acc3)", fontWeight: 800, border: "1px solid var(--brd)" }}>{g.genreName}</span>
                  ))}
                </div>
                <h2 style={{ fontFamily: "var(--fd)", fontSize: 26, marginBottom: 10 }}>{detailAnime.animeName}</h2>
                <div style={{ display: "flex", gap: 14, alignItems: "center", marginBottom: 14, flexWrap: "wrap" }}>
                  <span style={{ fontFamily: "var(--fd)", fontSize: 20, color: "#fbbf24", display: "flex", alignItems: "center", gap: 4 }}>
                    <Ico d={IC.star} size={16} /> {detailAnime.score}
                  </span>
                  <span style={{ fontSize: 12, color: "var(--mut)" }}>{detailAnime.episodes} tập</span>
                  <span style={{ fontSize: 11, padding: "3px 10px", borderRadius: 99, fontWeight: 700, background: detailAnime.status === "RELEASING" ? "#ef444422" : "#10b98122", color: detailAnime.status === "RELEASING" ? "#f87171" : "#34d399" }}>
                    {detailAnime.status === "RELEASING" ? "🔴 Đang chiếu" : "✅ Hoàn thành"}
                  </span>
                </div>
                <p style={{ fontSize: 13, color: "var(--mut)", lineHeight: 1.65 }}>{detailAnime.synopsis}</p>
                {detailAnime.viewCount && (
                  <div style={{ marginTop: 10, fontSize: 12, color: "var(--mut)" }}>👁 {detailAnime.viewCount.toLocaleString()} lượt xem</div>
                )}
              </div>
            </div>
            <div style={{ borderTop: "1px solid var(--brd)", paddingTop: 20, marginBottom: 22 }}>
              <div style={{ fontFamily: "var(--fd)", fontSize: 16, letterSpacing: 1, marginBottom: 14 }}>DANH SÁCH TẬP</div>
              <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill,minmax(56px,1fr))", gap: 8 }}>
                {Array.from({ length: Math.min(detailAnime.episodes || 12, 24) }, (_, i) => (
                  <button key={i}
                    style={{ padding: "9px 4px", background: "var(--card2)", border: "1px solid var(--brd)", borderRadius: 10, color: "var(--mut)", fontSize: 12, cursor: "pointer", fontFamily: "var(--fb)", fontWeight: 700, transition: "all .15s" }}
                    onMouseEnter={(e) => { e.currentTarget.style.background = "var(--acc)"; e.currentTarget.style.color = "#fff"; e.currentTarget.style.borderColor = "var(--acc)"; }}
                    onMouseLeave={(e) => { e.currentTarget.style.background = "var(--card2)"; e.currentTarget.style.color = "var(--mut)"; e.currentTarget.style.borderColor = "var(--brd)"; }}
                    onClick={() => { setDetailAnime(null); setVideoPlayer({ anime: detailAnime, episode: i + 1 }); }}>
                    {i + 1}
                  </button>
                ))}
                {(detailAnime.episodes || 0) > 24 && (
                  <div style={{ gridColumn: "1/-1", fontSize: 12, color: "var(--mut)", textAlign: "center", paddingTop: 6 }}>
                    +{detailAnime.episodes - 24} tập khác...
                  </div>
                )}
              </div>
            </div>
            <div style={{ display: "flex", gap: 10 }}>
              <button className="btn-p" onClick={() => { setDetailAnime(null); setVideoPlayer({ anime: detailAnime, episode: 1 }); }}>
                <Ico d={IC.play} size={16} /> Xem ngay
              </button>
              <button className="btn-g" onClick={() => setDetailAnime(null)}>Đóng</button>
            </div>
          </div>
        </div>
      )}

      {/* Login Modal */}
      <Modal open={loginModal} onClose={() => setLoginModal(false)} title="🎌 Đăng nhập">
        <label className="fl">TÊN ĐĂNG NHẬP</label>
        <input className="fi" placeholder="username" value={loginForm.username}
          onChange={(e) => setLoginForm((f) => ({ ...f, username: e.target.value }))}
          onKeyDown={(e) => e.key === "Enter" && handleLogin()} />
        <label className="fl">MẬT KHẨU</label>
        <input className="fi" type="password" placeholder="••••••••" value={loginForm.password}
          onChange={(e) => setLoginForm((f) => ({ ...f, password: e.target.value }))}
          onKeyDown={(e) => e.key === "Enter" && handleLogin()} />
        <button className="btn-p" onClick={handleLogin} disabled={loading}>
          {loading ? "Đang đăng nhập..." : <><Ico d={IC.login} size={15} /> Đăng nhập</>}
        </button>
        <div style={{ textAlign: "center", marginTop: 14, fontSize: 13, color: "var(--mut)" }}>
          Chưa có tài khoản?{" "}
          <span style={{ color: "var(--acc)", cursor: "pointer", fontWeight: 800 }}
            onClick={() => { setLoginModal(false); setRegModal(true); }}>
            Đăng ký ngay
          </span>
        </div>
      </Modal>

      {/* Register Modal */}
      <Modal open={regModal} onClose={() => setRegModal(false)} title="🌸 Đăng ký tài khoản">
        <label className="fl">TÊN ĐĂNG NHẬP</label>
        <input className="fi" placeholder="otaku_user" value={regForm.username}
          onChange={(e) => setRegForm((f) => ({ ...f, username: e.target.value }))} />
        <label className="fl">EMAIL</label>
        <input className="fi" type="email" placeholder="you@example.com" value={regForm.email}
          onChange={(e) => setRegForm((f) => ({ ...f, email: e.target.value }))} />
        <label className="fl">MẬT KHẨU</label>
        <input className="fi" type="password" placeholder="••••••••" value={regForm.password}
          onChange={(e) => setRegForm((f) => ({ ...f, password: e.target.value }))} />
        <button className="btn-p" onClick={handleRegister} disabled={loading}>
          {loading ? "Đang đăng ký..." : <><Ico d={IC.register} size={15} /> Đăng ký</>}
        </button>
        <div style={{ textAlign: "center", marginTop: 14, fontSize: 13, color: "var(--mut)" }}>
          Đã có tài khoản?{" "}
          <span style={{ color: "var(--acc)", cursor: "pointer", fontWeight: 800 }}
            onClick={() => { setRegModal(false); setLoginModal(true); }}>
            Đăng nhập
          </span>
        </div>
      </Modal>

      {/* Checkout Modal */}
      <Modal open={checkoutModal} onClose={() => setCheckoutModal(false)} title="🎁 Xác nhận đặt hàng">
        <div style={{ background: "var(--card2)", border: "1px solid var(--brd)", borderRadius: 12, padding: 16, marginBottom: 18 }}>
          {cart.map((i) => (
            <div key={i.productId} style={{ display: "flex", justifyContent: "space-between", fontSize: 13, color: "var(--mut)", marginBottom: 5 }}>
              <span>{i.productName} × {i.qty}</span>
              <span style={{ color: "var(--txt)" }}>{(i.price * i.qty).toLocaleString("vi-VN")}₫</span>
            </div>
          ))}
          <div style={{ borderTop: "1px solid var(--brd)", paddingTop: 10, marginTop: 8, display: "flex", justifyContent: "space-between", fontFamily: "var(--fd)", fontSize: 18 }}>
            <span>TỔNG</span>
            <span style={{ color: "#fbbf24" }}>{cartTotal.toLocaleString("vi-VN")}₫</span>
          </div>
        </div>
        <label className="fl">ĐỊA CHỈ GIAO HÀNG</label>
        <input className="fi" placeholder="Số nhà, đường, phường, quận, TP..." value={address}
          onChange={(e) => setAddress(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleCheckout()} />
        <button className="btn-p" onClick={handleCheckout}>🎌 Xác nhận đặt hàng</button>
      </Modal>

      <Toast toasts={toasts} />

      {videoPlayer && (
        <VideoPlayer
          anime={videoPlayer.anime}
          initEpisode={videoPlayer.episode}
          onClose={() => setVideoPlayer(null)}
        />
      )}
    </>
  );
}
