/**
 * ARCHITECTURAL NOTE: Clean File Separation
 * By moving our data structures into their own dedicated files, 
 * we make the codebase vastly easier to navigate. If another developer 
 * needs to see the shape of a CartItem, they don't have to hunt through 
 * business logic to find it.
 */
class CartItem {
    /**
     * @param {string} name 
     * @param {number} price 
     */
    constructor(name, price) {
        this.name = name;
        this.price = price;
    }
}

module.exports = CartItem;