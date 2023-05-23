using Insta_Server.DTO;
using Insta_Server.Models;
using Insta_Server.Serivce;
using Insta_Server.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

namespace Insta_Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly IHubContext<NotificationHub> _hubContext;

        public UserController(IHubContext<NotificationHub> hubContext)
        {
            _hubContext = hubContext;
        }

        [HttpGet("/getUserInformation")]
        public JsonResult getUserInformation(string user_id)
        {
            UserInforDTO person = UserService.getUser(user_id);
            return new JsonResult(person);
        }

        [HttpGet("/getUserLogin")]
        public JsonResult getUserLogin(string username, string password)
        {
            UserLoginDTO person = UserService.getUserLogin(username, password);
            return new JsonResult(person);
        }

        [HttpGet("/getUserByUsername")]
        public JsonResult getUserByUsername(string username)
        {
            UserInforDTO person = UserService.getUserByUsername(username);
            return new JsonResult(person);
        }

        [HttpGet("/getUserByEmail")]
        public JsonResult getUserByEmail(string email)
        {
            UserInforDTO person = UserService.getUserByUsername(email);
            return new JsonResult(person);
        }
        [HttpPost("/register")]
        public JsonResult register(UserRegisterDTO userRegisterDTO)
        {
            Boolean check = UserService.register(userRegisterDTO);
            return new JsonResult(check);
        }
        [HttpPost("/checkUserId")]
        public JsonResult checkUserId(string userId)
        {
            UserPassDTO person = UserService.CheckUserId(userId);
            return new JsonResult(person);
        }

        [HttpPost("/changePassword")]
        public JsonResult changePassword(UserPassDTO userPassDTO)
        {
            Boolean check = UserService.changePassword(userPassDTO);
            return new JsonResult(check);
        }
        [HttpPost("/saveInformation")]
        public JsonResult saveInformation(UserInforDTO userInforDTO)
        {
            Boolean check = UserService.saveInformation(userInforDTO);
            return new JsonResult(check);
        }

        [HttpGet("/getFollowerByUserId")]
        public JsonResult getFollowerByUserId(string user_id)
        {
            List<FollowInforDTO> list = UserService.getFollowerByUserId(user_id);
            return new JsonResult(list);
        }
        [HttpGet("/getUserToManage")]
        public JsonResult getUserToManage(string user_id)
        {
            List<UserToManageDTO> list = UserService.getUserToManage(user_id);
            return new JsonResult(list);
        }
        [HttpGet("/getFollowingByUserId")]
        public JsonResult getFollowingByUserId(string user_id)
        {
            List<FollowInforDTO> list = UserService.getFollowingByUserId(user_id);
            return new JsonResult(list);
        }

        [HttpGet("/getFollowingUser")]
        public JsonResult getFollowingUser(string user_id, string view_id)
        {
            List<FollowInforDTO> list = UserService.getFollowingUser(user_id,view_id);
            return new JsonResult(list);
        }
        [HttpGet("/getFollowerUser")]
        public JsonResult getFollowerUser(string user_id, string view_id)
        {
            List<FollowInforDTO> list = UserService.getFollowerUser(user_id, view_id);
            return new JsonResult(list);
        }

        [HttpPost("/followUser")]
        public JsonResult followUser(string user_id, string follow_To)
        {
            Boolean check = true;
            check = UserService.followUser(user_id, follow_To);
            NotificationService.Notificate(NotificationType.FollowNotification, user_id, 0, follow_To, _hubContext);
            return new JsonResult(check);
        }
        [HttpPost("/unfollowUser")]
        public JsonResult unfollow(int followId)
        {
            Boolean check = true;
            check = UserService.unfollow(followId);
            return new JsonResult(check);
        }
        [HttpPost("/refollowUser")]
        public JsonResult refollow(int followId)
        {
            Boolean check = true;
            check = UserService.refollow(followId);
            return new JsonResult(check);
        }
        [HttpPost("/unFollower")]
        public JsonResult unFollower(string user_id, string follower)
        {
            Boolean check = true;
            check = UserService.unfollower(user_id, follower);
            return new JsonResult(check);
        }
        [HttpPost("/activeUser")]
        public JsonResult activeUser(string user_id)
        {
            Boolean check = true;
            check = UserService.activeUser(user_id);
            return new JsonResult(check);
        }

        [HttpPost("/deActiveUser")]
        public JsonResult deActiveUser(string user_id)
        {
            Boolean check = true;
            check = UserService.deActiveUser(user_id);
            return new JsonResult(check);
        }
        [HttpPost("/getUserByUsernameAndEmail")]
        public JsonResult getUserByUsernameAndEmail(string username, string email)
        {
            Boolean check = true;
            check = UserService.getUserByUsernameAndEmail(username, email);
            return new JsonResult(check);
        }
        [HttpPost("/sendNewPass")]
        public JsonResult sendNewPass(string email, string newPass)
        {
            Boolean check = true;
            check = UserService.sendNewPass(email, newPass);
            return new JsonResult(check);
        }


        [HttpGet("/searchUser")]
        public JsonResult SearchUser(string userId)
        {
            List<UserInforDTO> person = UserService.SearchUser(userId);
            return new JsonResult(person);
        }
    }
}
