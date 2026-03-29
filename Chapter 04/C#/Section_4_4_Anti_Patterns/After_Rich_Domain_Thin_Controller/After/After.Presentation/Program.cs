using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System;
using After.Application;
using After.DataAccess;
using After.Domain.Interfaces;

var builder = WebApplication.CreateBuilder(args);

// 1. REQUIRED FOR SWAGGER: Add the API explorer and generator
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer(); // Required for Swagger to discover API endpoints
builder.Services.AddSwaggerGen();           // Required for Swagger to generate the JSON documentation

// --- THE COMPOSITION ROOT ---
// ARCHITECTURE NOTE: Because the Presentation layer sits at the very 
// top of the 4-layer stack, it is responsible for wiring all the 
// layers together via Dependency Injection.
builder.Services.AddScoped<IOrderService, OrderService>();
builder.Services.AddScoped<IOrderRepository, SqlOrderRepository>();
builder.Services.AddScoped<ICustomerRepository, SqlCustomerRepository>();
builder.Services.AddScoped<IItemRepository, SqlItemRepository>();
builder.Services.AddScoped<IEmailService, SmtpEmailService>();

var app = builder.Build();

// 2. REQUIRED FOR SWAGGER: Enable the middleware in development mode
if (app.Environment.IsDevelopment()) 
{
    app.UseSwagger();
    app.UseSwaggerUI(c => 
    {
        // Explicitly tell the UI where the JSON file is
        c.SwaggerEndpoint("/swagger/v1/swagger.json", "Rich Domain /Thin Controller Layered Architecture API v1"); 
        
        // Load the UI at the root of the localhost URL
        c.RoutePrefix = string.Empty; 
    });
}

app.MapControllers();

Console.WriteLine("--- Running Rich Domain / Thin Controller Traditional 4-Layer Architecture ---");
Console.WriteLine("Fat Controller and Anemic Domain eliminated.");

await app.RunAsync();