using After.Core;

namespace After.Application
{
    public class OrderService : IOrderService
    {
        private readonly IOrderRepository _orderRepo;
        private readonly ICustomerRepository _customerRepo;
        private readonly IEmailService _emailService;

        public OrderService(
            IOrderRepository orderRepo,
            ICustomerRepository customerRepo,
            IEmailService emailService)
        {
            _orderRepo = orderRepo;
            _customerRepo = customerRepo;
            _emailService = emailService;
        }

        public int CreateOrder(OrderRequest request)
        {
            // 1. Call down to Infrastructure to get domain objects
            var customer = _customerRepo.GetById(request.CustomerId);
            if (customer == null)
                throw new InvalidOperationException("Customer not found.");

            var order = new Order(customer.Email); // Create the "rich" model

            // 2. Use the rich model to do work
            foreach (var item in request.Items)
            {
                order.AddItem(item, customer);
            }

            // 3. Call down to Infrastructure
            _orderRepo.Save(order);
            _emailService.Send(order.CustomerEmail, "Order Confirmed!", "Your order is confirmed.");

            return order.Id;
        }
    }
}