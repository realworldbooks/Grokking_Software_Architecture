using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace After.Application
{
    public class OrderResponse
    {
        public int OrderId { get; set; }
        public decimal TotalPrice { get; set; }
        public string CustomerEmail { get; set; }
    }
}