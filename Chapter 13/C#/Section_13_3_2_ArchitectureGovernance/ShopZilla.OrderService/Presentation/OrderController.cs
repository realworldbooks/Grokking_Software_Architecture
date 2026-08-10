using Microsoft.AspNetCore.Mvc;
using ShopZilla.OrderService.Domain;
using ShopZilla.OrderService.Infrastructure;

namespace ShopZilla.OrderService.Presentation
{
    /// <summary>
    /// The HTTP API controller that exposes Order operations to clients.
    ///
    /// This is the Presentation layer - the outermost ring of our
    /// architecture. It depends on the Application/Domain layers below it.
    ///
    /// ARCHITECTURAL RULE: This class must:
    ///   1. Inherit from BaseController (enforced by fitness function)
    ///   2. End with the "Controller" suffix (enforced by fitness function)
    ///   3. Reside in the Presentation namespace (enforced by fitness function)
    ///
    /// If any of these rules are violated, the CI pipeline fails the build.
    /// </summary>
    [Route("api/[controller]")]
    public class OrderController : BaseController
    {
        private readonly OrderRepository _repository;

        public OrderController(OrderRepository repository)
        {
            _repository = repository;
        }

        /// <summary>
        /// GET /api/order/{id} - Retrieves a single order by ID.
        /// </summary>
        [HttpGet("{id}")]
        public async Task<IActionResult> GetById(Guid id)
        {
            var order = await _repository.GetByIdAsync(id);
            if (order == null)
                return NotFound();

            return OkResult(order);
        }

        /// <summary>
        /// POST /api/order - Creates a new order.
        /// </summary>
        [HttpPost]
        public async Task<IActionResult> Create([FromBody] CreateOrderRequest request)
        {
            var order = new Order(request.CustomerName, request.TotalAmount);
            await _repository.SaveAsync(order);
            return CreatedAtAction(nameof(GetById), new { id = order.Id }, order);
        }

        /// <summary>
        /// GET /api/order/customer/{name} - Retrieves all orders for a customer.
        /// </summary>
        [HttpGet("customer/{name}")]
        public async Task<IActionResult> GetByCustomer(string name)
        {
            var orders = await _repository.GetByCustomerAsync(name);
            return OkResult(orders);
        }
    }

    /// <summary>
    /// The request DTO for creating a new order.
    /// </summary>
    public class CreateOrderRequest
    {
        public string CustomerName { get; set; } = string.Empty;
        public decimal TotalAmount { get; set; }
    }
}