using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System;
using After.BusinessLogic;
using After.DataAccess; 

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();

// --- THE COMPOSITION ROOT ---
// ARCHITECTURE NOTE: Because the Presentation layer sits at the very 
// top of the 4-layer stack, it is responsible for wiring all the 
// layers together via Dependency Injection.
builder.Services.AddScoped<IOrderService, OrderService>();
builder.Services.AddScoped<IOrderRepository, SqlOrderRepository>();
builder.Services.AddScoped<ICustomerRepository, SqlCustomerRepository>();
builder.Services.AddScoped<IEmailService, SmtpEmailService>();

var app = builder.Build();

app.MapControllers();

Console.WriteLine("--- Running Traditional 4-Layer Architecture ---");
Console.WriteLine("Fat Controller and Anemic Domain eliminated.");

app.Run();