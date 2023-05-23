using System;
using System.Collections.Generic;

namespace Insta_Server.Models
{
    public partial class Notify
    {
        public Notify()
        {
            NotifyUsers = new HashSet<NotifyUser>();
        }

        public int NotifyId { get; set; }
        public int? NotifyType { get; set; }
        public string? NotifyTitle { get; set; }
        public string? NotifyContent { get; set; }
        public DateTime? CreateDate { get; set; }
        public string? UserId { get; set; }
        public int? Status { get; set; }
        public bool? DeleteFlag { get; set; }

        public virtual Person? User { get; set; }
        public virtual ICollection<NotifyUser> NotifyUsers { get; set; }
    }
}
