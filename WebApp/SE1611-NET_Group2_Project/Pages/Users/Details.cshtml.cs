using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.Pages.Users
{
    public class DetailsModel : PageModel
    {
        private readonly SE1611_NET_Group2_Project.Models.InstaContext _context;

        public DetailsModel(SE1611_NET_Group2_Project.Models.InstaContext context)
        {
            _context = context;
        }

      public Person Person { get; set; } = default!; 

        public async Task<IActionResult> OnGetAsync(string id)
        {
            string? user_id = HttpContext.Session.GetString("user_id");
            if (String.IsNullOrEmpty(user_id) || !user_id.Equals("user6"))
            {
                return RedirectToPage("/AccessDenied");
            }
            if (id == null || _context.People == null)
            {
                return NotFound();
            }

            var person = await _context.People.FirstOrDefaultAsync(m => m.UserId == id);
            if (person == null)
            {
                return NotFound();
            }
            else 
            {
                Person = person;
            }
            return Page();
        }
    }
}
