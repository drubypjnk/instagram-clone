namespace Insta_Server.DTO
{
    public class PostItemDTO
    {
        public int PostId { get; set; }
        public string? Title { get; set; }
        public string? Content { get; set; }
        public string? Image { get; set; }
        public string? UserId { get; set; }
        public bool? isDelete { get; set; }
    }
}
