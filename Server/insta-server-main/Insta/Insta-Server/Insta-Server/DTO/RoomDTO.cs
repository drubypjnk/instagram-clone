using Insta_Server.Models;

namespace Insta_Server.DTO
{
    public class RoomDTO
    {
        public Room room { get; set; }
        public RoomUserDTO receiver { get; set; }
        public Message lastMassage { get; set; }
    }
}
