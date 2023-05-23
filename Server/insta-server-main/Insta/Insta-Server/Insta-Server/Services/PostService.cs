using Insta_Server.Models;

namespace Insta_Server.Services
{
    public class PostService
    {
        public static Post GetPost(int id)
        {
            InstaContext context= new InstaContext();
            return context.Posts.FirstOrDefault(x => x.PostId== id);
        }
        public static string getOwner(int postId)
        {
            InstaContext context = new InstaContext();
            Post p = context.Posts.FirstOrDefault(x => x.PostId == postId);
            if (p != null)
            {
                return p.Author;
            }
            else
            {
                return "";
            }
        }
    }
}
