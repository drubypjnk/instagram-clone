namespace Insta_Server.DTO
{
    public class PostDTO
    {
        public int PostId { get; set; }
        public string? Title { get; set; }
        public string? Content { get; set; }
        public string? Image { get; set; }
        public string Username { get; set; }
        public string Avatar { get; set; }
        public string? UserId { get; set; }
        public string? Location { get; set; }
        public int TotalLike { get; set; }
        public bool? isLiked { get; set; }
    }
}
