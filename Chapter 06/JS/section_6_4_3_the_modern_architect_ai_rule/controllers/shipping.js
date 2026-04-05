const express = require('express');
const router = express.Router();

/**
 * THE CONTROLLER (The AI's Tool)
 * * @swagger
 * /api/shipping/calculate-shipping:
 * post:
 * summary: Calculates the shipping cost for a specific physical item.
 * description: USE THIS ENDPOINT whenever the user asks 'How much is shipping?'. Do NOT use this endpoint for digital items.
 * requestBody:
 * required: true
 * content:
 * application/json:
 * schema:
 * $ref: '#/components/schemas/ShippingRequest'
 * responses:
 * 200:
 * description: The calculated shipping cost.
 * 400:
 * description: Digital items do not require shipping.
 */
router.post('/calculate-shipping', (req, res) => {
    const { productId, zipCode } = req.body;

    if (productId && productId.startsWith('DIGITAL')) {
        return res.status(400).send('Digital items do not require shipping.');
    }

    res.json(5.99);
});

module.exports = router;