using Insta_Server.DTO;
using Insta_Server.Models;
using MessagePack.Formatters;
using Microsoft.EntityFrameworkCore;
using System.Diagnostics.Metrics;

namespace Insta_Server.Serivce
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
        private static RoomDTO getRoom(string userId, Room room)
        {
            room.RoomMembers = null;
            RoomDTO roomDTO = new RoomDTO()
            {
                room = room,
                receiver = UserService.getUserRoom(context.RoomMembers.Select(u => new { u.MemberNavigation, u.RoomId }).FirstOrDefault(u => u.RoomId == room.RoomId && u.MemberNavigation.UserId != userId).MemberNavigation.UserId),
                lastMassage = context.Messages.FirstOrDefault(m => m.AuthorNavigation.Room.RoomId == room.RoomId)
            };
            return roomDTO;
        }
        public static RoomDTO getRoomByUsers(string userId1, string userId2)
        {
            List<Room> rooms = (context.RoomMembers.Where(u => u.MemberNavigation.UserId == userId1).Select(u => u.Room)).ToList();
            List<Room> rooms2 = (context.RoomMembers.Where(u => u.MemberNavigation.UserId == userId2).Select(u => u.Room)).ToList();
            Room room = null;
            foreach(Room r in rooms)
            {
                foreach(Room r2 in rooms2){
                    if(r2.RoomId == r.RoomId)
                    {
                        room = r2; break;
                    }
                }
            }
            if(room == null)
            {
                return CreateRoom(userId1, userId2);
            }
            else
            {
                return getRoom(userId1, room);
            }
        }

        private static RoomDTO CreateRoom(string userId1, string userId2)
        {
            Room room = new Room
            {
                RoomTitle = $"{userId1}, {userId2}",
                CreateDate = DateTime.Now,
                DeleteFlag = false
            };
            context.Rooms.Add(room);
            context.SaveChanges();
            Room newroom = context.Rooms.OrderByDescending(r => r.RoomId).ToList()[0];
            RoomMember mem1 = new RoomMember()
            {
                RoomId = newroom.RoomId,
                Member = userId1,
                LastMessage = 1,
                DeleteFlag = false
            };
            RoomMember mem2 = new RoomMember()
            {
                RoomId = newroom.RoomId,
                Member = userId2,
                LastMessage = 1,
                DeleteFlag = false
            };
            context.RoomMembers.Add(mem1);
            context.RoomMembers.Add(mem2);
            context.SaveChanges();
            return getRoom(userId1, newroom.RoomId);
        }
    }
}
