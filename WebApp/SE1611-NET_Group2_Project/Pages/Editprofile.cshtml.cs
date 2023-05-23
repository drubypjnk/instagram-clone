using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;
using SE1611_NET_Group2_Project.Serivce;

namespace SE1611_NET_Group2_Project.Pages
{
    public class EditprofileModel : PageModel
    {
        private Microsoft.AspNetCore.Hosting.IHostingEnvironment _environment;
        public string user_id { get; set; }
        public string cur_password { get; set; }
        public UserInforDTO current_user { get; set; }


        public IActionResult OnGet()
        {
            try
            {
                user_id = HttpContext.Session.GetString("user_id");
                current_user = UserService.getUser(user_id);
                this.cur_password = UserService.getPass(user_id);
            }
            catch (Exception ex)
            {
                // Chuyển hướng đến trang /login/login
                return RedirectToPage("/login/login");
                
            }
            return Page();
        }
        public EditprofileModel(Microsoft.AspNetCore.Hosting.IHostingEnvironment environment)

        {
            try{
                _environment = environment;
                // user_id = HttpContext.Session.GetString("user_id");
                //current_user = UserService.getUser(user_id);
                user_id = HttpContext.Session.GetString("user_id");
               

            }catch (Exception ex)
            {
                OnGet();
            }
        }


        public async Task<IActionResult> OnPost(string username, string full_name, IFormFile file, string url, int gender,
           string description, string email, string address)
        {
            try
            {
                UserInforDTO userInforDTO = new UserInforDTO();
                userInforDTO.Username = username;
                userInforDTO.FullName = full_name;
                userInforDTO.Email = email;
                userInforDTO.Desription = description;
                userInforDTO.Location = address;
                userInforDTO.Gender = gender;
                //save photo
                string user_id = HttpContext.Session.GetString("user_id");
                userInforDTO.UserId = user_id;
                if(url != null)
                {
                    PhotoInforDTO photoInforDTO = new PhotoInforDTO();
                    photoInforDTO.Url = url;
                    userInforDTO.avartarImage = photoInforDTO;
                }
                if (file != null) //create form
                {
                    var uploadsFolder = Path.Combine(_environment.WebRootPath, "images");
                    var filePath = Path.Combine(uploadsFolder, file.FileName);

                    using (var stream = new FileStream(filePath, FileMode.Create))
                    {
                        await file.CopyToAsync(stream);
                    }

                }


                Boolean check = UserService.saveInformation(userInforDTO);
                this.user_id = HttpContext.Session.GetString("user_id");
                current_user = UserService.getUser(this.user_id);
                this.cur_password = UserService.getPass(user_id);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return Page();
            }
            return Page();
        }
        public async Task<IActionResult> OnGetChange(string newpass)
        {
            InstaContext context = new InstaContext();
            user_id = HttpContext.Session.GetString("user_id");
            Person p = context.People.FirstOrDefault(x => x.UserId.ToLower().Equals(user_id.ToLower()));
            p.Password = newpass;
            context.Update(p);
            context.SaveChanges();

            user_id = HttpContext.Session.GetString("user_id");
            this.current_user = UserService.getUser(user_id);
            this.cur_password = newpass;
            return Content("True");
        }
    }
}


