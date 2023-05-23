using SE1611_NET_Group2_Project.DTO;
using SE1611_NET_Group2_Project.Models;
using System.Collections;
using System.Net.Mail;
using System.Net;
using Microsoft.EntityFrameworkCore;
using System.Runtime.CompilerServices;
using System.Text.Json;

namespace SE1611_NET_Group2_Project.Serivce
{
    public class UserService
    {
        public Person GetUser(String username, String password)
        {
            InstaContext context = new InstaContext();
            return context.People.Include(x => x.Photo).FirstOrDefault(x => x.Username == username && x.Password == password);

        }
        public static string getPass(string user_id)
        {
            InstaContext context = new InstaContext();
            Person p = context.People.FirstOrDefault(x => x.UserId.ToLower().Equals(user_id.ToLower()));
            return p.Password;
        }
        public static UserInforDTO getUser(string user_id)
        {
            InstaContext context = new InstaContext();
            Person p = context.People.FirstOrDefault(x => x.UserId.ToLower().Equals(user_id.ToLower()));

            //set dto
            UserInforDTO user = new UserInforDTO();
            user.UserId = p.UserId;
            user.Username = p.Username;
            user.FullName = p.FullName;
            user.Age = p.Age;
            user.Gender = p.Gender;
            user.Email = p.Email;
            user.Desription = p.Description;
            user.Location = p.Location;
            user.IsPrivate = p.IsPrivate;
            //get avatar
            Photo? avt_raw = context.Photos.FirstOrDefault(x => x.PhotoId == p.PhotoId);

            user.avartarImage = convertToPhotoDTO(avt_raw);
            // get post
            List<Post> potst = context.Posts.Where(x => x.Author.Equals(user_id.ToLower())).ToList();
            List<PostInforDTO> listPost = new List<PostInforDTO>();
            foreach (Post post in potst)
            {
                PostInforDTO postInfor = new PostInforDTO();
                postInfor.PostId = post.PostId;
                postInfor.Content = post.Content;
                postInfor.Title = post.Title;
                Photo photo = context.Photos.FirstOrDefault(x => x.PostId == post.PostId);
                postInfor.postImage = convertToPhotoDTO(photo);
                //get react post
                context.Likes.ToList();
                context.Comments.ToList();
                postInfor.Comment = post.Comments.ToList().Count();
                postInfor.Like = post.Likes.ToList().Count();
                listPost.Add(postInfor);
            }
            user.listPost = listPost;
            //get follower
            List<FollowerInforDTO> listFollower = new List<FollowerInforDTO>();
            List<Follow> follows = context.Follows.Where(x => x.UserId.Equals(p.UserId) && x.DeleteFlag == false).ToList();
            foreach (Follow f in follows)
            {
                FollowerInforDTO fd = converFollowToDTO(f);
                listFollower.Add(fd);
            }

            //get flowwing
            List<Follow> following = context.Follows.Where(x => x.Follower.Equals(p.UserId) && x.DeleteFlag == false).ToList();
            List<FollowerInforDTO> listFollowing = new List<FollowerInforDTO>();
            foreach (Follow f in following)
            {
                FollowerInforDTO fd = converFollowerToDTO(f.UserId);
                listFollowing.Add(fd);
            }

            user.listFollower = listFollower;
            user.listFollowing = listFollowing;
            return user;
        }
        public static RoomUserDTO getUserRoom(string user_id)
        {
            InstaContext context = new InstaContext();
            Person p = context.People.FirstOrDefault(x => x.UserId.ToLower().Equals(user_id.ToLower()));

            //set dto
            RoomUserDTO user = new RoomUserDTO();
            user.UserId = p.UserId;
            user.Username = p.Username;
            user.FullName = p.FullName;
            //get avatar
            Photo? avt_raw = context.Photos.FirstOrDefault(x => x.PhotoId == p.PhotoId);

            user.avartarImage = convertToPhotoDTO(avt_raw);
            return user;
        }
        public static UserLoginDTO getUserLogin(string username, string password)
        {
            InstaContext context = new InstaContext();
            Person p = context.People.FirstOrDefault(x => x.Username.ToLower().Equals(username.ToLower()) &&
                                                          x.Password.ToLower().Equals(password.ToLower()));
            if (p != null)
            {
                //set dto
                UserLoginDTO user = new UserLoginDTO();
                user.user_id = p.UserId;
                user.username = p.Username;
                user.password = p.Password;
                user.status = (int)p.Status;
                return user;
            }
            else
            {
                return null;
            }

        }
        public static List<FollowInforDTO> getFollowerByUserId(string user_id)
        {
            List<FollowInforDTO> list = new List<FollowInforDTO>();
            InstaContext context = new InstaContext();
            List<Follow> follows = context.Follows.Where(x => x.UserId.Equals(user_id) && x.DeleteFlag == false).ToList();
            foreach (Follow f in follows)
            {
                FollowInforDTO fd = convertFollowerNormalToDTO(f.Follower, f.Id);
                if (checkRelaFlow(f.Follower, user_id))
                {
                    fd.flag = true;
                }

                list.Add(fd);

            }
            return list;
        }

