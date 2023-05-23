namespace SE1611_NET_Group2_Project.DTO
{
    public class PostInforDTO
    {
        public int PostId { get; set; }
        public string? Title { get; set; }
        public string? Content { get; set; }
        public int Like { get; set; }
        public int Comment { get; set; }
        public PhotoInforDTO postImage { get; set; }
    }
}
