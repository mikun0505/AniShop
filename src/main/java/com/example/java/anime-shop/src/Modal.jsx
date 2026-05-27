import Ico from "./Ico";
import { IC } from "./model.js";
export default function Modal({ open, onClose, title, children }) {
  if (!open) return null;
  return (
    <div onClick={onClose} style={{ position:"fixed",inset:0,zIndex:500,background:"rgba(0,0,0,.85)",backdropFilter:"blur(8px)",display:"flex",alignItems:"center",justifyContent:"center",padding:20 }}>
      <div onClick={e=>e.stopPropagation()} style={{ background:"#12121a",border:"1px solid #2a2a3e",borderRadius:20,maxWidth:440,width:"100%",padding:30,position:"relative" }}>
        <button onClick={onClose} style={{ position:"absolute",top:16,right:16,background:"#1a1a26",border:"1px solid #2a2a3e",borderRadius:8,color:"#8888aa",cursor:"pointer",width:34,height:34,display:"flex",alignItems:"center",justifyContent:"center" }}>
          <Ico d={IC.close} size={15}/>
        </button>
        <h2 style={{ fontFamily:"'Bebas Neue',sans-serif",fontSize:24,letterSpacing:1,marginBottom:22,color:"#e8e8f0" }}>{title}</h2>
        {children}
      </div>
    </div>
  );
}
