/**
 * THE DOMAIN MODEL (Entity).
 * Represents the internal business reality (e.g., a Database Table).
 */
class Product {
    constructor(id, name, isDigital, weightInLbs, price) {
        this.id = id;
        this.name = name;
        this.isDigital = isDigital;
        this.weightInLbs = weightInLbs;
        this.price = price;
    }
}

module.exports = Product;