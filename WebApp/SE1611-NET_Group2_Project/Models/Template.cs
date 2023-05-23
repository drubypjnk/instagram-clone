using System;
using System.Collections.Generic;

namespace SE1611_NET_Group2_Project.Models
{
    public partial class Template
    {
        public int Id { get; set; }
        public int? Key { get; set; }
        public string? Content { get; set; }
        public bool? DeleteFlag { get; set; }
    }
}
