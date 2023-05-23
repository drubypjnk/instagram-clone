using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.Serivce
{
    public class PhotoService
    {
        public static Photo getPhoto(int post_id)
        {
            Photo photo = new Photo();
            InstaContext context = new InstaContext();
            photo = context.Photos.FirstOrDefault(x => x.PostId == post_id && x.DeleteFlag == false);
            return photo;
        }

        public static void addPostImage(int post_id, string content)
        {
            InstaContext context = new InstaContext();
            Photo photo = new Photo();
            photo.PostId = post_id;
            photo.Content = content;
            photo.CreateDate = DateTime.Now;
            photo.DeleteFlag = false;
            context.Photos.Add(photo);
            context.SaveChanges();
        }

        public static Photo getAvatar(int? photo_id)
        {
            InstaContext context = new InstaContext();
            Photo photo = context.Photos.FirstOrDefault(x => x.PhotoId == photo_id && x.DeleteFlag == false);
            return photo;
        }
    }
}
