﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using SE1611_NET_Group2_Project.Models;

namespace SE1611_NET_Group2_Project.Pages.Posts
{
    public class DetailsModel : PageModel
    {
        private readonly SE1611_NET_Group2_Project.Models.InstaContext _context;

        public DetailsModel(SE1611_NET_Group2_Project.Models.InstaContext context)
        {
            _context = context;
        }

      public Post Post { get; set; } = default!; 

        public async Task<IActionResult> OnGetAsync(int? id)
        {
            string? user_id = HttpContext.Session.GetString("user_id");
            if (String.IsNullOrEmpty(user_id) || !user_id.Equals("user6"))
            {
                return RedirectToPage("/AccessDenied");
            }
            if (id == null || _context.Posts == null)
            {
                return NotFound();
            }

            var post = await _context.Posts.FirstOrDefaultAsync(m => m.PostId == id);
            if (post == null)
            {
                return NotFound();
            }
            else 
            {
                Post = post;
            }
            return Page();
        }
    }
}
