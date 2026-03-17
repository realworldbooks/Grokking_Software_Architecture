const express = require('express');
const swaggerJsdoc = require('swagger-jsdoc');
const swaggerUi = require('swagger-ui-express');

const app = express();
app.use(express.json());

// 1. SWAGGER SETUP
const options = {
  definition: {
    openapi: '3.0.0',
    info: { title: 'AI Shipping API', version: '1.0.0' },
  },
  apis: ['./app.js'], // Tells Swagger to read the comments in this file
};
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerJsdoc(options)));

// 2. THE CONTROLLER & DTO (The AI's Tool & Form)
/**
 * @openapi
 * /api/shipping/calculate-shipping:
 * post:
 * summary: Calculates physical shipping cost.
 * description: USE THIS ENDPOINT whenever the user asks "How much is shipping?". Do NOT use for digital items.
 * requestBody:
 * required: true
 * content:
 * application/json:
 * schema:
 * type: object
 * properties:
 * productId:
 * type: string
 * description: The unique ID of the physical product. Do NOT send digital product IDs.
 * zipCode:
 * type: string
 * description: The destination zip code. Must be exactly 5 digits.
 * responses:
 * 200:
 * description: The calculated shipping cost.
 */
app.post('/api/shipping/calculate-shipping', (req, res) => {
    const { productId } = req.body;
    
    if (productId && productId.startsWith('DIGITAL')) {
        return res.status(400).json({ error: 'Digital items do not require shipping.' });
    }
    
    res.json(5.99);
});

app.listen(3000, () => console.log('Server running on port 3000. Check /api-docs for the AI UI!'));
