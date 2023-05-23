using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.SignalR;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Services;

namespace SE1611_NET_Group2_Project.Pages.api
{
    public class MessageModel : PageModel
    {
        private readonly InstaContext _context;
        private IHubContext<MessageHub> _messageHubContext;
        private IHubContext<NotificationHub> _hubContext;
        public MessageModel(InstaContext context, IHubContext<MessageHub> messageHubContext, IHubContext<NotificationHub> hubContext)
        {
            _context = context;
            _messageHubContext = messageHubContext;
            _hubContext = hubContext;
        }
        public async Task OnPostAsync(int roomId, string messageContent)
        {
            string sender = HttpContext.Session.GetString("user_id");
            bool status = await MessageService.SendMessages(sender, roomId, messageContent);
            if(status)
            {
                List<RoomMember> members = RoomMemberService.getMemberInRoom(roomId);
                foreach (RoomMember m in members)
                {
                    _messageHubContext.Clients.Group(m.Member).SendAsync("ReceiveMessage", new { sender, roomId, messageContent });
                }
                NotificationService.Notificate(NotificationType.MessageNotification, sender, 0, roomId + "", _hubContext);
            }
        }
    }
}
