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

/**
 * THE THIN CONTROLLER
 * ARCHITECTURE NOTE: This route is cured of the "Fat Controller" anti-pattern. 
 * Its ONLY job is to translate an HTTP POST request into a Business Logic call.
 */
app.post('/order', (req, res) => {
    try {
        // 1. Map raw JSON into our explicit DTOs
        const items = req.body.items.map(i => new OrderItemRequest(i.itemId, i.quantity));
        const requestDto = new OrderRequest(req.body.customerId, items);

        // 2. Delegate to the Business Logic layer
        const orderId = orderService.createOrder(requestDto);
        
        // 3. Return the response
        res.status(200).json({ orderId: orderId });
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