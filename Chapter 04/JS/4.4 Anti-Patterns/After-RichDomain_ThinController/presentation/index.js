// Chapter 04/JavaScript/4.4 Anti-Patterns/After-RichDomain/presentation/index.js

const { SqlOrderRepository, SqlCustomerRepository } = require('../data_access/repositories');
const { SmtpEmailService } = require('../data_access/emailService');
const OrderService = require('../business_logic/orderService');
const OrderRequest = require('../business_logic/orderRequest');
const Item = require('../domain_models/item');
const OrderController = require('./controllers/orderController');

function main() {
    // --- THE COMPOSITION ROOT ---
    // ARCHITECTURE NOTE: Because the Presentation layer sits at the very 
    // top of the 4-layer stack, it is responsible for wiring all the 
    // layers together via Dependency Injection.

    // 1. Instantiate the Data Access Layer (Infrastructure)
    const orderRepo = new SqlOrderRepository();
    const customerRepo = new SqlCustomerRepository();
    const emailService = new SmtpEmailService();

    // 2. Inject Data Access into the Business Logic Layer
    const orderService = new OrderService(orderRepo, customerRepo, emailService);

    // 3. Inject Business Logic into the Presentation Layer
    const app = new OrderController(orderService);

    console.log("--- Running Traditional 4-Layer Architecture ---");
    console.log("Fat Controller and Anemic Domain eliminated.");

    // --- Simulate an incoming HTTP request ---
    const item1 = new Item();
    item1.price = 100.0;
    item1.quantity = 1;

    const item2 = new Item();
    item2.price = 50.0;
    item2.quantity = 2;

    const request = new OrderRequest(123, [item1, item2]);

    // Execute the controller endpoint
    const response = app.createOrder(request);
    console.log(`HTTP 200 OK: ${response}`);
}

main();