import { useEffect, useState } from "react";
export function useToast() {
  const [toasts, setToasts] = useState([]);
  const toast = (msg, type = "ok") => {
    const id = Date.now();
    setToasts(p => [...p, { id, msg, type }]);
    setTimeout(() => setToasts(p => p.filter(t => t.id !== id)), 3000);
  };
  return { toasts, toast };
}
export default function Toast({ toasts }) {
  return (
    <div style={{ position:"fixed", bottom:24, right:24, zIndex:999, display:"flex", flexDirection:"column", gap:8 }}>
      {toasts.map(t => (
        <div key={t.id} style={{
          background:"#12121a", border:`1px solid #2a2a3e`,
          borderLeft: `3px solid ${t.type==="error"?"#e63946":"#e63946"}`,
          padding:"12px 18px", borderRadius:8, fontSize:14, color:"#e8e8f0",
          boxShadow:"0 8px 32px rgba(0,0,0,.5)", animation:"slideIn .25s ease",
          minWidth:240,
        }}>{t.msg}</div>
      ))}
      <style>{`@keyframes slideIn{from{transform:translateX(60px);opacity:0}to{transform:none;opacity:1}}`}</style>
    </div>
  );
}
