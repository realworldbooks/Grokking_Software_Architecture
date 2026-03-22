using Microsoft.AspNetCore.Mvc;
using After.BusinessLogic;

namespace After.Presentation.Controllers
{
    [ApiController]
    [Route("[controller]")]
    /// <summary>
    /// THE THIN CONTROLLER
    /// ARCHITECTURE NOTE: This controller is finally cured of the "Fat 
    /// Controller" anti-pattern. It has zero business logic, zero 
    /// database logic, and zero validation rules. Its ONLY job is to 
    /// translate an HTTP POST request into a Business Logic method call, 
    /// and return an HTTP response (200 OK).
    /// </summary>
    public class OrderController : ControllerBase
    {
        private readonly IOrderService _orderService;

        public OrderController(IOrderService orderService)
        {
            _orderService = orderService;
        }

        [HttpPost]
        public IActionResult CreateOrder(OrderRequest request)
        {
            // Controller simply delegates work to the layer below it
            var orderId = _orderService.CreateOrder(request);
            
            // Controller formats the HTTP response
            return Ok(new { OrderId = orderId });
        }
    }
}