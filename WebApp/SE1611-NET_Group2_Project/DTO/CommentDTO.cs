namespace SE1611_NET_Group2_Project.DTO
{
    public class CommentDTO
    {
        public int CommentId { get; set; }
        public string? Content { get; set; }
        public string Author { get; set; } = null!;
        public string AuthorId { get; set; } = null!;
        public string Avatar { get; set; }
        public int? VisibilityId { get; set; }
    }
}
