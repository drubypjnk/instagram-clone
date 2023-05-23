using System;
using System.Collections.Generic;

namespace Insta_Server.Models
{
    public partial class Message
    {
        public int MessageId { get; set; }
        public int? RoomId { get; set; }
        public string? Content { get; set; }
        public int Author { get; set; }
        public int? ReactId { get; set; }
        public bool? DeleteFlag { get; set; }
        public DateTime? CreatedDate { get; set; }

        public virtual RoomMember AuthorNavigation { get; set; } = null!;
    }
}
