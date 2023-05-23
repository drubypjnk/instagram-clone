namespace Insta_Server.DTO
{
    public class NotificationItem
    {
        public  string content { get; set; }
        public string imageSource { get; set; }
        public bool isFollowNotification { get; set; }
        public string target { get; set; }
        public string userId { get; set; }
    }
}
