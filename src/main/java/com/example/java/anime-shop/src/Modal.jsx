import Ico from "./Ico";
import { IC } from "../constants";

export default function Modal({ open, onClose, title, children }) {
  if (!open) return null;
  return (
    <div style={{
      position: "fixed", inset: 0, background: "rgba(0,0,0,.88)", zIndex: 2000,
      display: "flex", alignItems: "center", justifyContent: "center", backdropFilter: "blur(8px)",
    }} onClick={onClose}>
      <div style={{
        background: "var(--card)", border: "1px solid var(--brd)", borderRadius: 22,
        padding: 32, minWidth: 390, maxWidth: "92vw", maxHeight: "90vh", overflow: "auto",
        animation: "modalIn .22s ease",
      }} onClick={(e) => e.stopPropagation()}>
        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 24 }}>
          <h2 style={{ fontFamily: "var(--fd)", fontSize: 20, color: "var(--acc)" }}>{title}</h2>
          <button onClick={onClose} style={{ background: "none", border: "none", color: "var(--mut)", cursor: "pointer" }}>
            <Ico d={IC.close} size={18} />
          </button>
        </div>
        {children}
      </div>
    </div>
  );
}
