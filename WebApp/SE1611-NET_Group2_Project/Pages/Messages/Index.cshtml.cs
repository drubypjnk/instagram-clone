using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Serivce;
using SE1611_NET_Group2_Project.Services;

namespace SE1611_NET_Group2_Project.Pages.Messages
{
    public class IndexModel : PageModel
    {
        private readonly InstaContext _context;

        public IndexModel(InstaContext context)
        {
            _context = context;
        }
        public IList<Message> Messages { get; set; } = default!;
        public RoomDTO roomDTO { get; set; } = default!;
        public IList<Message> Rooms { get; set; } = default!;

        public async Task OnGetAsync(int roomId, string userId)
        {
            string user = HttpContext.Session.GetString("user_id");
            if(user == null || user.Equals(String.Empty))
            {
                Response.Redirect("/login/login");
                return;
            }

            ViewData["user_id"] = user;

            if (roomId != null && roomId != 0)
            {
                Messages = await MessageService.GetMessages(roomId);
                roomDTO = RoomService.getRoom(user, roomId);
            }
            else if (userId != null && !userId.Equals(String.Empty))
            {
                roomDTO = RoomService.getRoom(user, userId);
                Messages = await MessageService.GetMessages(roomDTO.room.RoomId);
            }
        }

    }
}
