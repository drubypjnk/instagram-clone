using Insta_Server.DTO;
using Insta_Server.Models;
using Insta_Server.Serivce;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

namespace Insta_Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PostController : Controller
    {
        private readonly IHubContext<NotificationHub> _hubContext;

        public PostController(IHubContext<NotificationHub> hubContext)
        {
            _hubContext = hubContext;
        }

        [HttpGet("/post/all")]
        public JsonResult getListPost(string user_id)
        {
            List<PostDTO> posts = PostService.getListPost(user_id);
            return new JsonResult(posts);
        }

        [HttpGet("/post/manage")]
        public JsonResult getListPostToManage()
        {
            List<PostItemDTO> posts = PostService.getListPostToManage();
            return new JsonResult(posts);
        }

        [HttpPost("/post/add")]
        public void addPost(AddPostDTO addPost)
        {
            PostService.addPost(addPost);
        }

        [HttpPut("/post/update")]
        public void updatePost(int post_id, string content)
        {
            PostService.updatePost(post_id, content);
        }

        [HttpGet("/post/detail")]
        public JsonResult getPostDetail(int post_id)
        {
            PostDTO postDTO = PostService.getPostById(post_id);
            return new JsonResult(postDTO);
        }        
        [HttpGet("/photo/detail")]
        public JsonResult getPhotoById(int post_id)
        {
            Photo photo = PhotoService.GetPhotoByPost(post_id);
            if(photo != null)
            {
                return new JsonResult(photo.Content);
            }
            return null;
        }
        [HttpPost("/activePost")]
        public JsonResult activePost(string post_id)
        {
            Boolean check = true;
            check = PostService.activePost(post_id);
            return new JsonResult(check);
        }

        [HttpPost("/deActivePost")]
        public JsonResult deActivePost(string post_id)
        {
            Boolean check = true;
            check = PostService.deActivePost(post_id);
            return new JsonResult(check);
        }
    }
}
