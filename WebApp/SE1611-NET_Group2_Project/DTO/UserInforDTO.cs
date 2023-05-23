namespace SE1611_NET_Group2_Project.DTO
{
    public class UserInforDTO
    {
        public string UserId { get; set; } 
        public string? Username { get; set; }
        public string? FullName { get; set; }
        public string? Desription { get; set; }
        public int? Age { get; set; }
        public int? Gender { get; set; }
        public string? Location { get; set; }
        public string? Email { get; set; }
        public bool? IsPrivate { get; set; }
        public PhotoInforDTO ? avartarImage { get; set; }
        public List<PostInforDTO> ? listPost{ get; set; }
        public List<FollowerInforDTO>? listFollower{ get; set; }
        public List<FollowerInforDTO>? listFollowing{ get; set; }
    }
}
