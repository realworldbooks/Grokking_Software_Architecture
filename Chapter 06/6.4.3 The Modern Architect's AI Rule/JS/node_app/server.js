const express = require('express');
const swaggerJsdoc = require('swagger-jsdoc');
const swaggerUi = require('swagger-ui-express');
const shippingRoutes = require('./routes/shipping');

const app = express();
app.use(express.json());

// Tell Swagger to scan ALL files in our models and routes folders for AI Prompts!
const options = {
  definition: { openapi: '3.0.0', info: { title: 'AI Shipping API', version: '1.0.0' } },
  apis: ['./routes/*.js', './models/*.js'], 
};

app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerJsdoc(options)));

// Plug in our isolated router
app.use('/api/shipping', shippingRoutes);

app.listen(3000, () => console.log('Server running on port 3000.'));
