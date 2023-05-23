using System;
using System.Collections.Generic;

namespace SE1611_NET_Group2_Project.Models
{
    public partial class Visibility
    {
        public int? Id { get; set; }
        public string? Key { get; set; }
        public string? Value { get; set; }
        public bool? DeleteFlag { get; set; }
    }
}
