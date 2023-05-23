using SE1611_NET_Group2_Project.Models;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace SE1611_NET_Group2_Project.Services
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
                List<NotifyUser> lst = context.NotifyUsers
                    .Where(x => x.NotifyUser1.Equals(userId) && x.Notify.CreateDate <= compareEndDate && x.Notify.CreateDate >= compareStartDate)
                    .Include(x => x.Notify)
                    .Include(x => x.NotifyUser1Navigation)
                    .Include(x => x.Notify.User)
                    .Include(x => x.Notify.User.Photo)
                    .OrderByDescending(x => x.Notify.CreateDate)
                    .ToList();

                //foreach (var item in lst)
                //{
                //    item.Notify.User = context.People.Where(x => x.UserId.Equals(item.Notify.UserId)).FirstOrDefault();
                //}
                return lst;
            } else
            {
                List<NotifyUser> lst = context.NotifyUsers
                .Where(x => x.NotifyUser1.Equals(userId) && x.Notify.CreateDate <= compareEndDate && x.Notify.CreateDate >= compareStartDate)
                .Include(x => x.Notify)
                .Include(x => x.Notify.User)
                .Include(x => x.Notify.User.Photo)
                .OrderByDescending(x => x.Notify.CreateDate)
                .Take(50)
                .ToList();
                //foreach (var item in lst)
                //{
                //    item.Notify.User = context.People.Where(x => x.UserId.Equals(item.Notify.UserId)).FirstOrDefault();
                //}
                return lst;
            }
        }
    }
}
