﻿using System;
using System.Collections.Generic;

namespace Insta_Server.Models
{
    public partial class Like
    {
        public int LikeId { get; set; }
        public int? PostId { get; set; }
        public string UserId { get; set; } = null!;
        public bool? DeleteFlag { get; set; }

        public virtual Post? Post { get; set; }
        public virtual Person User { get; set; } = null!;
    }
}
