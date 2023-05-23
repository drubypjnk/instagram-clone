using System;

namespace SE1611_NET_Group2_Project.DTO
{
    public class UserToManageDTO
    {
        public string userId { get; set; }
        public string username { get; set; }
        public string fullName { get; set; }
        public string email { get; set; }
        public Boolean  isActive { get; set; }
        public PhotoInforDTO? avartarImage { get; set; }
    }
}
