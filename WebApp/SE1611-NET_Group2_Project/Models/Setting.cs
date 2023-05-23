using System;
using System.Collections.Generic;

namespace SE1611_NET_Group2_Project.Models
{
    public partial class Setting
    {
        public int Id { get; set; }
        public string? Key { get; set; }
        public string? Value { get; set; }
        public DateTime? DateCreated { get; set; }
        public DateTime? DateUpdated { get; set; }
        public bool? DeleteFlag { get; set; }
    }
}
