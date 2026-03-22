// Chapter 04/JavaScript/4.4 Anti-Patterns/After-RichDomain/presentation/index.js
const express = require('express');
const swaggerUi = require('swagger-ui-express');

const { SqlOrderRepository, SqlCustomerRepository, SqlItemRepository } = require('../data_access/repositories');
const SmtpEmailService = require('../data_access/emailService');
const OrderService = require('../business_logic/orderService');
const { OrderRequest, OrderItemRequest } = require('../business_logic/orderRequest');

const app = express();
app.use(express.json());

// --- THE COMPOSITION ROOT ---
// ARCHITECTURE NOTE: Because the Presentation layer sits at the very 
// top of the 4-layer stack, it is responsible for wiring all the 
// layers together via Dependency Injection.
const orderRepo = new SqlOrderRepository();
const customerRepo = new SqlCustomerRepository();
const itemRepo = new SqlItemRepository();
const emailService = new SmtpEmailService();

const orderService = new OrderService(orderRepo, customerRepo, itemRepo, emailService);

// Instantiate your dedicated Controller class!
const orderController = new OrderController(orderService); 

/**
 * THE EXPRESS ROUTE (The Web Framework Boundary)
 * ARCHITECTURE NOTE: Express handles the HTTP parsing, but we immediately 
 * delegate the actual control flow to our pure-architecture OrderController.
 */
app.post('/order', (req, res) => {
    try {
        // 1. Map raw JSON into our explicit DTOs
        const items = req.body.items.map(i => new OrderItemRequest(i.itemId, i.quantity));
        const requestDto = new OrderRequest(req.body.customerId, items);

        // 2. Pass the DTO to your pure Controller class
        const jsonResponse = orderController.createOrder(requestDto); //
        
        // 3. Express returns the response headers and the JSON string
        res.status(200).type('json').send(jsonResponse); 
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});
// --- SWAGGER UI CONFIGURATION ---
// This provides an interactive UI identical to the C# and Java versions.
const swaggerDocument = {
    openapi: '3.0.0',
    info: { title: 'Layered Architecture API', version: '1.0.0' },
    paths: {
        '/order': {
            post: {
                summary: 'Create an order using the Item ID Lookup pattern',
                requestBody: {
                    content: {
                        'application/json': {
                            example: { customerId: 123, items: [{ itemId: 1, quantity: 1 }, { itemId: 2, quantity: 2 }] }
                        }
                    }
                },
                responses: { '200': { description: 'Success' } }
            }
        }
    }
};
app.use('/swagger', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

// --- STARTUP ---
const PORT = 3000;
app.listen(PORT, () => {
    console.log("--- Running Traditional 4-Layer Architecture ---");
    console.log("Fat Controller and Anemic Domain eliminated.");
    console.log(`API listening on port ${PORT}`);
    console.log(`Swagger UI available at http://localhost:${PORT}/swagger`);
});