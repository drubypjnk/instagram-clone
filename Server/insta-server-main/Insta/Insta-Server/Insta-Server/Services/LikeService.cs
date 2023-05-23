using Insta_Server.Models;
using System.Security.Cryptography.Xml;

namespace Insta_Server.Services
{
    public class LikeService
    {
        public static List<String> GetNumberPeopleLiked(int postId, string actor)
        {
            try
            {
                InstaContext context = new InstaContext();
                return context.Likes
                    .Where(x => x.PostId == postId && (x.DeleteFlag == null || x.DeleteFlag == false))
                    .Select(x => x.UserId)
                    .Distinct()
                    .Except(new[] { actor })
                    .ToList();
            } catch (Exception ex) { 
                return new List<String>();
            }
        }
    }
}
