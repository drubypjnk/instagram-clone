using Insta_Server.DTO;
using Insta_Server.Models;
using Insta_Server.Serivce;
using Insta_Server.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using System.Collections.Generic;

namespace Insta_Server.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class NotificationController : Controller
    {
        private readonly ILogger<NotificationController> _logger;

        private readonly IHubContext<NotificationHub> _hubContext;

        public NotificationController(ILogger<NotificationController> logger, IHubContext<NotificationHub> hubContext)
        {
            _logger = logger;
            _hubContext = hubContext;
        }

        [HttpGet(Name = "GetNotification/{userId}")]
        public List<NotificationContainer> Get(string userId)
        {
            List<NotificationContainer> containerList = new List<NotificationContainer>();
            string[] type = new string[] { "Today", "This week", "This month", "Later" };
            for (int i = 0; i < type.Length; i++)
            {
                List<NotifyUser> notifyUsers = NotifyUserService.GetNotifyUsersByUserAndDate(userId, type[i]);
                List<NotificationItem> notificationItems= new List<NotificationItem>();
                foreach (var item in notifyUsers)
                {
                    NotificationItem notificationItem = new NotificationItem()
                    {
                        content = item.Notify.NotifyContent,
                        imageSource = item.NotifyUser1Navigation.Photo.Content,
                        isFollowNotification = item.Notify.NotifyType == 4 ? true : false,
                        target = item.Notify.NotifyTitle,
                        userId = item.Notify.UserId
                    };
                    if(item.Notify.NotifyType == 2 || item.Notify.NotifyType == 3)
                    {
                        Post post = Services.PostService.GetPost(Int32.Parse(item.Notify.NotifyTitle));
                        if (post.Author.Equals(userId))
                        {
                            notificationItem.content = notificationItem.content.Replace("?", "your");
                        } else
                        {
                            notificationItem.content = notificationItem.content.Replace("?", post.Author);
                        }
                    }

                    notificationItems.Add(notificationItem);
                }
                if(notificationItems.Count > 0)
                {
                    containerList.Add(new NotificationContainer()
                    {
                        title = type[i],
                        notificationItems = notificationItems
                    });
                }

            }
            return containerList;
        }
    }
}
