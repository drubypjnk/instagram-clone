using Insta_Server.Serivce;
using Insta_Server.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

namespace Insta_Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LikeController : ControllerBase
    {
        private readonly IHubContext<NotificationHub> _hubContext;

        public LikeController(IHubContext<NotificationHub> hubContext)
        {
            _hubContext = hubContext;
        }

        [HttpPost("/like/insert")]
        public void likePost(string user_id, int post_id)
        {
            bool res = Serivce.LikeService.AddLikePost(post_id, user_id);
            if(res)
            {
                NotificationService.Notificate(NotificationType.ReactNotification, user_id, post_id, null, _hubContext);
            }
        }
    }
}
