const Product = require('../domain/product');

class ProductRepositoryImpl {
    constructor() {
        this._productDatabase = {
            "WIDGET-99": new Product("WIDGET-99", "Standard Widget", false, 5.0, 19.99),
            "WIDGET-HEAVY": new Product("WIDGET-HEAVY", "Anvil", false, 50.0, 99.99),
            "DIGITAL-EBOOK-01": new Product("DIGITAL-EBOOK-01", "Architecture PDF", true, 0, 29.99)
        };
    }

    getById(productId) {
        return this._productDatabase[productId] || null;
    }
}

module.exports = ProductRepositoryImpl;