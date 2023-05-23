using System;
using System.Collections.Generic;

namespace Insta_Server.Models
{
    public partial class React
    {
        public int ReactId { get; set; }
        public string? Name { get; set; }
        public bool? DeleteFlag { get; set; }
    }
}
