// ═══════════════════════════════════════
// CONTROLLER — điều phối toàn bộ app
// ═══════════════════════════════════════
import { useState } from "react";
import Navbar       from "./Navbar";
import Toast, { useToast } from "./Toast";
import Modal        from "./Modal";
import HomePage     from "./HomePage";
import ShopPage     from "./ShopPage";
import CartPage     from "./CartPage";
import OrdersPage   from "./OrdersPage";
import VideoPlayer  from "./VideoPlayer";
import { IC, loginApi, registerApi, searchAnimeApi } from "./model.js";
import Ico from "./Ico";

// ── useCart hook ──
function useCart(user, openLogin, toast) {
  const [cart, setCart] = useState([]);
  const addToCart = (p) => {
    if (!user) { openLogin(); return; }
    setCart(prev => {
      const ex = prev.find(i=>i.productId===p.productId);
      if (ex) return prev.map(i=>i.productId===p.productId?{...i,qty:i.qty+1}:i);
      return [...prev,{...p,qty:1}];
    });
    toast(`🛒 Đã thêm "${p.productName}"!`);
  };
  const removeCart = (id) => setCart(p=>p.filter(i=>i.productId!==id));
  const updateQty  = (id,d) => setCart(p=>p.map(i=>i.productId===id?{...i,qty:Math.max(1,i.qty+d)}:i));
  const clearCart  = () => setCart([]);
  const cartTotal  = cart.reduce((s,i)=>s+i.price*i.qty,0);
  const cartCount  = cart.reduce((s,i)=>s+i.qty,0);
  return { cart, addToCart, removeCart, updateQty, clearCart, cartTotal, cartCount };
}

