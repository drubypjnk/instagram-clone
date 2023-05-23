using Insta_Server.DTO;
using Insta_Server.Models;

namespace Insta_Server.Serivce
{
    public class CommentService
    {
        public static List<CommentDTO> getCommentsByPost(int post_id)
        {
            InstaContext context = new InstaContext();
            List<Comment> comments = new List<Comment>();
            List<CommentDTO> list = new List<CommentDTO>();
            comments = context.Comments.Where(x => x.PostId == post_id && x.DeleteFlag == false).OrderByDescending(x => x.CreatedDate).ToList();
            if(comments.Count > 0)
            {
                foreach(Comment cmt in comments)
                {
                    CommentDTO comment = new CommentDTO();
                    comment.CommentId = cmt.CommentId;
                    Person person = context.People.FirstOrDefault(x => x.UserId.Equals(cmt.Author));
                    comment.Author = person.Username;
                    Photo photo = PhotoService.getAvatar(person.PhotoId);
                    comment.Avatar = photo.Content;
                    comment.Content = cmt.Content;
                    comment.VisibilityId = cmt.VisibilityId;
                    list.Add(comment);
                }
            }
            return list;
        }

        public static void addComment(int post_id, string user_id, string content) 
        { 
            InstaContext context = new InstaContext();
            Comment comment = new Comment();
            comment.PostId = post_id;
            comment.Author = user_id;
            comment.Content = content;
            comment.CreatedDate = DateTime.Now;
            comment.DeleteFlag = false;
            context.Comments.Add(comment);
            context.SaveChanges();
        }
    }
}