        public static List<FollowInforDTO> getFollowingUser(string user_id, string view_id)
        {
            List<FollowInforDTO> list = new List<FollowInforDTO>();
            List<string> generalList = new List<string>();
            InstaContext context = new InstaContext();

            List<Follow> follows = context.Follows.Where(x => x.Follower.Equals(user_id) && x.DeleteFlag == false).ToList();
            foreach (Follow f in follows)
            {
                FollowInforDTO fd = convertFollowerNormalToDTO(f.UserId, f.Id);
                list.Add(fd);
            }
            List<FollowInforDTO> listsamples = new List<FollowInforDTO>();
            List<Follow> follows_view = context.Follows.Where(x => x.Follower.Equals(view_id) && x.DeleteFlag == false).ToList();
            foreach (Follow f in follows_view)
            {
                FollowInforDTO fd = convertFollowerNormalToDTO(f.UserId, f.Id);
                listsamples.Add(fd);
            }
            List<FollowInforDTO> newList = new List<FollowInforDTO>();
            //set default
            list = SetUpFollowUserDefault(list);
            //get flag
            foreach (FollowInforDTO f in list)
            {
                if (checkRelaFlow(f.UserId, view_id) == true)
                {
                    f.flag = true;
                }

                newList.Add(f);
            }

            return newList;
        }
        public static List<FollowInforDTO> getFollowerUser(string user_id, string view_id)
        {
            List<FollowInforDTO> list = new List<FollowInforDTO>();
            List<string> generalList = new List<string>();
            InstaContext context = new InstaContext();
            generalList = getGeneralFollow(user_id, view_id);
            List<Follow> follows = context.Follows.Where(x => x.UserId.Equals(user_id) && x.DeleteFlag == false).ToList();
            foreach (Follow f in follows)
            {
                FollowInforDTO fd = convertFollowerNormalToDTO(f.Follower, f.Id);
                list.Add(fd);
            }
            List<FollowInforDTO> listsamples = new List<FollowInforDTO>();
            List<Follow> follows_view = context.Follows.Where(x => x.UserId.Equals(view_id) && x.DeleteFlag == false).ToList();
            foreach (Follow f in follows_view)
            {
                FollowInforDTO fd = convertFollowerNormalToDTO(f.UserId, f.Id);
                listsamples.Add(fd);
            }
            List<FollowInforDTO> newList = new List<FollowInforDTO>();
            //set default
            list = SetUpFollowUserDefault(list);
            //get flag
            foreach (FollowInforDTO f in list)
            {
                if (checkRelaFlow(f.UserId, view_id) == true)
                {
                    f.flag = true;
                }
                newList.Add(f);

            }

            return newList;
        }
        public static Boolean checkRelaFlow(string user_id, string follower)
        {
            InstaContext context = new InstaContext();
            Follow? f = context.Follows.FirstOrDefault(x => x.UserId.Equals(user_id) && x.Follower.Equals(follower) && x.DeleteFlag == false);
            if (f != null)
            {
                return true;
            }
            return false;
        }
        public static List<string> getGeneralFollow(string user1, string user2)
        {
            List<string> list = new List<string>();
            List<FollowInforDTO> list1 = getFollowingByUserId(user1);
            List<FollowInforDTO> list2 = getFollowingByUserId(user2);
            foreach (FollowInforDTO f1 in list1)
            {
                foreach (FollowInforDTO f2 in list2)
                {
                    if (f1.UserId.Equals(f2.UserId))
                    {
                        list.Add(f1.UserId);
                    }
                }
            }
            return list;
        }


