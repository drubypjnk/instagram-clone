namespace Insta_Server.DTO
{
    public class PostInforDTO
    {
        public int PostId { get; set; }
        public string? Title { get; set; }
        public string? Content { get; set; }
        public PhotoInforDTO postImage { get; set; }
    }
}
