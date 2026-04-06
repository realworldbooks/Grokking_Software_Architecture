const express = require('express');
const router = express.Router();

/**
 * @openapi
 * /api/OrderPricing/calculate-totals:
 *   post:
 *     summary: Calculates the total cost of an order
 *     description: >
 *       AI AGENT INSTRUCTIONS:
 *       Calculates the total cost of an order, including dynamic shipping rates.
 *       USE THIS ENDPOINT whenever the user asks "How much will my total order cost?"
 *       CRITICAL: Do NOT attempt to calculate shipping costs or subtotal math yourself.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               zipCode:
 *                 type: string
 *                 example: "12211"
 *               items:
 *                 type: array
 *                 items:
 *                   type: object
 *                   properties:
 *                     productId:
 *                       type: string
 *                     quantity:
 *                       type: integer
 *             example:
 *               zipCode: "12211"
 *               items:
 *                 - productId: "WIDGET-99"
 *                   quantity: 2
 *                 - productId: "DIGITAL-EBOOK-01"
 *                   quantity: 1
 *     responses:
 *       200:
 *         description: The final authoritative calculated totals.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 itemsSubtotal:
 *                   type: number
 *                 shippingCost:
 *                   type: number
 *                 totalOrderCost:
 *                   type: number
 */
module.exports = (pricingService) => {
    // Note: The route here should match the path defined in the @openapi tag
    router.post('/api/OrderPricing/calculate-totals', (req, res) => {
        try {
            const response = pricingService.calculateOrderTotals(req.body);
            res.json(response);
        } catch (error) {
            const statusCode = error.name === 'KeyNotFoundException' ? 404 : 400;
            res.status(statusCode).send(error.message);
        }
    });
    return router;
};