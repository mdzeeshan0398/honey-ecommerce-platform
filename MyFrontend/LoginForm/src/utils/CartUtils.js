export const calculateCartTotals = (cartItems,quantities) => {
    const subTotal = cartItems.reduce(
    (acc, product) => acc + product.price * quantities[product.id],
    0,
  );
  const shipping = subTotal === 0 ? 0.0 : 20;
  const tax = subTotal * 0.1; // 10%
  const total = subTotal + shipping + tax;
  return {subTotal,shipping,tax,total}
}