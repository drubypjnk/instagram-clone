using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Serivce;

namespace SE1611_NET_Group2_Project.Pages
{
    public class SearchModel : PageModel
    {
        public JsonResult OnGet(string name)
        {
            List<Person> person = UserService.SearchUser(name);
            return new JsonResult(person);
        }
    }
}
