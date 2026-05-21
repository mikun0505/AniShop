import Ico from "./Ico";
import { IC } from "../constants";

export default function Toast({ toasts }) {
  return (
    <div style={{ position: "fixed", bottom: 24, right: 24, zIndex: 9999, display: "flex", flexDirection: "column", gap: 8 }}>
      {toasts.map((t) => (
        <div key={t.id} style={{
          background: t.type === "error"
            ? "linear-gradient(135deg,#ef4444,#dc2626)"
            : "linear-gradient(135deg,#10b981,#059669)",
          color: "#fff", padding: "11px 18px", borderRadius: 12, fontSize: 13,
          boxShadow: "0 8px 32px #0009", animation: "toastIn .3s ease",
          display: "flex", alignItems: "center", gap: 8, maxWidth: 320, fontFamily: "var(--fb)",
        }}>
          <Ico d={t.type === "error" ? IC.close : IC.check} size={15} /> {t.msg}
        </div>
      ))}
    </div>
  );
}
