using System;
using System.Collections.Generic;

namespace Insta_Server.Models
{
    public partial class NotifyUser
    {
        public int Id { get; set; }
        public int? NotifyId { get; set; }
        public string? NotifyUser1 { get; set; }

        public virtual Notify? Notify { get; set; }
        public virtual Person? NotifyUser1Navigation { get; set; }
    }
}
