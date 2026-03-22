// Chapter 04/JavaScript/4.4 Anti-Patterns/After-RichDomain/presentation/controllers/orderController.js

/**
 * THE THIN CONTROLLER
 * ARCHITECTURE NOTE: This controller is finally cured of the "Fat 
 * Controller" anti-pattern. It has zero business logic, zero 
 * database logic, and zero validation rules. Its ONLY job is to 
 * translate an HTTP POST request into a Business Logic method call, 
 * and return an HTTP response (200 OK).
 * 
 * * * REAL-WORLD JAVASCRIPT NOTE: 
 * Vanilla JavaScript does not have a native "Controller" concept like C#. 
 * 
 * 1. Traditional Node Backends (Express/NestJS):
 * 
 * - In Express.js, a controller is usually just a simple callback 
 * function: `app.post('/orders', (req, res) => {...})`
 * 
 * - In NestJS, controllers are OOP classes with decorators, looking 
 * almost exactly like C# (.NET).
 * 
 * 2. Frontend Frameworks (React, Vue, Angular):
 * 
 * - You generally do not write backend controllers in these tools. 
 * Instead, you write UI "Components" that act as the presentation layer, 
 * which make HTTP fetch() calls to a separate backend API. 
 * 
 * - (Note: Angular heavily uses classes and Dependency Injection similar 
 * to this example, but applied to frontend architecture).
 * 
 * 3. Full-Stack Meta-Frameworks (Next.js, Nuxt):
 * 
 * - The OOP "Controller" class is usually replaced by API Routes 
 * (e.g., an exported async function inside `app/api/route.js`) or 
 * Server Actions. They serve the exact same architectural purpose, 
 * just using functional programming instead of classes.
 * 
 * * We use a plain class here to demonstrate the pure architectural pattern 
 * without forcing you to learn a specific framework!
 */
class OrderController {
    
    /**
     * @param {OrderService} orderService 
     */
    constructor(orderService) {
        this._orderService = orderService;
    }

    /**
     * Simulates an HTTP POST endpoint returning JSON
     * @param {OrderRequest} request 
     * @returns {string} Simulated JSON response
     */
    createOrder(request) {
        // Controller simply delegates work to the layer below it
        const orderId = this._orderService.createOrder(request);
        
        // Controller formats the HTTP response
        return JSON.stringify({ OrderId: orderId });
    }
}

module.exports = OrderController;