        public static List<FollowInforDTO> SetUpFollowUserDefault(List<FollowInforDTO> list)
        {
            List<FollowInforDTO> newList = new List<FollowInforDTO>();
            foreach (FollowInforDTO f in list)
            {
                f.flag = false;
                newList.Add(f);
            }
            return newList;
        }

        public static List<FollowInforDTO> getFollowingByUserId(string user_id)
        {
            List<FollowInforDTO> list = new List<FollowInforDTO>();
            InstaContext context = new InstaContext();
            List<Follow> follows = context.Follows.Where(x => x.Follower.Equals(user_id) && x.DeleteFlag == false).ToList();
            foreach (Follow f in follows)
            {
                FollowInforDTO fd = convertFollowerNormalToDTO(f.UserId, f.Id);

                list.Add(fd);
            }
            return list;
        }
        public static Boolean UpdatePassword(String user_id, String newPassword)
        {
            try
            {
                InstaContext context = new InstaContext();
                Person? person = context.People.FirstOrDefault(x => x.UserId.ToLower().Equals(user_id.ToLower()));
                if (person == null) return false;
                else
                {
                    person.Password = newPassword;
                    context.People.Update(person);
                }
            }
            catch (Exception ex)
            {

                return false;
            }
            return true;
        }

        public static PhotoInforDTO convertToPhotoDTO(Photo p)
        {
            if (p != null)
            {
                InstaContext context = new InstaContext();
                Photo? photo_raw = context.Photos.FirstOrDefault(x => x.PhotoId == p.PhotoId);
                PhotoInforDTO photo = new PhotoInforDTO();
                photo.PhotoId = photo_raw.PhotoId;
                photo.Content = photo_raw.Content;
                photo.Url = photo_raw.Url;
                return photo;

            }
            return null;
        }
        public static UserPassDTO CheckUserId(String user_id)
        {
            InstaContext context = new InstaContext();
            Person p = context.People.FirstOrDefault(x => x.UserId.ToLower().Equals(user_id.ToLower()));
            UserPassDTO userPass = new UserPassDTO();
            userPass.username = p.Username;
            userPass.user_id = p.UserId;
            userPass.password = p.Password;
            return userPass;
        }
        public static Boolean changePassword(UserPassDTO userPassDTO)
        {
            InstaContext context = new InstaContext();
            Person? p = context.People.FirstOrDefault(x => x.UserId.Equals(userPassDTO.user_id));
            if (p == null)
            {
                return false;
            }
            p.Password = userPassDTO.password;
            context.Update(p);
            context.SaveChanges();
            return true;
        }
        public static Boolean saveInformation(UserInforDTO userInforDTO)
        {
            try
            {
                InstaContext context = new InstaContext();
                Person? p = context.People.FirstOrDefault(x => x.UserId.ToLower().Equals(userInforDTO.UserId.ToLower()));
                p.Username = userInforDTO.Username;
                p.Gender = userInforDTO.Gender;
                p.FullName = userInforDTO?.FullName;
                p.IsPrivate = userInforDTO.IsPrivate;
                p.Gender = userInforDTO.Gender;
                p.Email = userInforDTO.Email;
                p.Description = userInforDTO.Desription;
                p.Location = userInforDTO.Location;
                p.Age = userInforDTO.Age;

                //convert image
                PhotoInforDTO? avartarImage = userInforDTO.avartarImage;
                if (avartarImage != null)
                {
                    Photo photo = convertImageDTO(avartarImage);

                    context.Photos.Add(photo);
                    context.SaveChanges();
                    p.PhotoId = photo.PhotoId;
                }
                context.Update(p);
                context.SaveChanges();
            }
            catch (Exception e)
            {
                return false;
            }
            return true;
        }
        public static Photo convertImageDTO(PhotoInforDTO p)
        {
            Photo photo = new Photo();
            photo.VisibilityId = 1;
            photo.DeleteFlag = false;
            photo.Url = p.Url;
            photo.Content = p.Content;
            return photo;
        }
        public static FollowerInforDTO converFollowToDTO(Follow f)
        {
            InstaContext context = new InstaContext();
            FollowerInforDTO s = new FollowerInforDTO();
            Person? p = context.People.FirstOrDefault(x => x.UserId.Equals(f.Follower));
            s.UserId = f.Follower;
            s.UserName = p.Username;
            s.FullName = p.FullName;
            return s;
        }
        public static FollowerInforDTO converFollowerToDTO(string user_id)
        {
            InstaContext context = new InstaContext();
            FollowerInforDTO s = new FollowerInforDTO();
            Person? p = context.People.FirstOrDefault(x => x.UserId.Equals(user_id));
            s.UserId = p.UserId;
            s.UserName = p.Username;
            s.FullName = p.FullName;
            return s;
        }

