export default function Ico({ d, size = 20 }) {
  return (
    <svg width={size} height={size} viewBox="0 0 24 24" fill="none"
      stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
      {[].concat(d).map((p, i) => <path key={i} d={p} />)}
    </svg>
  );
}
