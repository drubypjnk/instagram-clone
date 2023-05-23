using Insta_Server.DTO;
using Insta_Server.Models;
using Insta_Server.Serivce;
using Insta_Server.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

namespace Insta_Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CommentController : ControllerBase
    {
        private readonly IHubContext<NotificationHub> _hubContext;

        public CommentController(IHubContext<NotificationHub> hubContext)
        {
            _hubContext = hubContext;
        }

        [HttpGet("/comment/all")]
        public JsonResult getComments(int post_id)
        {
            List<CommentDTO> comments = Serivce.CommentService.getCommentsByPost(post_id);
            return new JsonResult(comments);
        }

        [HttpPost("/comment/add")]
        public void addComment(int post_id, string user_id, string content) 
        { 
            if(post_id > 0 && !string.IsNullOrEmpty(user_id) && !string.IsNullOrEmpty(content)) 
            {
                Serivce.CommentService.addComment(post_id, user_id, content);
            }
            PostDTO postDTO = Serivce.PostService.getPostById(post_id);
            if (postDTO != null)
            {
                string owner = postDTO.UserId;
                if (!owner.Equals(user_id))
                {
                    NotificationService.Notificate(NotificationType.CommentNotification, user_id, post_id, null, _hubContext);
                }
            }
        }
    }
}
