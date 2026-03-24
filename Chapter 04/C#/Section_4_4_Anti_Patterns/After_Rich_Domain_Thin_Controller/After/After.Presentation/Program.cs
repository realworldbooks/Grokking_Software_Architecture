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
builder.Services.AddEndpointsApiExplorer(); //
builder.Services.AddSwaggerGen();           //

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
if (app.Environment.IsDevelopment()) // Matches appsettings.Development.json
{
    app.UseSwagger();
    app.UseSwaggerUI(); // This enables the UI at /swagger
}

app.MapControllers();

Console.WriteLine("--- Running Traditional 4-Layer Architecture ---");
Console.WriteLine("Fat Controller and Anemic Domain eliminated.");

app.Run();