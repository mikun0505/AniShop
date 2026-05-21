import { useState } from "react";

export function useCart(user, onNeedLogin, toast) {
  const [cart, setCart] = useState([]);

  const addToCart = (p) => {
    if (!user) {
      onNeedLogin();
      toast("Đăng nhập để thêm vào giỏ hàng! 🔐", "error");
      return;
    }
    setCart((prev) => {
      const ex = prev.find((i) => i.productId === p.productId);
      return ex
        ? prev.map((i) => i.productId === p.productId ? { ...i, qty: i.qty + 1 } : i)
        : [...prev, { ...p, qty: 1 }];
    });
    toast(`Đã thêm "${p.productName}" vào giỏ 🛒`);
  };

  const removeCart = (id) => {
    setCart((p) => p.filter((i) => i.productId !== id));
    toast("Đã xóa khỏi giỏ hàng", "error");
  };

  const updateQty = (id, d) =>
    setCart((p) => p.map((i) => i.productId === id ? { ...i, qty: Math.max(1, i.qty + d) } : i));

  const cartTotal = cart.reduce((s, i) => s + i.price * i.qty, 0);
  const cartCount = cart.reduce((s, i) => s + i.qty, 0);
  const clearCart = () => setCart([]);

  return { cart, addToCart, removeCart, updateQty, cartTotal, cartCount, clearCart };
}
