using GoodWay.Application;
using Microsoft.AspNetCore.Mvc;
using System.Runtime.InteropServices;

namespace GoodWay.WebAPI
{
    [ApiController]
    [Route("[controller]")]
    public class OrderController : ControllerBase
    {
        private readonly IOrderService _orderService;

        // The dependency is injected!
        public OrderController(IOrderService orderService)
        {
            _orderService = orderService;
        }

        [HttpPost]
        public IActionResult CreateOrder(OrderRequest request)
        {
            try
            {
                Console.WriteLine("(API) Received request...");
                var orderId = _orderService.CreateOrder(request);
                Console.WriteLine("(API) Request finished successfully.");
                return Ok(new { OrderId = orderId });
            }
            catch (Exception ex)
            {
                // Simple error handling
                Console.WriteLine($"(API) Error: {ex.Message}");
                return BadRequest(ex.Message);
            }
        }
    }
}