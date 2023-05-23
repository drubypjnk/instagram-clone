using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.Serivce
{
    public class LikeService
    {
        public static int getTotalLike(int post_id)
        {
            InstaContext context = new InstaContext();
            List<Like> likes = context.Likes.Where(x => x.PostId == post_id && x.DeleteFlag == false).ToList();
            return likes.Count;
        }

        public static bool isLikedPost(int post_id, string user_id)
        {
            InstaContext context = new InstaContext();
            Like likedPost = context.Likes.FirstOrDefault(x => x.UserId.Equals(user_id) && x.PostId == post_id && x.DeleteFlag == false);
            if (likedPost == null)
            {
                return false;
            }
            return true;
        }

        public static void AddLikePost(int post_id, string user_id)
        {
            using (var context = new InstaContext())
            {
                Like like = context.Likes.FirstOrDefault(x => x.UserId.Equals(user_id) && x.PostId == post_id);
                if (like == null)
                {
                    Like likePost = new Like();
                    likePost.UserId = user_id;
                    likePost.PostId = post_id;
                    likePost.DeleteFlag = false;
                    context.Likes.Add(likePost);
                    context.SaveChanges();
                }
                else
                {
                    if (!isLikedPost(post_id, user_id))
                    {
                        like.DeleteFlag = false;
                        context.Likes.Update(like);
                    }
                    else
                    {
                        like.DeleteFlag = true;
                        context.Likes.Update(like);
                    }
                    context.SaveChanges();
                }
            }
        }
    }
}
