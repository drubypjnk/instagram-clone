using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.Services
{
    public class RoomMemberService
    {
        public static InstaContext context = new InstaContext();
        public static List<RoomMember> getMemberInRoom(int roomId)
        {
            return context.RoomMembers.Where(rm => rm.Room.RoomId == roomId).ToList(); ;
        }
    }
}
