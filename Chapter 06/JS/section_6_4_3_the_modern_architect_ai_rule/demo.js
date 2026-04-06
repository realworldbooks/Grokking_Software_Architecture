const path = require('path');
const express = require('express');
const swaggerJsdoc = require('swagger-jsdoc');
const swaggerUi = require('swagger-ui-express');

const ProductRepository = require('./repositories/productRespositoryImpl');
const ShippingService = require('./services/shippingCalculatorServiceImpl');
const PricingService = require('./services/orderPricingService');
const OrderController = require('./controllers/orderPricingController');

/**
 * The Execution Layer.
 * Replaces Program.cs to maintain consistency with the rest of the book's architecture.
 * Configures Swagger to act as the bridge between our code and the AI Agent.
 */
const run = () => {
    return new Promise((resolve) => {
        console.log("--- STARTING THE MODERN AI ARCHITECT DEMO (NODE.JS) ---");
        console.log("Goal: Turn our JS codebase into a perfect LLM Prompt.");
        console.log("Swagger UI will be available at: http://localhost:3000/api-docs");
        console.log("\n--> Open the URL above and read the descriptions.");
        console.log("--> Notice how we are commanding the AI exactly how to behave!\n");

        const app = express();
        app.use(express.json());

        // Dependency Injection / Singleton setup
        const repo = new ProductRepository();
        const shipping = new ShippingService();
        const pricing = new PricingService(shipping, repo);

        const options = {
            definition: {
                openapi: '3.0.0',
                info: {
                    title: 'Chapter 06 AI-Ready API',
                    version: '1.0.0',
                    description: 'The API bridge between business logic and LLM Agents.'
                },
                servers: [
                    {
                        url: "http://localhost:3000",
                        description: "Local Development Server"
                    }
                ]
            },
            // Path to the controller containing JSDoc/OpenAPI annotations
            // Note: Adjusted to ../../ to reach the root-level controllers directory if nested
            apis: [path.join(__dirname, './controllers/*.js')], 
        };

        const specs = swaggerJsdoc(options);
        app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(specs));
        
        // Mount the Controller
        app.use('/', OrderController(pricing));

        const server = app.listen(3000, () => {
            resolve(server);
        });
    });
};

module.exports = { run };