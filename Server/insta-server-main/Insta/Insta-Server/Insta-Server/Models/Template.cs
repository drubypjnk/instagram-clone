using System;
using System.Collections.Generic;

namespace Insta_Server.Models
{
    public partial class Template
    {
        public int Id { get; set; }
        public int? Key { get; set; }
        public string? Content { get; set; }
        public bool? DeleteFlag { get; set; }
    }
}
