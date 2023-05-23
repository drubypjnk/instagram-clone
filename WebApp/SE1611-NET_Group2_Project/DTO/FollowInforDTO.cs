namespace SE1611_NET_Group2_Project.DTO
{
    public class FollowInforDTO
    {   public int followId { get; set; } 
        public string UserId { get; set; }
        public string UserName { get; set; } = null!;
        public string FullName { get; set; } = null!;
        public string avatar { get; set; } = null!;
        public bool flag { get; set; }  
       
    }
}
