const express = require('express');
const swaggerUi = require('swagger-ui-express');
const { createOrder } = require('./orderController');

const app = express();
app.use(express.json());

// Set up the exact same route as the C# version
app.post('/api/Order', createOrder);

// ARCHITECTURAL NOTE: Swagger Configuration.
// We enable Swagger to make the anti-pattern runnable and testable.
const swaggerDocument = {
    openapi: "3.0.0",
    info: {
        title: "Grokking Software Architecture: The Fat Controller / Anemic Domain",
        version: "v1",
        description: "Demonstrating the pitfalls of tight coupling and anemic models in JS."
    },
    paths: {
        "/api/Order": {
            post: {
                summary: "Create a new order",
                requestBody: {
                    required: true,
                    content: {
                        "application/json": {
                            schema: {
                                type: "object",
                                properties: {
                                    customerId: { type: "integer", default: 1 },
                                    items: {
                                        type: "array",
                                        items: {
                                            type: "object",
                                            properties: {
                                                itemId: { type: "integer", default: 1 },
                                                quantity: { type: "integer", default: 3 }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                responses: {
                    "200": { description: "Order Created" },
                    "400": { description: "Bad Request" }
                }
            }
        }
    }
};

// Explicitly override the default Swagger options
const swaggerOptions = {
    customSiteTitle: "Fat Controller / Anemic Domain Anti-Pattern",
    swaggerOptions: {
        // This stops Swagger from caching or falling back to the Petstore URL
        persistAuthorization: true,
        displayRequestDuration: true
    }
};

// Map Swagger UI to the root URL using the options
app.use('/', swaggerUi.serveFiles(swaggerDocument), swaggerUi.setup(swaggerDocument, swaggerOptions));

// Add a friendly redirect from the root so you don't get a "Cannot GET /" error
app.get('/', (req, res) => {
    res.redirect('/swagger/');
});

const PORT = 5000;

// Wrap the execution in a run() method
const run = () => {
    return new Promise((resolve) => {
        const server = app.listen(PORT, () => {
            console.log("--- FAT CONTROLLER / ANEMIC DOMAIN APP RUNNING (NODE.JS) ---");
            console.log(`Swagger UI available at: http://localhost:${PORT}/`);
            console.log("---------------------------------------------");
            
            // Return the server instance back to the menu
            resolve(server); 
        });
    });
};

// Export the run method so menu.js can find it
module.exports = { run };