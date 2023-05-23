using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using SE1611_NET_Group2_Project.Models;

using SE1611_NET_Group2_Project;
using SE1611_NET_Group2_Project.Models;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorPages();
builder.Services.AddDbContext<InstaContext>();
builder.Services.AddSession();
builder.Services.AddSignalR();
builder.Services.AddHttpContextAccessor();
builder.Services.AddDbContext<InstaContext>(Options => Options.UseSqlServer(builder.Configuration.GetConnectionString("ConString")));
builder.Services.AddDatabaseDeveloperPageExceptionFilter();
var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}
else
{
    app.UseDeveloperExceptionPage();
    app.UseMigrationsEndPoint();
}


app.UseHttpsRedirection();

app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapRazorPages();

app.UseSession();
app.MapHub<NotificationHub>("notificationHub");
app.MapHub<MessageHub>("/messageHub");
app.Run();
