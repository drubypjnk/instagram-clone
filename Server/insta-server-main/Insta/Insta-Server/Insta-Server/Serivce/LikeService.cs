using Insta_Server.DTO;
using Insta_Server.Models;

namespace Insta_Server.Serivce
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

        public static bool AddLikePost(int post_id, string user_id)
        {
            bool res = false;
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
                    res = true;

                    PostDTO postDTO = PostService.getPostById(post_id);
                    if(postDTO != null)
                    {
                        string owner = postDTO.UserId;
                        if (owner.Equals(user_id)) res = false;
                    }
                    
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
            return res;
        }
    }
}
