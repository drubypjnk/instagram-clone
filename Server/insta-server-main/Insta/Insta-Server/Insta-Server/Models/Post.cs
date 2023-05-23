using System;
using System.Collections.Generic;

namespace Insta_Server.Models
{
    public partial class Post
    {
        public Post()
        {
            Comments = new HashSet<Comment>();
            Likes = new HashSet<Like>();
            Photos = new HashSet<Photo>();
        }

        public int PostId { get; set; }
        public string? Title { get; set; }
        public string? Content { get; set; }
        public DateTime? CreatedDate { get; set; }
        public string Author { get; set; } = null!;
        public int? VisibilityId { get; set; }
        public DateTime? UpdateDate { get; set; }
        public bool? DeleteFlag { get; set; }

        public virtual Person AuthorNavigation { get; set; } = null!;
        public virtual ICollection<Comment> Comments { get; set; }
        public virtual ICollection<Like> Likes { get; set; }
        public virtual ICollection<Photo> Photos { get; set; }
    }
}
