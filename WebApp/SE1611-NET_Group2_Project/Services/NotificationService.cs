using SE1611_NET_Group2_Project.Models;
using Microsoft.AspNetCore.SignalR;
using System.Text.Json;
using SE1611_NET_Group2_Project.Serivce;

namespace SE1611_NET_Group2_Project.Services
{
    public class NotificationService
    {
        public static bool Notificate(NotificationType type, string actor, int postId, string secondaryPeople, IHubContext<NotificationHub> context)
        {
            if (type == NotificationType.CommentNotification || type == NotificationType.ReactNotification)
            {
                return CommonPostNotificate(type, actor, postId, context);
            }
            else if (type == NotificationType.FollowNotification)
            {
                return CommonFollowNotificate(actor, secondaryPeople, context);
            }
            else
            {
                return CommonMessageNotificate(actor, secondaryPeople, context);
            }
        }

        private static bool CommonPostNotificate(NotificationType type, string actor, int postId, IHubContext<NotificationHub> context)
        {
            try
            {
                List<String> numberPeopleCommented;
                string username = UserService.GetUsernameByID(actor);
                Notify notify;
                string postOwner = PostService.getOwner(postId);
                if (type == NotificationType.CommentNotification)
                {
                    numberPeopleCommented = CommentService.GetNumberPeopleCommentedExcludeActor(postId, actor);
                    notify = new NotificationBuilder()
                    .SetType(type, postId + "")
                    .SetContent((numberPeopleCommented.Count == 0 ? "" : $"and {numberPeopleCommented.Count} other(s) ") + "commented in ? post.")
                    .SetUserId(actor)
                    .Build();
                }
                else
                {
                    numberPeopleCommented = LikeService.GetNumberPeopleLiked(postId, actor);
                    notify = new NotificationBuilder()
                    .SetType(type, postId + "")
                    .SetContent((numberPeopleCommented.Count == 0 ? "" : $"and {numberPeopleCommented.Count} other(s) ") + "reacted ? post.")
                    .SetUserId(actor)
                    .Build();
                }
                notify.DeleteFlag = false;
                notify.Status = 1;
                if (!numberPeopleCommented.Contains(postOwner))
                {
                    numberPeopleCommented.Add(postOwner);
                }
                AddNotify(notify);

                AddNotifyUser(numberPeopleCommented, notify.NotifyId);

                NotificateUsers(notify, numberPeopleCommented, context);

            }
            catch (Exception ex)
            {
                return false;
            }

            return true;
        }
        private static bool CommonFollowNotificate(string actor, string followedPeople, IHubContext<NotificationHub> context)
        {
            try
            {
                string username = UserService.GetUsernameByID(actor);
                Notify notify = new NotificationBuilder()
                    .SetType(NotificationType.FollowNotification, actor)
                    .SetContent($"{username} is following you.")
                    .SetUserId(actor)
                    .Build();
                notify.DeleteFlag = false;
                notify.Status = 1;
                AddNotify(notify);

                List<string> receivedPeople = new List<string>()
                                {
                                    followedPeople
                                };
                AddNotifyUser(receivedPeople, notify.NotifyId);

                NotificateUsers(notify, receivedPeople, context);
            }
            catch (Exception ex)
            {
                return false;
            }

            return true;
        }
        private static bool CommonMessageNotificate(string actor, string receivedRoom, IHubContext<NotificationHub> context)
        {
            try
            {
                string username = UserService.GetUsernameByID(actor);
                Notify notify = new NotificationBuilder()
                    .SetType(NotificationType.MessageNotification, receivedRoom)
                    .SetContent($"{username} sent you a message.")
                    .SetUserId(actor)
                    .Build();

                AddNotify(notify);
                notify.DeleteFlag = false;
                notify.Status = 1;

                List<string> receivedPeople = GetUsersByRoom(receivedRoom);
                receivedPeople.Except(new[] { actor });
                AddNotifyUser(receivedPeople, notify.NotifyId);

                NotificateUsers(notify, receivedPeople, context);
            }
            catch (Exception ex)
            {
                return false;
            }

            return true;
        }
        private static bool NotificateUsers(Notify notify, List<string> users, IHubContext<NotificationHub> context)
        {
            string content = notify.NotifyContent;
            string postOwner = "";
            if (notify.NotifyType == 2 || notify.NotifyType == 3)
            {
                postOwner = PostService.getOwner(Int32.Parse(notify.NotifyTitle));
            }
            if (users == null) return false;
            foreach (string user in users)
            {
                string newContent;
                if (postOwner.Equals(user))
                {
                    newContent = content.Replace("?", "your");
                }
                else
                {
                    newContent = content.Replace("?", postOwner);
                }
                notify.NotifyContent = newContent;
                NotificateUser(notify, user, context);
            }
            return true;
        }
        public static void NotificateUser(Notify notify, string user, IHubContext<NotificationHub> context)
        {
            string json = JsonSerializer.Serialize<Notify>(notify);
            List<string> connectionsId;
            NotificationHub.ConnectedUsers.TryGetValue(user, out connectionsId);
            context.Clients.Clients(connectionsId).SendAsync("ReceiveNotification", json);
        }

        public static void AddNotify(Notify notify)
        {
            InstaContext context = new();
            context.Notifies.Add(notify);
            context.SaveChanges();
        }
        private static void AddNotifyUser(List<String> users, int notifyId)
        {
            if (users == null || users.Count == 0) return;
            List<NotifyUser> userList = new List<NotifyUser>();
            foreach (string user in users)
            {
                userList.Add(new NotifyUser()
                {
                    NotifyId = notifyId,
                    NotifyUser1 = user
                });
            }
            NotifyUserService.AddAll(userList);
        }
        private static List<string> GetUsersByRoom(string roomId)
        {
            try
            {
                int room = int.Parse(roomId);
                return UserService.GetUsersByRoom(room);
            }
            catch (Exception ex)
            {
                return new List<string>();
            }
        }
    }

    public enum NotificationType
    {
        None = 0,
        MessageNotification = 1,
        CommentNotification = 2,
        ReactNotification = 3,
        FollowNotification = 4
    }
    class NotificationBuilder
    {
        private int? _notifyType { get; set; }
        private string? _notifyTitle { get; set; }
        private string? _notifyContent { get; set; }
        private DateTime? _createDate { get; set; }
        private string? _userId { get; set; }
        public NotificationBuilder()
        {
            _createDate = DateTime.Now;
        }
        public NotificationBuilder SetType(NotificationType type, string title)
        {
            _notifyType = (int?)type;
            if (type == NotificationType.CommentNotification)
            {
                _notifyTitle = title;
            }
            else if (type == NotificationType.ReactNotification)
            {
                _notifyTitle = title;
            }
            else if (type == NotificationType.FollowNotification)
            {
                _notifyTitle = title;
            }
            else
            {
                _notifyTitle = title;
            }
            return this;
        }
        public NotificationBuilder SetContent(string content)
        {
            _notifyContent = content;
            return this;
        }
        public NotificationBuilder SetUserId(string userId)
        {
            _userId = userId;
            return this;
        }
        public Notify Build()
        {
            return new Notify()
            {
                UserId = _userId != null ? _userId : String.Empty,
                CreateDate = _createDate,
                NotifyTitle = _notifyTitle != null ? _notifyTitle : String.Empty,
                NotifyContent = _notifyContent != null ? _notifyContent : String.Empty,
                NotifyType = _notifyType != null ? (int)_notifyType : 0,
            };
        }
    }
}
