using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.DTO
{
    public class NotificationItem
    {
        public string content { get; set; }
        public int type { get; set; }
        public Post post { get; set; }
        public Person person { get; set; }
        public string timeAgo { get; set; }
    }
}
