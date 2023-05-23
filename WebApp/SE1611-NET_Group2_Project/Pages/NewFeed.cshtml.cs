using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.SignalR;
using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Serivce;
using SE1611_NET_Group2_Project.Services;
using System.Text.Json;

namespace SE1611_NET_Group2_Project.Pages
{
    public class NewFeedModel : PageModel
    {
        private readonly IHubContext<NotificationHub> _hubContext;

        public NewFeedModel(IHubContext<NotificationHub> hubContext)
        {
            _hubContext = hubContext;
        }

        [FromRoute(Name = "id")]
        public int? PostId { get; set; }

        public void OnGet()
        {
            //string user_id = "user"; //drubypjnk
            string user_id = HttpContext.Session.GetString("user_id");
            if(!String.IsNullOrEmpty(user_id))
            {
                List<PostDTO> listPost = Serivce.PostService.getListPost(user_id);
                List<CommentDTO> listComments = new List<CommentDTO>();
                foreach (PostDTO post in listPost)
                {
                    listComments.AddRange(Serivce.CommentService.getCommentsByPost(post.PostId));
                }

                ViewData["ListComment"] = listComments;
                ViewData["ListPost"] = listPost;
                ViewData["userId"] = user_id;
            } else
            {
                RedirectToPage("/Login/login");
            }
        }

        [HttpGet("/comment/all")]
        public JsonResult OnGetGetComments(int post_id)
        {
            Console.WriteLine("POST ID: " + post_id);
            List<CommentDTO> comments = Serivce.CommentService.getCommentsByPost(post_id);
            String text = "";
            foreach(CommentDTO comment in comments)
            {
                text += "<div style='margin-top:10px; margin-bottom:10px'>" + "<a style='color: black; text-decoration: none' href='/UserProfile/" + comment.AuthorId + "'>" + "<strong>" +comment.Author + "</strong>" + "</a>" + " " + comment.Content + "</div>";
            }
            //return new JsonResult(comments);
            return new JsonResult(text);
        }

        [HttpPost("/comment/add")]
        public JsonResult OnPostAddComment(int post_id, string user_id, string content)
        {
            if(!String.IsNullOrEmpty(user_id))
            {
                if (post_id > 0 && !string.IsNullOrEmpty(user_id) && !string.IsNullOrEmpty(content))
                {
                    Serivce.CommentService.addComment(post_id, user_id, content);
                }
                List<CommentDTO> comments = Serivce.CommentService.getCommentsByPost(post_id);
                String text = "";
                foreach (CommentDTO comment in comments)
                {
                    text += "<div style='margin-top:10px; margin-bottom:10px'>" + "<a style='color: black; text-decoration: none' href='/UserProfile/" + comment.Author + "'>" + "<strong>" + comment.Author + "</strong>" + "</a>" + " " + comment.Content + "</div>";
                }
                //return new JsonResult(comments);
                return new JsonResult(text);
            }
            return new JsonResult(null);
        }

        [HttpPost("/like/insert")]
        public void OnPostLikePost(string user_id, int post_id)
        {
            if(!String.IsNullOrEmpty(user_id))
            {
                Serivce.LikeService.AddLikePost(post_id, user_id);
            }
            if (!String.IsNullOrEmpty(user_id))
            {
                List<PostDTO> listPost = Serivce.PostService.getListPost(user_id);
                List<CommentDTO> listComments = new List<CommentDTO>();
                foreach (PostDTO post in listPost)
                {
                    listComments.AddRange(Serivce.CommentService.getCommentsByPost(post.PostId));
                }

                ViewData["ListComment"] = listComments;
                ViewData["ListPost"] = listPost;
                ViewData["userId"] = user_id;
            }
        }
    }
}
