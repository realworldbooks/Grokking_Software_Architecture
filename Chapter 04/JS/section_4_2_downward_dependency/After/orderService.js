/**
 * BUSINESS LOGIC LAYER.
 * ARCHITECTURE NOTE: No 'IOrderRepository' file is needed. 
 * JS uses Duck Typing. As long as the 'repo' object has a 
 * .save() method, the code will execute successfully.
 */
class OrderService {
    constructor(repo) { 
        this.repo = repo; 
    }

    saveOrder(order) {
        // Calls DOWNWARDS. The service doesn't care 
        // if this is SQL, NoSQL, or a Mock.
        this.repo.save(order);
    }
}

module.exports = OrderService;