using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.IO;
using System.Reflection;
using Chapter06.AiApiExample.Interfaces;
using Chapter06.AiApiExample.Services;

namespace Chapter06.AiApiExample
{
    /// <summary>
    /// The Execution Layer.
    /// Replaces Program.cs to maintain consistency with the rest of the book's architecture.
    /// Configures Swagger to act as the bridge between our code and the AI Agent.
    /// </summary>
    public class Demo
    {
       public static void Run()
        {
            Console.WriteLine("--- STARTING THE MODERN AI ARCHITECT DEMO ---");
            Console.WriteLine("Goal: Turn our C# codebase into a perfect LLM Prompt.");
            Console.WriteLine("Swagger UI will be available at: http://localhost:5000/swagger");
            Console.WriteLine("\n--> Open the URL above and read the descriptions.");
            Console.WriteLine("--> Notice how we are commanding the AI exactly how to behave!\n");
            
            var builder = WebApplication.CreateBuilder(Array.Empty<string>());

            builder.Services.AddControllers();
            builder.Services.AddSingleton<IProductRepository, Repositories.ProductRepository>();
            builder.Services.AddSingleton<IShippingCalculatorService, Services.ShippingCalculatorService>();
            builder.Services.AddSingleton<IOrderPricingService, Services.OrderPricingService>();

            builder.Services.AddSwaggerGen(c =>
            {
                var xmlFilename = $"{Assembly.GetExecutingAssembly().GetName().Name}.xml";
                var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFilename);
                c.IncludeXmlComments(xmlPath);
            });

            var app = builder.Build();

            app.UseSwagger();
            app.UseSwaggerUI();
            app.MapControllers();

            app.Run();
        }
    }
}