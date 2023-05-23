using SE1611_NET_Group2_Project.Models;
using System.Security.Cryptography.Xml;

namespace SE1611_NET_Group2_Project.Services
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
            }
            catch (Exception ex)
            {
                return new List<String>();
            }
        }
    }
}
