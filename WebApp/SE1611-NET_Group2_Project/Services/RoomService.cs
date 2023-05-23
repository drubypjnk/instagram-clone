using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Serivce;

namespace SE1611_NET_Group2_Project.Services
{
    public class RoomService
    {
        public static InstaContext context = new InstaContext();
        public static List<RoomDTO> getRooms(string userId)
        {
            List<Room> rooms = context.RoomMembers.Where(u => u.MemberNavigation.UserId == userId).Select(u => u.Room).ToList();
            List<RoomDTO> result = new List<RoomDTO>();
            foreach (Room r in rooms)
            {
                result.Add(getRoom(userId, r));
            }
            return result;
        }
        public static RoomDTO getRoom(string userId, int roomId)
        {
            Room room = context.Rooms.FirstOrDefault(r => r.RoomId == roomId);
            return getRoom(userId, room);
        }
        public static RoomDTO getRoom(string userId1, string userId2)
        {
            List<Room> rooms = (context.RoomMembers.Where(u => u.MemberNavigation.UserId == userId1).Select(u => u.Room)).ToList();
            List<Room> rooms2 = (context.RoomMembers.Where(u => u.MemberNavigation.UserId == userId2).Select(u => u.Room)).ToList();
            Room room = null;
            foreach (Room r in rooms)
            {
                foreach (Room r2 in rooms2)
                {
                    if (r2 != null && r != null && r2.RoomId == r.RoomId)
                    {
                        room = r2; break;
                    }
                }
            }
            if (room == null)
            {
                CreateRoom(userId1, userId2);
                return getRoom(userId1, userId2);
            }
            else
            {
                return getRoom(userId1, room);
            }
        }
        private static RoomDTO getRoom(string userId, Room room)
        {
            room.RoomMembers = null;
            RoomDTO roomDTO = new RoomDTO()
            {
                room = room,
                receiver = UserService.getUserRoom(context.RoomMembers.Select(u => new { u.MemberNavigation, u.RoomId }).FirstOrDefault(u => u.RoomId == room.RoomId && u.MemberNavigation.UserId != userId).MemberNavigation.UserId),
                lastMassage = context.Messages.OrderByDescending(m => m.CreatedDate).FirstOrDefault(m => m.AuthorNavigation.Room.RoomId == room.RoomId)
            };
            if(roomDTO.lastMassage != null)
            {
                roomDTO.lastMassage.AuthorNavigation = null;
            }
            return roomDTO;
        }
        private static void CreateRoom(string userId1, string userId2)
        {
            Room room = new Room
            {
                RoomTitle = "",
                CreateDate = DateTime.Now,
                DeleteFlag = false,
                RoomMembers = new List<RoomMember>()
                {
                    new RoomMember()
                    {
                        Member = userId1,
                        LastMessage = 1,
                        DeleteFlag = false
                    },
                    new RoomMember()
                    {
                        Member = userId2,
                        LastMessage = 1,
                        DeleteFlag = false
                    }
                }
            };
            context.Rooms.Add(room);
            context.SaveChanges();
        }
    }
}

