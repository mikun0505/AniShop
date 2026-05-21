export const API_BASE = "http://localhost:8080";

export const PROVIDERS = ["ANIMEVIETSUB", "ANIMEVN", "NINIYO"];

export const ANIME_COLORS = ["#e74c3c","#9b59b6","#3498db","#e67e22","#1abc9c","#e91e63","#f39c12","#2ecc71"];
export const ANIME_EMOJI  = ["🏴‍☠️","🍃","⚔️","🗡️","👊","📓","⚗️","🎮"];
export const PROD_COLS    = ["#e74c3c","#9b59b6","#3498db","#e67e22","#1abc9c","#e91e63"];
export const PROD_EMJS    = ["⚔️","🌸","🔥","⚡","🌙","🗡️"];

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
  eye:      ["M15 12a3 3 0 11-6 0 3 3 0 016 0z","M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"],
  close:    "M6 18L18 6M6 6l12 12",
  plus:     "M12 4v16m8-8H4",
  minus:    "M20 12H4",
  trash:    "M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16",
  check:    "M5 13l4 4L19 7",
  lock:     ["M12 15v2","M6 9V7a6 6 0 0112 0v2","M5 9h14a1 1 0 011 1v10a1 1 0 01-1 1H5a1 1 0 01-1-1V10a1 1 0 011-1z"],
  user:     ["M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2","M12 11a4 4 0 100-8 4 4 0 000 8z"],
};

export const MOCK_ANIMES = [
  { animeId: 1, anilistId: 21,     animeName: "One Piece",                      coverImg: "", score: 8.7, episodes: 1000, viewCount: 98420,  status: "RELEASING", synopsis: "Monkey D. Luffy lên đường chinh phục đại dương, tìm kiếm kho báu huyền thoại One Piece để trở thành Vua Hải Tặc.",                                              animeGenres: [{genreId:1,genreName:"Action"},{genreId:2,genreName:"Adventure"}] },
  { animeId: 2, anilistId: 20,     animeName: "Naruto",                          coverImg: "", score: 8.3, episodes: 220,  viewCount: 77310,  status: "FINISHED",  synopsis: "Naruto Uzumaki, một ninja trẻ mang trong mình con hồ ly chín đuôi, quyết tâm trở thành Hokage để được mọi người công nhận.",                                     animeGenres: [{genreId:1,genreName:"Action"},{genreId:3,genreName:"Shounen"}] },
  { animeId: 3, anilistId: 16498,  animeName: "Attack on Titan",                 coverImg: "", score: 9.0, episodes: 87,   viewCount: 112000, status: "FINISHED",  synopsis: "Nhân loại sống sau những bức tường khổng lồ để tránh Titan. Khi bức tường bị phá vỡ, Eren Yeager thề sẽ tiêu diệt tất cả Titan.",                           animeGenres: [{genreId:1,genreName:"Action"},{genreId:4,genreName:"Drama"},{genreId:5,genreName:"Dark Fantasy"}] },
  { animeId: 4, anilistId: 101922, animeName: "Demon Slayer",                    coverImg: "", score: 8.7, episodes: 44,   viewCount: 89500,  status: "RELEASING", synopsis: "Tanjiro Kamado gia nhập Quỷ Sát Đội sau khi gia đình bị tàn sát và em gái biến thành quỷ. Hành trình biến Nezuko trở lại làm người.",                       animeGenres: [{genreId:1,genreName:"Action"},{genreId:6,genreName:"Supernatural"}] },
  { animeId: 5, anilistId: 113415, animeName: "Jujutsu Kaisen",                  coverImg: "", score: 8.6, episodes: 48,   viewCount: 74200,  status: "RELEASING", synopsis: "Yuji Itadori nuốt ngón tay nguyền rủa của Sukuna và bước vào thế giới bí ẩn của Chú Thuật Hồi Chiến.",                                                         animeGenres: [{genreId:1,genreName:"Action"},{genreId:6,genreName:"Supernatural"}] },
  { animeId: 6, anilistId: 1535,   animeName: "Death Note",                      coverImg: "", score: 8.9, episodes: 37,   viewCount: 95100,  status: "FINISHED",  synopsis: "Light Yagami tình cờ tìm thấy quyển sổ tử thần Death Note — ai bị viết tên vào sẽ chết. Cuộc đấu trí giữa Light và thám tử L bắt đầu.",                   animeGenres: [{genreId:7,genreName:"Thriller"},{genreId:8,genreName:"Mystery"}] },
  { animeId: 7, anilistId: 269,    animeName: "Fullmetal Alchemist: Brotherhood", coverImg: "", score: 9.1, episodes: 64,   viewCount: 88800,  status: "FINISHED",  synopsis: "Hai anh em Edward và Alphonse Elric dùng giả kim thuật để hồi sinh mẹ — cái giá phải trả là quá lớn. Hành trình tìm Đá Triết Học bắt đầu.",            animeGenres: [{genreId:1,genreName:"Action"},{genreId:9,genreName:"Fantasy"},{genreId:4,genreName:"Drama"}] },
  { animeId: 8, anilistId: 11757,  animeName: "Sword Art Online",                coverImg: "", score: 7.6, episodes: 25,   viewCount: 61200,  status: "FINISHED",  synopsis: "Kirito bị mắc kẹt trong game nhập vai thực tế ảo SAO. Chết trong game đồng nghĩa chết ngoài thực tế.",                                                   animeGenres: [{genreId:1,genreName:"Action"},{genreId:9,genreName:"Fantasy"},{genreId:10,genreName:"Romance"}] },
];

export const MOCK_PRODUCTS = [
  { productId: 1, productName: "Mô hình Naruto Sage Mode",  price: 450000,  stock: 12, categoryName: "Figure",   shopName: "OtakuStore VN",  description: "Figure PVC cao cấp, chi tiết sắc nét" },
  { productId: 2, productName: "Áo thun Attack on Titan",   price: 185000,  stock: 30, categoryName: "Áo thun",  shopName: "AniMerch HCM",   description: "Cotton 100%, in lụa Survey Corps" },
  { productId: 3, productName: "Poster Demon Slayer Tanjiro",price: 45000,   stock: 50, categoryName: "Poster",   shopName: "AniMerch HCM",   description: "Giấy ảnh A2, màu sắc sống động" },
  { productId: 4, productName: "Keychain Luffy Gear 5",     price: 65000,   stock: 25, categoryName: "Phụ kiện", shopName: "OtakuStore VN",  description: "Mica trong suốt, thiết kế chuẩn nhân vật" },
  { productId: 5, productName: "Manga FMA Trọn bộ",         price: 1200000, stock: 5,  categoryName: "Manga",    shopName: "BookAnime VN",   description: "27 tập, bìa màu đặc biệt" },
  { productId: 6, productName: "Nón Straw Hat One Piece",   price: 320000,  stock: 8,  categoryName: "Phụ kiện", shopName: "OtakuStore VN",  description: "Chất liệu thật, size unisex" },
];
