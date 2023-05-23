using Insta_Server.Models;

namespace Insta_Server.Serivce
{
    public class FollowService
    {
        public static bool isFollower(string user_id1, string user_id2)
        {
            InstaContext context = new InstaContext();
            Follow follow = context.Follows.FirstOrDefault(x => x.UserId.Equals(user_id1) && x.Follower.Equals(user_id2) && x.DeleteFlag == false);
            if (follow != null)
            {
                return true;
            }
            return false;
        }
    }
}
