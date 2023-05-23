using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.Pages.Users
{
    public class EditModel : PageModel
    {
        private readonly SE1611_NET_Group2_Project.Models.InstaContext _context;

        public EditModel(SE1611_NET_Group2_Project.Models.InstaContext context)
        {
            _context = context;
        }

        [BindProperty]
        public Person Person { get; set; } = default!;


        public async Task<IActionResult> OnGetAsync(string id)
        {
            string? user_id = HttpContext.Session.GetString("user_id");
            if (String.IsNullOrEmpty(user_id) ||  !user_id.Equals("user6"))
            {
                return RedirectToPage("/AccessDenied");
            }
            if (id == null || _context.People == null)
            {
                return NotFound();
            }

            var person =  await _context.People.FirstOrDefaultAsync(m => m.UserId == id);
            if (person == null)
            {
                return NotFound();
            }
            Person = person;
           ViewData["PhotoId"] = new SelectList(_context.Photos, "PhotoId", "PhotoId");
            return Page();
        }

        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see https://aka.ms/RazorPagesCRUD.
        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            _context.Attach(Person).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PersonExists(Person.UserId))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return RedirectToPage("./Index");
        }

        private bool PersonExists(string id)
        {
          return (_context.People?.Any(e => e.UserId == id)).GetValueOrDefault();
        }
    }
}
