using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.SignalR;
using Microsoft.EntityFrameworkCore;
using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Serivce;
using SE1611_NET_Group2_Project.Services;

namespace SE1611_NET_Group2_Project.Pages
{
    public class UserProileModel : PageModel
    {
        private readonly IHubContext<NotificationHub> _hubContext;
        [BindProperty]
        public UserInforDTO userInforDTO { get; set; }
        [BindProperty]
        public List<FollowInforDTO> Followings { get; set; }
        [BindProperty]
        public List<FollowInforDTO> Followers { get; set; }
        public string user_id;
        public bool checkRelation;
        public string id;

        public UserProileModel(IHubContext<NotificationHub> hubContext)
        {
            _hubContext = hubContext;

        }
        public Boolean checkFollow;
        public async Task<IActionResult> OnGetAsync(string id)
        {

            try
            {

                this.id = id;
                this.user_id = HttpContext.Session.GetString("user_id");
                userInforDTO = UserService.getUser(id);
                Followings = UserService.getFollowingUser(id, user_id);
                Followers = UserService.getFollowerUser(id, user_id);
                this.checkRelation = checkfollow();
            }catch(Exception ex)
            {
                return Redirect("/login/login");
            }

            return Page();
        }
        public void resetModel()
        {
            this.user_id = HttpContext.Session.GetString("user_id");
            userInforDTO = UserService.getUser(id);
            Followings = UserService.getFollowingUser(id, user_id);
            Followers = UserService.getFollowerUser(id, user_id);
            this.checkRelation = checkfollow();
        }


        public async Task<IActionResult> OnGetFollow(string follow_to)
        {
            try
            {
                this.user_id = HttpContext.Session.GetString("user_id");
                UserService.followUser(this.user_id, follow_to);
                NotificationService.Notificate(NotificationType.FollowNotification, user_id, 0, follow_to, _hubContext);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Content("False");
            }
            resetModel(follow_to);

            return Content("True");
        }
        public async Task<IActionResult> OnGetUnFollow(string follow_to)
        {
            try
            {
                this.user_id = HttpContext.Session.GetString("user_id");
                UserService.unfollower(follow_to, this.user_id);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Content("True");
            }
            resetModel(follow_to);
            return Content("True");
        }



        public bool checkfollow()
        {
            this.user_id = HttpContext.Session.GetString("user_id");
            List<FollowInforDTO> sample = this.Followers;
            foreach (FollowInforDTO follower in sample)
            {
                if (follower.UserId.Equals(this.user_id))
                {
                    return true;
                }
            }
            return false;
        }
        public void resetModel(String uId)
        {
            userInforDTO = UserService.getUser(uId);
            Followings = UserService.getFollowingUser(uId, this.user_id);
            Followers = UserService.getFollowerUser(uId, this.user_id);
            this.checkRelation = checkfollow();
        }

    }
}
