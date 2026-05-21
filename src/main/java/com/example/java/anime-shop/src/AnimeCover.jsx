import Ico from "./Ico";
import { IC, ANIME_COLORS, ANIME_EMOJI } from "../constants";

export default function AnimeCover({ anime, size = "full", onClick }) {
  const idx   = anime.animeId % ANIME_COLORS.length;
  const color = ANIME_COLORS[idx];
  const emoji = ANIME_EMOJI[idx];

  if (size === "sm") return (
    <div onClick={onClick} style={{
      width: 90, height: 126, borderRadius: 10, flexShrink: 0,
      background: `linear-gradient(160deg,${color}55,#0d111f)`,
      border: `1px solid ${color}55`, display: "flex", alignItems: "center", justifyContent: "center",
      fontSize: 36, cursor: "pointer", transition: "transform .2s",
    }}
      onMouseEnter={(e) => (e.currentTarget.style.transform = "scale(1.05)")}
      onMouseLeave={(e) => (e.currentTarget.style.transform = "scale(1)")}>
      {emoji}
    </div>
  );

  return (
    <div onClick={onClick} style={{
      width: "100%", paddingTop: "145%", position: "relative",
      borderRadius: 14, overflow: "hidden", cursor: "pointer",
      background: `linear-gradient(160deg,${color}66,#0d111f)`,
      border: `1px solid ${color}44`,
    }}
      onMouseEnter={(e) => (e.currentTarget.querySelector(".ov").style.opacity = 1)}
      onMouseLeave={(e) => (e.currentTarget.querySelector(".ov").style.opacity = 0)}>
      <div style={{
        position: "absolute", inset: 0, display: "flex", flexDirection: "column",
        alignItems: "center", justifyContent: "center", gap: 8,
      }}>
        <span style={{ fontSize: 52 }}>{emoji}</span>
        <span style={{ fontSize: 11, color, fontFamily: "var(--fd)", letterSpacing: 1 }}>★ {anime.score}</span>
      </div>
      <div className="ov" style={{
        position: "absolute", inset: 0, background: "rgba(0,0,0,.5)",
        display: "flex", alignItems: "center", justifyContent: "center",
        opacity: 0, transition: "opacity .2s",
      }}>
        <div style={{
          width: 52, height: 52, borderRadius: "50%", background: "rgba(255,255,255,.18)",
          backdropFilter: "blur(4px)", display: "flex", alignItems: "center", justifyContent: "center",
          border: "2px solid rgba(255,255,255,.35)",
        }}>
          <Ico d={IC.play} size={22} />
        </div>
      </div>
    </div>
  );
}
