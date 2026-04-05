using System;
using System.Collections.Generic;
using System.Linq;
using Chapter06.AiApiExample.Interfaces;
using Chapter06.AiApiExample.Models;

namespace Chapter06.AiApiExample.Services
{
    public class OrderPricingService : IOrderPricingService
    {
        private readonly IShippingCalculatorService _shippingCalculator;
        private readonly IProductRepository _productRepository;

        // Constructor Injection for both the Shipping Math and the Data Access
        public OrderPricingService(
            IShippingCalculatorService shippingCalculator, 
            IProductRepository productRepository)
        {
            _shippingCalculator = shippingCalculator;
            _productRepository = productRepository;
        }

        public OrderPricingResponse CalculateOrderTotals(OrderPricingRequest request)
        {
            if (request.Items == null || !request.Items.Any())
            {
                throw new InvalidOperationException("The cart is empty.");
            }

            decimal itemsSubtotal = 0m;
            decimal totalPhysicalWeight = 0m;

            // 1. Calculate the Items
            foreach (var cartItem in request.Items)
            {
                // DELEGATED DATA ACCESS: Ask the repository for the product
                var product = _productRepository.GetById(cartItem.ProductId);
                
                if (product == null)
                {
                    throw new KeyNotFoundException($"Product ID '{cartItem.ProductId}' could not be found.");
                }

                itemsSubtotal += (product.Price * cartItem.Quantity);

                if (!product.IsDigital)
                {
                    totalPhysicalWeight += (product.WeightInLbs * cartItem.Quantity);
                }
            }

            // 2. Delegate Shipping Calculation
            decimal shippingCost = _shippingCalculator.CalculateShippingCost(
                request.ZipCode, 
                totalPhysicalWeight, 
                itemsSubtotal
            );

            // 3. Construct the Response
            return new OrderPricingResponse
            {
                ItemsSubtotal = Math.Round(itemsSubtotal, 2),
                ShippingCost = Math.Round(shippingCost, 2),
                TotalOrderCost = Math.Round(itemsSubtotal + shippingCost, 2)
            };
        }
    }
}