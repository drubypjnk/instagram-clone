using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.Pages.Users
{
    public class CreateModel : PageModel
    {
        private readonly SE1611_NET_Group2_Project.Models.InstaContext _context;

        public CreateModel(SE1611_NET_Group2_Project.Models.InstaContext context)
        {
            _context = context;
        }

        public IActionResult OnGet()
        {
            string? user_id = HttpContext.Session.GetString("user_id");
            if (String.IsNullOrEmpty(user_id) || !user_id.Equals("user6"))
            {
                return RedirectToPage("/AccessDenied");
            }
            ViewData["PhotoId"] = new SelectList(_context.Photos, "PhotoId", "PhotoId");
            return Page();
        }

        [BindProperty]
        public Person Person { get; set; } = default!;
        

        // To protect from overposting attacks, see https://aka.ms/RazorPagesCRUD
        public async Task<IActionResult> OnPostAsync()
        {
          if (!ModelState.IsValid || _context.People == null || Person == null)
            {
                return Page();
            }

            _context.People.Add(Person);
            await _context.SaveChangesAsync();

            return RedirectToPage("./Index");
        }
    }
}
