using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Serivce;

namespace SE1611_NET_Group2_Project.Pages.Register
{
    public class IndexModel : PageModel
    {
        [BindProperty]
        public string Username { get; set; }
        [BindProperty]
        public string Email { get; set; }
        [BindProperty]
        public string Password { get; set; }

        [BindProperty]
        public string RePassword { get; set; }
        [BindProperty]
        public string Msg { get; set; }
        public void OnGet()
        {
        }
        public IActionResult OnPost(string Username,string Email, string Password, string RePassword)
        {
            UserService userService = new UserService();
            bool checkUserExist = userService.GetRegisterUser(Username, Email);
            Person u = userService.GetUser(Username, Password);
            if (checkUserExist ==false) {
                if (!Password.Equals(RePassword))
                {
                    // Login failed, set error message in TempData
                    TempData["ErrorMessage"] = "Password not similar with RePassword";
                    return RedirectToPage("/Register/Register");
                }
                else
                {
                    UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
                    userRegisterDTO.username= Username;
                    userRegisterDTO.password= Password;
                    userRegisterDTO.email= Email;
                    bool isRegisted = userService.isRegister(userRegisterDTO);
                    if (isRegisted == true)
                    {
                        return RedirectToPage("/Login/Login");

                    }
                    else
                    {
                        TempData["ErrorMessage"] = "Can not registed!";
                        return RedirectToPage("/Register/Register");
                    }
                }
            }
            else
            {
                TempData["ErrorMessage"] = " Username or Email existed!";
                return RedirectToPage("/Register/Register");
            }


        }
    }
}
