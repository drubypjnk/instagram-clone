using Insta_Server.DTO;
using Insta_Server.Models;

namespace Insta_Server.Serivce
{
    public class MessageService
    {
        public static InstaContext context = new InstaContext();
        public static List<MessageDTO> GetMessages(int roomId)
        {
            List<MessageDTO> messageDTOs = new List<MessageDTO>();
            List<Int32> members = context.RoomMembers.Where(m => m.RoomId == roomId).Select(m => m.Id).ToList();
            List<Message> messages = context.Messages.Where(m => members.Contains(m.Author))
                .Select(m => new Message
                {
                    MessageId = m.MessageId,
                    CreatedDate = m.CreatedDate,
                    Author = m.Author,
                    Content = m.Content,
                    ReactId = m.ReactId,
                    DeleteFlag = m.DeleteFlag
                })
                .ToList();
            foreach (Message m in messages)
            {
                messageDTOs.Add(new MessageDTO
                {
                    message = m,
                    author = context.RoomMembers.FirstOrDefault(rm => rm.Id == m.Author).Member
                });
            }
            return messageDTOs;
        }
    }
}
