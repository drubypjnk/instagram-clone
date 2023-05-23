using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.Serivce
{
    public class PostService
    {
        public static List<PostDTO> getListPost(string user_id)
        {
            InstaContext context = new InstaContext();
            List<string> ids = new  List<string>();
            ids.Add(user_id);
            List<Follow> follows = context.Follows.Where(x => x.Follower.Equals(user_id.ToLower()) && x.DeleteFlag == false).ToList();
            foreach (Follow follow in follows)
            {
                ids.Add(follow.UserId);
            }
            List<Post> posts = context.Posts.Where(x => ids.Contains(x.Author) && x.VisibilityId != null && x.DeleteFlag == false).OrderByDescending(x => x.CreatedDate).ToList();
            List<PostDTO> list = new List<PostDTO>();
            foreach (Post post in posts)
            {
                PostDTO postInfor = new PostDTO();
                postInfor.PostId = post.PostId;
                postInfor.Content = post.Content;
                postInfor.Title = post.Title;
                postInfor.UserId = post.Author;
                Person person = context.People.FirstOrDefault(x => x.UserId.Equals(post.Author));
                postInfor.Username = person.Username;
                postInfor.Location = person.Location;
                postInfor.TotalLike = LikeService.getTotalLike(post.PostId);
                postInfor.isLiked = LikeService.isLikedPost(post.PostId, user_id);
                Photo photo = PhotoService.getPhoto(post.PostId);
                Photo avatar = PhotoService.getAvatar(person.PhotoId);
                if(photo != null)
                {
                    //postInfor.Image = photo.Content;
                    postInfor.Image = photo.Url;
                }
                if(avatar != null)
                {
                    //postInfor.Avatar = avatar.Content;
                    postInfor.Avatar = avatar.Url;
                }
                list.Add(postInfor);
            }

            return list;
        }

        public static void addPost(AddPostDTO addPost)
        {
            if (addPost != null)
            {
                InstaContext context = new InstaContext();
                Post post = new Post();
                post.Title = addPost.Title;
                post.Content = addPost.Content;
                post.Author = addPost.UserId;
                post.CreatedDate = DateTime.Now;
                post.DeleteFlag = false;
                post.VisibilityId = 1;
                context.Posts.Add(post);
                context.SaveChanges();

                PhotoService.addPostImage(post.PostId, addPost.Image);
            }
        }

        public static void updatePost(int post_id, string content)
        {
            InstaContext context = new InstaContext();
            Post post = context.Posts.Find(post_id);
            if (post != null)
            {
                post.Content = content;
                post.UpdateDate = DateTime.Now;
                context.Posts.Update(post);
                context.SaveChanges();
            }
        }

        public static PostDTO getPostById(int post_id)
        {
            InstaContext context = new InstaContext();
            Post post = context.Posts.FirstOrDefault(x => x.PostId == post_id && x.VisibilityId != null && x.DeleteFlag == false);
            if(post != null)
            {
                PostDTO postInfor = new PostDTO();
                postInfor.PostId = post.PostId;
                postInfor.Content = post.Content;
                postInfor.Title = post.Title;
                postInfor.UserId = post.Author;
                Person person = context.People.FirstOrDefault(x => x.UserId.Equals(post.Author));
                postInfor.Username = person.Username;
                postInfor.Location = person.Location;
                postInfor.TotalLike = LikeService.getTotalLike(post.PostId);
                postInfor.isLiked = LikeService.isLikedPost(post.PostId, person.UserId);
                Photo photo = PhotoService.getPhoto(post.PostId);
                Photo avatar = PhotoService.getAvatar(person.PhotoId);
                if (photo != null)
                {
                    postInfor.Image = photo.Content;
                }
                if (avatar != null)
                {
                    postInfor.Avatar = avatar.Content;
                }
                return postInfor;
            }
            return null;
        }
    }
}
