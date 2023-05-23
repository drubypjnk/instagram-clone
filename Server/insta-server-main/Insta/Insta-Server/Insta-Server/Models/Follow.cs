using System;
using System.Collections.Generic;

namespace Insta_Server.Models
{
    public partial class Follow
    {
        public int Id { get; set; }
        public string UserId { get; set; } = null!;
        public string Follower { get; set; } = null!;
        public DateTime? RequestDate { get; set; }
        public DateTime? ApproveDate { get; set; }
        public bool? DeleteFlag { get; set; }

        public virtual Person FollowerNavigation { get; set; } = null!;
        public virtual Person User { get; set; } = null!;
    }
}
