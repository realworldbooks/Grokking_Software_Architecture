const { processOrderBefore, processOrderAfter } = require('./shoppingCart');

const cart = [
    { name: "Laptop", price: 1000.00 },
    { name: "Mouse", price: 50.00 }
];

console.log("--- Maintainability Example: Shopping Cart Refactor ---");
console.log("Before Refactor:");
console.log(processOrderBefore(cart));

console.log("\nAfter Refactor:");
console.log(processOrderAfter(cart));