import Ico from "./Ico";
import { IC } from "../constants";

export default function Navbar({ page, user, cartCount, onNavigate, onLogin, onLogout }) {
  const mainNav = [
    { id: "home",   icon: IC.home,  label: "Home" },
    { id: "shop",   icon: IC.shop,  label: "Shop" },
    { id: "cart",   icon: IC.cart,  label: "Cart",   badge: cartCount || null, requiresAuth: true },
    { id: "orders", icon: IC.order, label: "Orders", requiresAuth: true },
  ];

  const authNav = user
    ? [{ id: "logout", icon: IC.logout, label: user.username?.slice(0, 5) || "Me", action: onLogout }]
    : [
        { id: "login",    icon: IC.login,    label: "Login",    action: onLogin },
        { id: "register", icon: IC.register, label: "Register", action: () => onNavigate("register") },
      ];

  return (
    <aside className="sb">
      <div className="logo" onClick={() => onNavigate("home")}>ANI</div>

      {mainNav.map((n) => (
        <button key={n.id}
          className={`nb${page === n.id ? " active" : ""}`}
          onClick={() => onNavigate(n.id)}
          title={n.requiresAuth && !user ? `${n.label} (cần đăng nhập)` : n.label}>
          <Ico d={n.icon} size={20} />
          <span>{n.label}</span>
          {n.badge ? <span className="nbadge">{n.badge}</span> : null}
          {n.requiresAuth && !user && (
            <span className="nb-lock"><Ico d={IC.lock} size={9} /></span>
          )}
        </button>
      ))}

      <div className="sdiv" />

      <div className="sb-bot">
        {authNav.map((n) => (
          <button key={n.id} className="nb" onClick={n.action} title={n.label}>
            <Ico d={n.icon} size={20} />
            <span>{n.label}</span>
          </button>
        ))}
      </div>
    </aside>
  );
}
