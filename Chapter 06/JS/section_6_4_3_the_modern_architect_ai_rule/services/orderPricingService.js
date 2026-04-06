const OrderPricingResponse = require('../models/orderPricingResponse');

/**
 * THE ORCHESTRATION SERVICE.
 * AI INSTRUCTION: This class acts strictly as an orchestrator. It does NOT contain 
 * primary business math or direct data access. It relies entirely on 
 * ShippingCalculatorService and ProductRepository.
 */
class OrderPricingServiceImpl {
    constructor(shippingCalculator, productRepository) {
        this.shippingCalculator = shippingCalculator;
        this.productRepository = productRepository;
    }

    calculateOrderTotals(request) {
        if (!request.items || request.items.length === 0) {
            throw new Error("The cart is empty.");
        }

        let itemsSubtotal = 0;
        let totalPhysicalWeight = 0;

        // 1. Calculate the Items
        for (const cartItem of request.items) {
            const product = this.productRepository.getById(cartItem.productId);
            
            if (!product) {
                const err = new Error(`Product ID '${cartItem.productId}' could not be found.`);
                err.name = 'KeyNotFoundException';
                throw err;
            }

            itemsSubtotal += (product.price * cartItem.quantity);

            if (!product.isDigital) {
                totalPhysicalWeight += (product.weightInLbs * cartItem.quantity);
            }
        }

        // 2. Delegate Shipping Calculation
        const shippingCost = this.shippingCalculator.calculateShippingCost(
            request.zipCode, 
            totalPhysicalWeight, 
            itemsSubtotal
        );

        // 3. Construct the Response
        const response = new OrderPricingResponse();
        response.itemsSubtotal = parseFloat(itemsSubtotal.toFixed(2));
        response.shippingCost = parseFloat(shippingCost.toFixed(2));
        response.totalOrderCost = parseFloat((itemsSubtotal + shippingCost).toFixed(2));
        
        return response;
    }
}

module.exports = OrderPricingServiceImpl;