// --- "Before" Code: Low Maintainability ---

function processOrderBefore(cartItems) {
  let subtotal = 0;
  for (const item of cartItems) {
    subtotal += item.price;
  }

  // Apply a hardcoded 10% discount
  const discount = subtotal * 0.10;
  const totalAfterDiscount = subtotal - discount;

  // Apply a hardcoded 8% sales tax
  const tax = totalAfterDiscount * 0.08;
  const finalTotal = totalAfterDiscount + tax;

  return `(Before) Order processed! Your final total is $${finalTotal.toFixed(2)}`;
}

// --- "After" Code: High Maintainability ---

const DISCOUNT_RATE = 0.10;
const TAX_RATE = 0.08;

function calculateSubtotal(items) {
  return items.reduce((sum, item) => sum + item.price, 0);
}

function applyDiscount(amount, rate) {
  return amount * (1 - rate);
}

function addTax(amount, rate) {
  return amount * (1 + rate);
}

function processOrderAfter(cartItems) {
  const subtotal = calculateSubtotal(cartItems);
  const totalAfterDiscount = applyDiscount(subtotal, DISCOUNT_RATE);
  const finalTotal = addTax(totalAfterDiscount, TAX_RATE);

  return `(After) Order processed! Your final total is $${finalTotal.toFixed(2)}`;
}

// --- To make it runnable ---
const myCart = [{ price: 100 }, { price: 50 }];

console.log("--- Running Maintainability Example ---");
console.log(processOrderBefore(myCart));
console.log(processOrderAfter(myCart));
console.log("---------------------------------------");