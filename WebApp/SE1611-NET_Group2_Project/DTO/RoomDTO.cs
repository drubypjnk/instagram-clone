using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.DTO
{
    public class RoomDTO
    {
        public Room room { get; set; }
        public RoomUserDTO receiver { get; set; }
        public Message lastMassage { get; set; }
    }
}
