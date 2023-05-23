using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Serivce;
using System.Security.Claims;

namespace SE1611_NET_Group2_Project.Pages.Login
{
    public class LoginModel : PageModel
    {
        [BindProperty]
        public string Username { get; set; }
        
        [BindProperty]
        public string Password { get; set; }

        
        [BindProperty]
        public string Msg { get; set; }
        public void OnGet()
        {
        }
        public IActionResult OnGetLogout()
        {
            HttpContext.Session.Remove("user_id");
            HttpContext.Session.Remove("userName");
            HttpContext.Session.Remove("avatar");
            return RedirectToPage("/Login/Login");
        }
        public IActionResult OnPost(String Username, String Password)
        {
            UserService userService = new UserService();
            Person u = userService.GetUser(Username, Password);
            if (u == null)
            {
                // Login failed, set error message in TempData
                TempData["ErrorMessage"] = "Invalid username or password";
                return RedirectToPage("/Login/Login");
            }
            else
            {
                if (u.UserId.Equals("user6"))
                {
                    ClaimsIdentity identity = (ClaimsIdentity)User.Identity;

                    // Add the role claim to the user's identity
                    identity.AddClaim(new Claim(ClaimTypes.Role, "user6"));

                    // Update the user's identity in the HttpContext
                    HttpContext.User = new ClaimsPrincipal(identity);
                }
                HttpContext.Session.SetString("user_id", u.UserId);
                HttpContext.Session.SetString("userName", u.Username);
                HttpContext.Session.SetString("avatar", u.Photo.Url);
                return RedirectToPage("/Index");
            }


        }
    }
}
