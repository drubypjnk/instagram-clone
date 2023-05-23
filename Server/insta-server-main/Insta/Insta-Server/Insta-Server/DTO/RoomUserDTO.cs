namespace Insta_Server.DTO
{
    public class RoomUserDTO
    {
        public string UserId { get; set; }
        public string? Username { get; set; }
        public string? FullName { get; set; }
        public PhotoInforDTO? avartarImage { get; set; }
    }
}
