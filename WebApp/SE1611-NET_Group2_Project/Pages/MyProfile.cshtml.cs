
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Serivce;

namespace SE1611_NET_Group2_Project.Pages
{
    public class MyProfileModel : PageModel
    {

        [BindProperty]
        public UserInforDTO userInforDTO { get; set; }
        [BindProperty]
        public List<FollowInforDTO> Followings { get; set; }
        public List<FollowInforDTO> Followers { get; set; }


        public async Task<IActionResult> OnGetAsync(string id)
        {
            try{
                string user_id = HttpContext.Session.GetString("user_id");
                userInforDTO = UserService.getUser(user_id);
                Followings = UserService.getFollowingByUserId(user_id);
                Followers = UserService.getFollowerByUserId(user_id);
                return Page();

            }catch (Exception ex) {
                return Redirect("/login/login");
            }
        }


    }
}
