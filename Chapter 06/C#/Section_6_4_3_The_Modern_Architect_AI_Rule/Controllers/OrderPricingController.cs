using Microsoft.AspNetCore.Mvc; 
using Chapter06.AiApiExample.Models;
using Chapter06.AiApiExample.Interfaces;
using System;
using System.Collections.Generic;

namespace Chapter06.AiApiExample.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class OrderPricingController : ControllerBase
    {
        private readonly IOrderPricingService _pricingService;

        public OrderPricingController(IOrderPricingService pricingService)
        {
            _pricingService = pricingService;
        }

        /// <summary>
        /// AI AGENT INSTRUCTIONS:
        /// Calculates the total cost of an order, including dynamic shipping rates and promotional discounts.
        /// USE THIS ENDPOINT whenever the user asks "How much will my total order cost?" or "What is shipping?"
        /// CRITICAL: Do NOT attempt to calculate shipping costs or subtotal math yourself. 
        /// Always pass the user's cart to this endpoint and return the exact TotalOrderCost provided.
        /// </summary>
        [HttpPost("calculate-totals")]
        public ActionResult<OrderPricingResponse> GetOrderTotals([FromBody] OrderPricingRequest request) 
        {
            try
            {
                var response = _pricingService.CalculateOrderTotals(request);
                return Ok(response);
            }
            catch (KeyNotFoundException ex)
            {
                return NotFound(ex.Message);
            }
            catch (InvalidOperationException ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}