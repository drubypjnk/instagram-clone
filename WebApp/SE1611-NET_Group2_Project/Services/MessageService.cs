using Microsoft.AspNetCore.SignalR;
using Microsoft.EntityFrameworkCore;
using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.Services
{
    public class MessageService
    {
        public static InstaContext context = new InstaContext();
        public async static Task<IList<Message>> GetMessages(int roomId)
        {
            List<Int32> members = await context.RoomMembers.Where(m => m.RoomId == roomId).Select(m => m.Id).ToListAsync();
            List<Message> messages = await context.Messages.Where(m => members.Contains(m.Author)).Include(m => m.AuthorNavigation)
                //.Select(m => new Message
                //{
                //    MessageId = m.MessageId,
                //    CreatedDate = m.CreatedDate,
                //    Author = m.Author,
                //    Content = m.Content,
                //    ReactId = m.ReactId,
                //    DeleteFlag = m.DeleteFlag
                //})
                .ToListAsync();
            return messages;
        }

        public async static Task<Boolean> SendMessages(string userId, int roomId, string messageContent)
        {
            try
            {
                Message message = new Message()
                {
                    Content = messageContent,
                    CreatedDate = DateTime.Now,
                    Author = context.RoomMembers.FirstOrDefault(rm => rm.Room.RoomId == roomId && rm.Member == userId).Id,
                    DeleteFlag = false
                };
                context.Messages.Add(message);
                context.SaveChanges();
                return true;
            } catch (Exception e)
            {
                return false;
            }
            //List<RoomMember> members = context.RoomMembers.Where(rm => rm.Room.RoomId == roomId).ToList();
            //foreach (RoomMember m in members)
            //{
            //    _messageHubContext.Clients.Group(m.Member).SendAsync("ReceiveMessage", "");
            //}
            //await context.SaveChangesAsync();

            ////NotificationService.Notificate(NotificationType.MessageNotification, userId, 0, roomId + "", _hubContext);
            //return JsonSerializer({  });
        }
    }
}
