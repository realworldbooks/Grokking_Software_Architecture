/**
 * ARCHITECTURE NOTE: A simple data entity. The behavior regarding 
 * how items are priced and discounted is encapsulated inside the 
 * Rich 'Order' model, not here.
 */
class Item {
    constructor(id = 0, price = 0.0, quantity = 0) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }
}
module.exports = Item;