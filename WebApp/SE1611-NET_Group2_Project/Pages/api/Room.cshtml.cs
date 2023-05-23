using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Services;
using System.Text.Json;
using System.Threading.Tasks;

namespace SE1611_NET_Group2_Project.Pages.api
{
    public class RoomModel : PageModel
    {
        public async Task<IActionResult> OnGetAsync(string userId )
        {
            return Content(JsonSerializer.Serialize(RoomService.getRooms(userId)));
        }
    }
}
