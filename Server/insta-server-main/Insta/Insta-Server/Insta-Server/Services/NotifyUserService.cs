using Insta_Server.Models;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace Insta_Server.Services
{
    public class NotifyUserService
    {
        public static void AddAll(List<NotifyUser> notifyUsers)
        {
            InstaContext context= new InstaContext();
            context.NotifyUsers.AddRange(notifyUsers);
            context.SaveChanges();
        }
        public static List<NotifyUser> GetNotifyUsersByUserAndDate(string userId, string duration)
        {   
            InstaContext context = new InstaContext();
            context.Photos.ToList();
            context.People.ToList();
            context.Notifies.ToList();
            DateTime compareStartDate;
            DateTime compareEndDate;
            if (duration.Equals("Today"))
            {
                compareStartDate = DateTime.Now.Date;
                compareEndDate = compareStartDate.AddDays(1);
            } else if(duration.Equals("This week"))
            {
                compareEndDate= DateTime.Now.Date;
                compareStartDate = compareEndDate.AddDays(-6);
            } else if(duration.Equals("This month")){
                compareStartDate = DateTime.Now.Date.AddDays(-30);
                compareEndDate = DateTime.Now.Date.AddDays(-6);
            } else
            {
                compareStartDate = DateTime.Now.Date.AddDays(-365);
                compareEndDate = DateTime.Now.Date.AddDays(-30);
            }
            if (!duration.Equals("Later"))
            {
                return context.NotifyUsers
                    .Where(x => x.NotifyUser1.Equals(userId) && x.Notify.CreateDate <= compareEndDate && x.Notify.CreateDate >= compareStartDate && x.Notify.NotifyType != 1)
                    .OrderByDescending(x => x.Notify.CreateDate)
                    .ToList();
            } else
            {
                return context.NotifyUsers
                .Where(x => x.NotifyUser1.Equals(userId) && x.Notify.CreateDate <= compareEndDate && x.Notify.CreateDate >= compareStartDate && x.Notify.NotifyType != 1)
                .OrderByDescending(x => x.Notify.CreateDate)
                .Take(50)
                .ToList();
            }
        }
    }
}
