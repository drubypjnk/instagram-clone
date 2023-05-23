using System;
using System.Collections.Generic;

namespace SE1611_NET_Group2_Project.Models
{
    public partial class Person
    {
        public Person()
        {
            Comments = new HashSet<Comment>();
            FollowFollowerNavigations = new HashSet<Follow>();
            FollowUsers = new HashSet<Follow>();
            Likes = new HashSet<Like>();
            Notifies = new HashSet<Notify>();
            NotifyUsers = new HashSet<NotifyUser>();
            Posts = new HashSet<Post>();
            RoomMembers = new HashSet<RoomMember>();
        }

        public string UserId { get; set; } = null!;
        public string? Username { get; set; }
        public string? Password { get; set; }
        public string? FullName { get; set; }
        public string? Description { get; set; }
        public int? Age { get; set; }
        public int? Gender { get; set; }
        public string? Location { get; set; }
        public string? Email { get; set; }
        public int? Status { get; set; }
        public int? PhotoId { get; set; }
        public bool? IsPrivate { get; set; }
        public bool? DeleteFlag { get; set; }

        public virtual Photo? Photo { get; set; }
        public virtual ICollection<Comment> Comments { get; set; }
        public virtual ICollection<Follow> FollowFollowerNavigations { get; set; }
        public virtual ICollection<Follow> FollowUsers { get; set; }
        public virtual ICollection<Like> Likes { get; set; }
        public virtual ICollection<Notify> Notifies { get; set; }
        public virtual ICollection<NotifyUser> NotifyUsers { get; set; }
        public virtual ICollection<Post> Posts { get; set; }
        public virtual ICollection<RoomMember> RoomMembers { get; set; }
    }
}
