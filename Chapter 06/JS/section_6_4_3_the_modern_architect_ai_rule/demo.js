const express = require('express');
const swaggerJsDoc = require('swagger-jsdoc');
const swaggerUi = require('swagger-ui-express');
const shippingController = require('./controllers/shippingController');
require('./models/shippingRequest'); // Ensure schema is loaded into memory

class Demo {
    static run() {
        console.log("--- STARTING AI-DRIVEN API DEMO ---");
        
        const app = express();
        app.use(express.json());

        const swaggerOptions = {
            swaggerDefinition: {
                openapi: '3.0.0',
                info: {
                    title: 'AI Shipping API',
                    version: '1.0.0',
                    description: 'An API designed specifically for AI Agents.'
                }
            },
            apis: ['./*.js'], // Scrape all JS files for annotations
        };

        const swaggerDocs = swaggerJsDoc(swaggerOptions);
        app.use('/swagger', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

        app.use('/api/shipping', shippingController);

        app.listen(3000, () => {
            console.log('Swagger UI available at: http://localhost:3000/swagger');
        });
    }
}

if (require.main === module) {
    Demo.run();
}

module.exports = Demo;