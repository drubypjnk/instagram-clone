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
    public class IndexModel : PageModel
    {
        private readonly SE1611_NET_Group2_Project.Models.InstaContext _context;

        public IndexModel(SE1611_NET_Group2_Project.Models.InstaContext context)
        {
            _context = new InstaContext();
        }
        [BindProperty]
        public List<Person> Person { get; set; }
        [BindProperty]
        public int? PageIndex { get; set; }
        [BindProperty]
        public int? TotalPages { get; set; }
        public IActionResult OnGet(int? pageIndex)
        {
            string? user_id = HttpContext.Session.GetString("user_id");
            if (String.IsNullOrEmpty(user_id) || (_context.People == null && !user_id.Equals("user6")))
            {
                return RedirectToPage("/AccessDenied");
            }

            int pageSize = 10; // Number of records to display on each page.
            pageIndex ??= 1; // If pageIndex is null, set it to 1.

            var data = _context.People
                .OrderBy(d => d.UserId)
                .Skip((pageIndex.Value - 1) * pageSize)
                .Take(pageSize)
                .ToList();

            Person = data;
            PageIndex = pageIndex.Value;
            TotalPages =(int) Math.Ceiling(_context.People.Count() / (double)pageSize);
            return Page();

        }

    }
}