        public static FollowInforDTO convertFollowerNormalToDTO(string user_id, int follow_id)
        {
            InstaContext context = new InstaContext();
            FollowInforDTO s = new FollowInforDTO();
            Person? p = context.People.FirstOrDefault(x => x.UserId.Equals(user_id));
            s.followId = follow_id;
            s.UserId = p.UserId;
            s.UserName = p.Username;
            s.FullName = p.FullName;
            //convert image
            Photo? photo = context.Photos.FirstOrDefault(x => x.PhotoId == p.PhotoId);
            s.avatar = photo.Url;
            //default flag
            //s.flag = true;
            s.flag = false;
            return s;
        }
        public static Follow? checkFollow(string user_id, string follow_To)
        {
            InstaContext context = new InstaContext();
            return context.Follows.FirstOrDefault(x => x.UserId.Equals(follow_To) && x.Follower.Equals(user_id));
        }
        public static Boolean followUser(string user_id, string follow_To)
        {
            Boolean check = true;
            Follow? f = checkFollow(user_id, follow_To);
            try
            {
                InstaContext context = new InstaContext();
                if (f == null)
                {
                    Follow follow = new Follow();
                    follow.UserId = follow_To;
                    follow.Follower = user_id;
                    follow.RequestDate = DateTime.Now;
                    follow.ApproveDate = DateTime.Now;
                    follow.DeleteFlag = false;
                    context.Follows.Add(follow);
                    context.SaveChanges();

                }
                else
                {
                    f.DeleteFlag = false;
                    context.Follows.Update(f);
                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }
        public static Boolean unfollow(int follow_id)
        {
            Boolean check = true;
            try
            {

                InstaContext context = new InstaContext();
                Follow? follow = context.Follows.FirstOrDefault(x => x.Id == follow_id);
                if (follow != null)
                {
                    follow.DeleteFlag = true;
                }
                context.SaveChanges();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }
        public static Boolean refollow(int follow_id)
        {
            Boolean check = true;
            try
            {

                InstaContext context = new InstaContext();
                Follow? follow = context.Follows.FirstOrDefault(x => x.Id == follow_id);
                if (follow != null)
                {
                    follow.DeleteFlag = false;
                }
                context.SaveChanges();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }

        public static Boolean unfollower(string user_id, string follower)
        {
            Boolean check = true;
            try
            {
                InstaContext context = new InstaContext();
                Follow? follow = context.Follows.FirstOrDefault(x => x.UserId.Equals(user_id)
                && x.Follower.Equals(follower));
                if (follow == null)
                {
                    return false;
                }
                else
                {
                    check = true;
                    follow.DeleteFlag = true;
                    context.Follows.Update(follow);
                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }

        public static UserInforDTO getUserByUsername(string username)
        {
            InstaContext context = new InstaContext();
            Person p = context.People.FirstOrDefault(x => x.Username.ToLower().Equals(username.ToLower()));
            if (p != null)
            {
                //set dto
                UserInforDTO user = new UserInforDTO();
                user.UserId = p.UserId;
                user.Username = p.Username;
                user.Email = p.Email;

                return user;
            }
            else
            {
                return null;
            }
        }
        public static UserInforDTO getUserByEmail(string email)
        {
            InstaContext context = new InstaContext();
            Person p = context.People.FirstOrDefault(x => x.Email.ToLower().Equals(email.ToLower()));
            if (p != null)
            {
                //set dto
                UserInforDTO user = new UserInforDTO();
                user.UserId = p.UserId;
                user.Username = p.Username;
                user.Email = p.Email;

                return user;
            }
            else
            {
                return null;
            }
        }

        public static bool register(UserRegisterDTO userRegisterDTO)
        {
            Boolean check = true;
            try
            {
                InstaContext context = new InstaContext();
                String userID = "user" + context.People.ToList().Count;
                Photo? photo = context.Photos.FirstOrDefault(x => x.PhotoId == 1);
                Person person = new Person();
                person.UserId = userID;
                person.Username = userRegisterDTO.username;
                person.Password = userRegisterDTO.password;
                person.FullName = userRegisterDTO.username;
                person.Description = "No limit";
                person.Age = null;
                person.Gender = 1;
                person.Location = "vn";
                person.Email = userRegisterDTO.email;
                person.Status = 1;
                person.PhotoId = 1;
                person.IsPrivate = true;
                person.DeleteFlag = null;
                person.Photo = photo;
                person.Comments = null;
                person.FollowFollowerNavigations = null;
                person.FollowUsers = null;
                person.Likes = null;
                person.Notifies = null;
                person.NotifyUsers = null;
                person.Posts = null;
                person.RoomMembers = null;
                context.People.Add(person);
                context.SaveChanges();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }
        public bool isRegister(UserRegisterDTO userRegisterDTO)
        {
            Boolean check = true;
            try
            {
                InstaContext context = new InstaContext();
                String userID = "user" + context.People.ToList().Count;
                Photo? photo = context.Photos.FirstOrDefault(x => x.PhotoId == 1);
                Person person = new Person();
                person.UserId = userID;
                person.Username = userRegisterDTO.username;
                person.Password = userRegisterDTO.password;
                person.FullName = userRegisterDTO.username;
                person.Description = "No limit";
                person.Age = null;
                person.Gender = 1;
                person.Location = "vn";
                person.Email = userRegisterDTO.email;
                person.Status = 1;
                person.PhotoId = 1;
                person.IsPrivate = true;
                person.DeleteFlag = null;
                person.Photo = photo;
                person.Comments = null;
                person.FollowFollowerNavigations = null;
                person.FollowUsers = null;
                person.Likes = null;
                person.Notifies = null;
                person.NotifyUsers = null;
                person.Posts = null;
                person.RoomMembers = null;
                context.People.Add(person);
                context.SaveChanges();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }

        public static List<UserToManageDTO> getUserToManage(string userId)
        {
            List<UserToManageDTO> users = new List<UserToManageDTO>();
            InstaContext context = new InstaContext();
            List<Person> p = context.People.Where(x => !x.UserId.ToLower().Equals(userId.ToLower())).ToList();
            if (p != null)
            {
                foreach (Person person in p)
                {
                    UserToManageDTO user = new UserToManageDTO();
                    user.userId = person.UserId;
                    user.username = person.Username;
                    user.fullName = person.FullName;
                    user.email = person.Email;
                    if (person.Status == 1)
                    {
                        user.isActive = true;
                    }
                    else
                    {
                        user.isActive = false;
                    }
                    //convert image
                    Photo? photo = context.Photos.FirstOrDefault(x => x.PhotoId == person.PhotoId);
                    user.avartarImage = convertToPhotoDTO(photo);
                    //default flag
                    users.Add(user);
                }

                return users;
            }
            else
            {
                return null;
            }
        }
        public static bool activeUser(string userId)
        {
            Boolean check = true;
            try
            {
                InstaContext context = new InstaContext();
                Person person = context.People.FirstOrDefault(x => x.UserId.ToLower() == userId.ToLower());
                if (person == null)
                {
                    return false;
                }
                else
                {
                    person.Status = 1;
                    context.Update(person);
                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }

        public static bool deActiveUser(string userId)
        {
            Boolean check = true;
            try
            {

                InstaContext context = new InstaContext();
                Person person = context.People.FirstOrDefault(x => x.UserId.ToLower() == userId.ToLower());
                if (person == null)
                {
                    return false;
                }
                else
                {
                    person.Status = 0;
                    context.Update(person);
                    context.SaveChanges();
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }

        internal static bool getUserByUsernameAndEmail(string usename, string email)
        {
            bool check = true;
            try
            {

                InstaContext context = new InstaContext();
                Person person = context.People.FirstOrDefault(x => x.Username.ToLower() == usename.ToLower());
                if (person != null)
                {
                    if (person.Email.Equals(email))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                }
                else
                {
                    return false;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }

        internal static bool sendNewPass(string email, string newPass)
        {
            Boolean check = true;
            try
            {
                var smtpClient = new SmtpClient("smtp.gmail.com", 587)
                {
                    Credentials = new NetworkCredential("toanpvdt224@gmail.com", "anhtoan123"),
                    EnableSsl = true
                };

                var message = new MailMessage("toanpvdt224@gmail.com", "toanpv224@gmail.com", "[Reset Passworrd]", "Your new password is: " + newPass);

                smtpClient.Send(message);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }

        internal bool GetRegisterUser(string username, string email)
        {
            Person p = new Person();
            Boolean check = true;
            try
            {

                InstaContext context = new InstaContext();
                 p = context.People.FirstOrDefault(x => x.Username.ToLower() == username.ToLower());
                if (p != null)
                {
                    if (p.Email.Equals(email))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                }
                else
                {
                    return false;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                return false;
            }
            return check;
        }
        public static string GetUsernameByID(string id)
        {
            InstaContext context = new InstaContext();
            Person p = context.People.FirstOrDefault(x => x.UserId.Equals(id));
            if (p != null)
            {
                return p.Username != null ? p.FullName : "";
            }
            return "";
        }
        public static List<string> GetUsersByRoom(int roomId)
        {
            InstaContext context = new InstaContext();
            return context.RoomMembers
                .Where(x => x.RoomId == roomId && x.Member != null)
                .Select(x => x.Member)
                .ToList();
        }
        public static List<Person> SearchUser(string user_id)
        {
            InstaContext context = new InstaContext();
            List<Person> people = context.People.Where(x => x.Username.ToLower().Contains(user_id.ToLower())).Include(x => x.Photo).ToList();
            //List<UserInforDTO> users = new List<UserInforDTO>();
            //foreach (var item in people)
            //{
            //    //set dto
            //    UserInforDTO user = new UserInforDTO();
            //    user.Username = item.Username;
            //    user.FullName = item.FullName;
            //    user.Age = item.Age;
            //    user.UserId = item.UserId;
            //    user.Gender = item.Gender;
            //    user.Email = item.Email;
            //    user.Location = item.Location;
            //    user.IsPrivate = item.IsPrivate;

            //    Photo? avt_raw = context.Photos.FirstOrDefault(x => x.PhotoId == item.PhotoId);
            //    user.avartarImage = item.Photo.Url;

            //    users.Add(user);
            //}

            foreach (var item in people)
            {
                item.Photo.Person = null;
            }
            return people;
        }
    }
}
