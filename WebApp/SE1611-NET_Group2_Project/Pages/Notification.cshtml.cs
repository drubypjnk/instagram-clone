using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Services;
using System.Text.Json;

namespace SE1611_NET_Group2_Project.Pages
{
    public class NotificationModel : PageModel
    {
        public JsonResult OnPost()
        {
            return new JsonResult("quang2");
        }
        public JsonResult OnGet()
        {   
            string userId = HttpContext.Session.GetString("user_id");
            if(userId == null)
            {
                Response.Redirect("/Error");
            }
            List<NotificationContainer> containerList = new List<NotificationContainer>();
            string[] type = new string[] { "Today", "This week", "This month", "Later" };
            for (int i = 0; i < type.Length; i++)
            {
                List<NotifyUser> notifyUsers = NotifyUserService.GetNotifyUsersByUserAndDate(userId, type[i]);
                List<NotificationItem> notificationItems = new List<NotificationItem>();
                foreach (var item in notifyUsers)
                {
                    item.Notify.User.Notifies = null;
                    item.Notify.User.NotifyUsers = null;
                    item.Notify.User.Photo.Person = null;
                    NotificationItem notificationItem = new NotificationItem()
                    {
                        content = item.Notify.NotifyContent,
                        person = item.Notify.User,
                        type = (int)item.Notify.NotifyType,
                        post = item.Notify.NotifyType == 2 || item.Notify.NotifyType == 3 ? PostService.GetPost(Int32.Parse(item.Notify.NotifyTitle)) : null,
                        timeAgo = DateTime.Now.Subtract((DateTime)item.Notify.CreateDate).ToString("d'd 'h'h 'm'm 's's'")
                    };
                    if (item.Notify.NotifyType == 2 || item.Notify.NotifyType == 3)
                    {
                        Post post = Services.PostService.GetPost(Int32.Parse(item.Notify.NotifyTitle));
                        if (post.Author.Equals(userId))
                        {
                            notificationItem.content = notificationItem.content.Replace("?", "your");
                        }
                        else
                        {
                            notificationItem.content = notificationItem.content.Replace("?", post.Author);
                        }
                    }

                    notificationItems.Add(notificationItem);
                }
                if (notificationItems.Count > 0)
                {
                    containerList.Add(new NotificationContainer()
                    {
                        title = type[i],
                        notificationItems = notificationItems
                    });
                }

            }
            return new JsonResult(containerList);
        }        
        public JsonResult OnPostGetNotification(string name)
        {
            return new JsonResult(new { message = "Success" });
        }
    }
}
