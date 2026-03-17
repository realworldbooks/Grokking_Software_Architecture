const express = require('express');
const router = express.Router();

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
 * $ref: '#/components/schemas/ShippingRequest'
 * responses:
 * 200:
 * description: The calculated shipping cost.
 */
router.post('/calculate-shipping', (req, res) => {
    const { productId } = req.body;
    
    if (productId && productId.startsWith('DIGITAL')) {
        return res.status(400).json({ error: 'Digital items do not require shipping.' });
    }
    
    res.json(5.99);
});

module.exports = router;