export default function App() {
  const [page,         setPage]       = useState("home");
  const [user,         setUser]       = useState(null);
  const [token,        setToken]      = useState(null);
  const [orders,       setOrders]     = useState([]);
  const [videoPlayer,  setVideo]      = useState(null);
  const [loginModal,   setLoginModal] = useState(false);
  const [regModal,     setRegModal]   = useState(false);
  const [checkoutModal,setCheckout]   = useState(false);
  const [address,      setAddress]    = useState("");
  const [loading,      setLoading]    = useState(false);
  const [loginForm,    setLoginForm]  = useState({ email:"", password:"" });
  const [regForm,      setRegForm]    = useState({ name:"", email:"", password:"" });

  // Search state
  const [searchQ,      setSearchQ]    = useState("");
  const [searchRes,    setSearchRes]  = useState([]);
  const [searchLoading,setSearchLoad] = useState(false);

  const { toasts, toast } = useToast();
  const { cart, addToCart, removeCart, updateQty, clearCart, cartTotal, cartCount } =
    useCart(user, ()=>setLoginModal(true), toast);

  // ── navigate ──
  const goPage = (p) => {
    if ((p==="cart"||p==="orders") && !user) { setLoginModal(true); toast("Vui lòng đăng nhập! 🔐","error"); return; }
    setPage(p);
  };

  // ── search ──
  const handleSearch = async (anime) => {
    // khi click item trong dropdown → mở watch
    setVideo({ anime, initEpisode:1 });
  };

  const handleSearchInput = async (q) => {
    setSearchQ(q);
    if (!q.trim()) { setSearchRes([]); return; }
    setSearchLoad(true);
    try { const r = await searchAnimeApi(q); setSearchRes(r); }
    catch { setSearchRes([]); }
    finally { setSearchLoad(false); }
  };

  // ── auth ──
  const handleLogin = async () => {
    if (!loginForm.email.trim()||!loginForm.password.trim()) { toast("Điền đầy đủ thông tin!","error"); return; }
    setLoading(true);
    try {
      const res = await loginApi(loginForm.email, loginForm.password);
      const tok = res.data?.accessToken;
      if (!tok) { toast(res.message||"Sai email hoặc mật khẩu!","error"); setLoading(false); return; }
      setToken(tok); setUser({ email:loginForm.email });
      setLoginModal(false); toast(`Chào mừng ${loginForm.email}! 🎌`);
      setLoginForm({ email:"", password:"" });
    } catch { toast("Không kết nối server!","error"); }
    setLoading(false);
  };

  const handleRegister = async () => {
    if (!regForm.name.trim()||!regForm.email.trim()||!regForm.password.trim()) { toast("Điền đầy đủ thông tin!","error"); return; }
    setLoading(true);
    try {
      const res = await registerApi(regForm.name, regForm.email, regForm.password);
      if (res.status===201) {
        toast("Đăng ký thành công! 🌸"); setRegForm({ name:"", email:"", password:"" });
        setRegModal(false); setLoginModal(true);
      } else toast(res.message||"Đăng ký thất bại!","error");
    } catch { toast("Không kết nối server!","error"); }
    setLoading(false);
  };

  const logout = () => { setToken(null); setUser(null); setPage("home"); toast("Đã đăng xuất!"); };

  // ── checkout ──
  const handleCheckout = () => {
    if (!address.trim()) { toast("Nhập địa chỉ giao hàng!","error"); return; }
    setOrders(prev=>[{ orderId:Date.now(), address, totalPrice:cartTotal, status:false,
      createdAt:new Date().toISOString(),
      orderDetail:cart.map(i=>({ productName:i.productName, quantity:i.qty, price:i.price }))
    }, ...prev]);
    clearCart(); setCheckout(false); setAddress(""); setPage("orders"); toast("Đặt hàng thành công! 🎉");
  };

  // CSS vars
  const css = `
    @import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Noto+Sans+JP:wght@300;400;700&family=Orbitron:wght@400;700;900&display=swap');
    :root{--bg:#0a0a0f;--surface:#12121a;--surface2:#1a1a26;--border:#2a2a3e;--accent:#e63946;--gold:#ffd60a;--text:#e8e8f0;--dim:#8888aa;}
    *,*::before,*::after{box-sizing:border-box;margin:0;padding:0;}
    body{background:#0a0a0f;color:#e8e8f0;font-family:'Noto Sans JP',sans-serif;min-height:100vh;}
    .fi{width:100%;padding:11px 14px;background:#0a0a0f;border:1px solid #2a2a3e;border-radius:10px;color:#e8e8f0;font-size:14px;outline:none;font-family:'Noto Sans JP',sans-serif;margin-bottom:14px;transition:border-color .2s;}
    .fi:focus{border-color:#e63946;}
    .fl{font-size:11px;color:#8888aa;margin-bottom:6px;display:block;letter-spacing:.5px;font-weight:700;}
    .btn-red{width:100%;padding:13px;background:#e63946;border:none;border-radius:10px;color:#fff;font-size:14px;font-weight:700;cursor:pointer;font-family:'Noto Sans JP',sans-serif;transition:opacity .2s;}
    .btn-red:hover{opacity:.88;}
    .btn-red:disabled{opacity:.5;cursor:not-allowed;}
  `;

  return (
    <>
      <style>{css}</style>
      <div style={{ minHeight:"100vh",background:"#0a0a0f" }}>
        <Navbar
          page={page} user={user} cartCount={cartCount}
          onNavigate={goPage} onLogin={()=>setLoginModal(true)}
          onLogout={logout} onSearch={handleSearch} onSearchSubmit={(q)=>setSearchQ(q)}
          onRegister={()=>setRegModal(true)}
        />

        {page==="home"   && <HomePage onWatch={(a,ep)=>setVideo({anime:a,initEpisode:ep})} onDetail={a=>{ if(a){ setVideo({anime:a,initEpisode:1}); } else { setSearchQ(""); } }} searchQuery={searchQ} onClearSearch={()=>setSearchQ("")}/>}
        {page==="shop"   && <ShopPage user={user} onLogin={()=>setLoginModal(true)} onAddToCart={addToCart}/>}
        {page==="cart"   && <CartPage cart={cart} cartTotal={cartTotal} user={user} onLogin={()=>setLoginModal(true)} onUpdateQty={updateQty} onRemove={removeCart} onCheckout={()=>setCheckout(true)}/>}
        {page==="orders" && <OrdersPage orders={orders} user={user} onLogin={()=>setLoginModal(true)}/>}
      </div>

      {/* Video Player */}
      {videoPlayer && <VideoPlayer anime={videoPlayer.anime} initEpisode={videoPlayer.initEpisode} token={token} onClose={()=>setVideo(null)}/>}

      {/* Login Modal */}
      <Modal open={loginModal} onClose={()=>setLoginModal(false)} title="🎌 Đăng nhập">
        <label className="fl">EMAIL</label>
        <input className="fi" type="email" placeholder="you@example.com" value={loginForm.email} onChange={e=>setLoginForm(f=>({...f,email:e.target.value}))} onKeyDown={e=>e.key==="Enter"&&handleLogin()}/>
        <label className="fl">MẬT KHẨU</label>
        <input className="fi" type="password" placeholder="••••••••" value={loginForm.password} onChange={e=>setLoginForm(f=>({...f,password:e.target.value}))} onKeyDown={e=>e.key==="Enter"&&handleLogin()}/>
        <button className="btn-red" onClick={handleLogin} disabled={loading}>{loading?"Đang đăng nhập...":"🎌 Đăng nhập"}</button>
        <div style={{ textAlign:"center",marginTop:14,fontSize:13,color:"#8888aa" }}>
          Chưa có tài khoản? <span style={{ color:"#e63946",cursor:"pointer",fontWeight:700 }} onClick={()=>{setLoginModal(false);setRegModal(true);}}>Đăng ký ngay</span>
        </div>
      </Modal>

      {/* Register Modal */}
      <Modal open={regModal} onClose={()=>setRegModal(false)} title="🌸 Đăng ký">
        <label className="fl">HỌ TÊN</label>
        <input className="fi" placeholder="Nguyen Van A" value={regForm.name} onChange={e=>setRegForm(f=>({...f,name:e.target.value}))}/>
        <label className="fl">EMAIL</label>
        <input className="fi" type="email" placeholder="you@example.com" value={regForm.email} onChange={e=>setRegForm(f=>({...f,email:e.target.value}))}/>
        <label className="fl">MẬT KHẨU</label>
        <input className="fi" type="password" placeholder="••••••••" value={regForm.password} onChange={e=>setRegForm(f=>({...f,password:e.target.value}))}/>
        <button className="btn-red" onClick={handleRegister} disabled={loading}>{loading?"Đang đăng ký...":"🌸 Đăng ký"}</button>
        <div style={{ textAlign:"center",marginTop:14,fontSize:13,color:"#8888aa" }}>
          Đã có tài khoản? <span style={{ color:"#e63946",cursor:"pointer",fontWeight:700 }} onClick={()=>{setRegModal(false);setLoginModal(true);}}>Đăng nhập</span>
        </div>
      </Modal>

      {/* Checkout Modal */}
      <Modal open={checkoutModal} onClose={()=>setCheckout(false)} title="🎁 Xác nhận đặt hàng">
        <div style={{ background:"#1a1a26",border:"1px solid #2a2a3e",borderRadius:10,padding:16,marginBottom:18 }}>
          {cart.map(i=>(
            <div key={i.productId} style={{ display:"flex",justifyContent:"space-between",fontSize:13,color:"#8888aa",marginBottom:4 }}>
              <span>{i.productName} × {i.qty}</span>
              <span style={{ color:"#e8e8f0" }}>{(i.price*i.qty).toLocaleString("vi-VN")}đ</span>
            </div>
          ))}
          <div style={{ borderTop:"1px solid #2a2a3e",paddingTop:10,marginTop:8,display:"flex",justifyContent:"space-between",fontFamily:"'Bebas Neue',sans-serif",fontSize:18 }}>
            <span>TỔNG</span><span style={{ color:"#ffd60a" }}>{cartTotal.toLocaleString("vi-VN")}đ</span>
          </div>
        </div>
        <label className="fl">ĐỊA CHỈ GIAO HÀNG</label>
        <input className="fi" placeholder="Số nhà, đường, phường, quận, TP..." value={address} onChange={e=>setAddress(e.target.value)} onKeyDown={e=>e.key==="Enter"&&handleCheckout()}/>
        <button className="btn-red" onClick={handleCheckout}>🎌 Xác nhận đặt hàng</button>
      </Modal>

      <Toast toasts={toasts}/>
    </>
  );
}