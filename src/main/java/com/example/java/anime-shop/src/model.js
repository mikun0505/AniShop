// ═══════════════════════════════════════
// MODEL — data, state, API calls
// ═══════════════════════════════════════

export const API_BASE  = "";   // dùng Vite proxy
export const ANILIST   = "https://graphql.anilist.co";
export const PROVIDERS = ["ANIMEVIETSUB", "ANIMEVN", "NINEANIME"];

export const IC = {
  home:     "M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6",
  shop:     "M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z",
  cart:     ["M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"],
  order:    "M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2",
  login:    "M15 3h4a2 2 0 012 2v14a2 2 0 01-2 2h-4M10 17l5-5-5-5M15 12H3",
  logout:   "M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1",
  register: "M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z",
  search:   "M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z",
  play:     "M5 3l14 9-14 9V3z",
  star:     "M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z",
  close:    "M6 18L18 6M6 6l12 12",
  back:     "M19 12H5M12 19l-7-7 7-7",
  plus:     "M12 4v16m8-8H4",
  minus:    "M20 12H4",
  trash:    "M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16",
  user:     ["M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2", "M12 11a4 4 0 100-8 4 4 0 000 8z"],
  eye:      "M15 12a3 3 0 11-6 0 3 3 0 016 0zM2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z",
  lock:     "M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z",
};

export const MOCK_PRODUCTS = [
  { productId:1, productName:"Mô hình Naruto Sage Mode",    price:450000, stock:12, categoryName:"Figure",    img:"https://m.media-amazon.com/images/I/61t5r5KZSBL._AC_SX679_.jpg" },
  { productId:2, productName:"Áo thun Attack on Titan",     price:185000, stock:30, categoryName:"Clothing",  img:"https://m.media-amazon.com/images/I/71g8xAQBJ6L._AC_SX679_.jpg" },
  { productId:3, productName:"Poster Demon Slayer Tanjiro",  price:45000,  stock:50, categoryName:"Poster",   img:"https://cdn.myanimelist.net/images/anime/1286/99889l.jpg" },
  { productId:4, productName:"Keychain Luffy Gear 5",       price:65000,  stock:25, categoryName:"Accessory", img:"https://m.media-amazon.com/images/I/71PfbZBN-QL._AC_SX679_.jpg" },
  { productId:5, productName:"Manga FMA Trọn bộ 27 tập",   price:1200000,stock:5,  categoryName:"Manga",     img:"https://cdn.myanimelist.net/images/manga/1/260551l.jpg" },
  { productId:6, productName:"Nón Straw Hat One Piece",     price:320000, stock:8,  categoryName:"Accessory", img:"https://m.media-amazon.com/images/I/71tpDoJulZL._AC_SX679_.jpg" },
  { productId:7, productName:"Figurine Gojo Satoru",        price:850000, stock:10, categoryName:"Figure",    img:"https://cdn.myanimelist.net/images/characters/7/492123.jpg" },
  { productId:8, productName:"Hoodie Survey Corps",         price:450000, stock:15, categoryName:"Clothing",  img:"https://m.media-amazon.com/images/I/71g8xAQBJ6L._AC_SX679_.jpg" },
];

// ── AniList ──
async function anilistQuery(query, variables) {
  const res = await fetch(ANILIST, {
    method: "POST",
    headers: { "Content-Type": "application/json", "Accept": "application/json" },
    body: JSON.stringify({ query, variables }),
  });
  return (await res.json()).data;
}

export async function fetchTrending(page = 1) {
  const q = `query($p:Int $n:Int){Page(page:$p perPage:$n){media(type:ANIME sort:TRENDING_DESC status_in:[RELEASING,FINISHED]){id title{romaji native}coverImage{extraLarge large}bannerImage averageScore episodes status description(asHtml:false)genres nextAiringEpisode{episode}}}}`;
  const d = await anilistQuery(q, { p: page, n: 12 });
  return d?.Page?.media || [];
}

export async function searchAnimeApi(query, offset = 0) {
  const res = await fetch(`/api/animes/search?title=${encodeURIComponent(query)}&offset=${offset}`);
  const json = await res.json();
  return json.data || [];
}

export async function fetchEpisodes(anilistId, provider, offset = 0, token = "") {
  const res = await fetch(`/api/animes/${anilistId}/episodes?provider=${provider}&offset=${offset}`, {
    headers: token ? { Authorization: `Bearer ${token}` } : {},
  });
  if (!res.ok) throw new Error("backend offline");
  return res.json();
}

export async function fetchStream(episodeId, provider, token = "") {
  const res = await fetch(`/api/animes/stream?episodes=${encodeURIComponent(episodeId)}&provider=${provider}`, {
    headers: token ? { Authorization: `Bearer ${token}` } : {},
  });
  if (!res.ok) throw new Error("no stream");
  return res.json();
}

export async function loginApi(email, password) {
  const res = await fetch("/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password }),
  });
  return res.json();
}

export async function registerApi(name, email, password) {
  const res = await fetch("/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email, password }),
  });
  return res.json();
}

export async function fetchProductsApi(token = "") {
  try {
    const res = await fetch("/api/products", {
      headers: token ? { Authorization: `Bearer ${token}` } : {},
    });
    const json = await res.json();
    return json.data || MOCK_PRODUCTS;
  } catch { return MOCK_PRODUCTS; }
}
