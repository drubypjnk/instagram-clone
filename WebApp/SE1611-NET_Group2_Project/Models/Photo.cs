using System;
using System.Collections.Generic;

namespace SE1611_NET_Group2_Project.Models
{
    public partial class Photo
    {
        public int PhotoId { get; set; }
        public int? PostId { get; set; }
        public DateTime? CreateDate { get; set; }
        public string? Content { get; set; }
        public string? Url { get; set; }
        public int? VisibilityId { get; set; }
        public bool? DeleteFlag { get; set; }

        public virtual Post? Post { get; set; }
        public virtual Person? Person { get; set; }
    }
}
