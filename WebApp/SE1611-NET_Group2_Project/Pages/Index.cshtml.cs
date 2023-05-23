using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace SE1611_NET_Group2_Project.Pages
{
    public class IndexModel : PageModel
    {
        private readonly ILogger<IndexModel> _logger;

        public IndexModel(ILogger<IndexModel> logger)
        {
            _logger = logger;
        }

        public void OnGet()
        {
            string userId = HttpContext.Session.GetString("user_id");
            if(userId == null)
            {
                Response.Redirect("/Login/login");
            } else
            {
                Response.Redirect("/NewFeed");
            }
        }
    }
}