using Insta_Server.Models;

namespace Insta_Server.Services
{
    public class CommentService
    {
        public static List<String> GetNumberPeopleCommentedExcludeActor(int postId, string actor)
        {
            InstaContext instaContext = new();
            try
            {
                return instaContext.Comments.Where(x => x.PostId == postId)
                    .Select(x => x.Author)
                    .Distinct()
                    .Except(new[] { actor})
                    .ToList();
            } catch (Exception ex)
            {
                return new List<string>();
            }

        }
    }
}
