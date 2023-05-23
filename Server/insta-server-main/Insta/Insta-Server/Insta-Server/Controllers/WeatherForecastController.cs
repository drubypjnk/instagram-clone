using Insta_Server.Models;
using Insta_Server.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

namespace Insta_Server.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        private static readonly string[] Summaries = new[]
        {
        "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
    };

        private readonly ILogger<WeatherForecastController> _logger;

        private readonly IHubContext<NotificationHub> _hubContext;
        public WeatherForecastController(ILogger<WeatherForecastController> logger, IHubContext<NotificationHub> hubContext)
        {
            _logger = logger;
            _hubContext = hubContext;
        }

        [HttpGet(Name = "GetWeatherForecast")]
        public IEnumerable<WeatherForecast> Get()
        {
            Notify notify = new Notify()
            {
                NotifyId = 0,
                CreateDate = DateTime.Now,
                DeleteFlag = true,
                NotifyContent = "Quang has mention you in a comment",
                NotifyTitle = "Nobita",
                NotifyType = (int?)NotificationType.FollowNotification,
                Status = 1,
                UserId = "quang",
                User = new Person()
                {
                    Password = "https://th.bing.com/th/id/OIP.QcCkNFcJNd1yCHiU7AGcwgHaGy?pid=ImgDet&rs=1"
                }
                
                 
            };
            NotificationService.NotificateUser(notify, "user7", _hubContext);
            return Enumerable.Range(1, 5).Select(index => new WeatherForecast
            {
                Date = DateTime.Now.AddDays(index),
                TemperatureC = Random.Shared.Next(-20, 55),
                Summary = Summaries[Random.Shared.Next(Summaries.Length)]
            })
            .ToArray();
        }
    }
}