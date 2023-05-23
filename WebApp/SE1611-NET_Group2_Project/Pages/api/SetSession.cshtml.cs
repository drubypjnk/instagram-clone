using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Services;

namespace SE1611_NET_Group2_Project.Pages.api
{
    public class SetSessionModel : PageModel
    {
        public async Task OnGetAsync(string userId)
        {
            HttpContext.Session.SetString("userId", userId);
        }
    }
}
