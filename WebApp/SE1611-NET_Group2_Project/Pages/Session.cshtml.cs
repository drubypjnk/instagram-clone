using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace SE1611_NET_Group2_Project.Pages.Users
{
    public class SessionModel : PageModel
    {
        public JsonResult OnGet()
        {
            string userId = HttpContext.Session.GetString("user_id");
            return new JsonResult(userId);

        }
    }
}
