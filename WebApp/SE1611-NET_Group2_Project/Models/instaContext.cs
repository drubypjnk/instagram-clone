using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace SE1611_NET_Group2_Project.Models
{
    public partial class InstaContext : DbContext
    {
        public InstaContext()
        {
        }

        public InstaContext(DbContextOptions<InstaContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Comment> Comments { get; set; } = null!;
        public virtual DbSet<Follow> Follows { get; set; } = null!;
        public virtual DbSet<Like> Likes { get; set; } = null!;
        public virtual DbSet<Message> Messages { get; set; } = null!;
        public virtual DbSet<Notify> Notifies { get; set; } = null!;
        public virtual DbSet<NotifyUser> NotifyUsers { get; set; } = null!;
        public virtual DbSet<Permission> Permissions { get; set; } = null!;
        public virtual DbSet<Person> People { get; set; } = null!;
        public virtual DbSet<Photo> Photos { get; set; } = null!;
        public virtual DbSet<Post> Posts { get; set; } = null!;
        public virtual DbSet<React> Reacts { get; set; } = null!;
        public virtual DbSet<Role> Roles { get; set; } = null!;
        public virtual DbSet<Room> Rooms { get; set; } = null!;
        public virtual DbSet<RoomMember> RoomMembers { get; set; } = null!;
        public virtual DbSet<Setting> Settings { get; set; } = null!;
        public virtual DbSet<Template> Templates { get; set; } = null!;
        public virtual DbSet<Visibility> Visibilities { get; set; } = null!;

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                var config = new ConfigurationBuilder().AddJsonFile("appsettings.json").Build();
                optionsBuilder.UseSqlServer(config.GetConnectionString("ConString"));
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Comment>(entity =>
            {
                entity.ToTable("Comment");

                entity.Property(e => e.CommentId).HasColumnName("comment_id");

                entity.Property(e => e.Author)
                    .HasMaxLength(64)
                    .IsUnicode(false)
                    .HasColumnName("author");

                entity.Property(e => e.Content)
                    .HasColumnType("text")
                    .HasColumnName("content");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("created_date");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.PostId).HasColumnName("post_id");

                entity.Property(e => e.VisibilityId).HasColumnName("visibility_id");

                entity.HasOne(d => d.AuthorNavigation)
                    .WithMany(p => p.Comments)
                    .HasForeignKey(d => d.Author)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Comment.author");

                entity.HasOne(d => d.Post)
                    .WithMany(p => p.Comments)
                    .HasForeignKey(d => d.PostId)
                    .HasConstraintName("FK_Comment.post_id");
            });

            modelBuilder.Entity<Follow>(entity =>
            {
                entity.ToTable("Follow");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.ApproveDate)
                    .HasColumnType("datetime")
                    .HasColumnName("approve_date");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.Follower)
                    .HasMaxLength(64)
                    .IsUnicode(false)
                    .HasColumnName("follower");

                entity.Property(e => e.RequestDate)
                    .HasColumnType("datetime")
                    .HasColumnName("request_date");

                entity.Property(e => e.UserId)
                    .HasMaxLength(64)
                    .IsUnicode(false)
                    .HasColumnName("user_id");

                entity.HasOne(d => d.FollowerNavigation)
                    .WithMany(p => p.FollowFollowerNavigations)
                    .HasForeignKey(d => d.Follower)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Follow.follower");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.FollowUsers)
                    .HasForeignKey(d => d.UserId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Follow.user_id");
            });

            modelBuilder.Entity<Like>(entity =>
            {
                entity.ToTable("Like");

                entity.Property(e => e.LikeId).HasColumnName("like_id");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.PostId).HasColumnName("post_id");

                entity.Property(e => e.UserId)
                    .HasMaxLength(64)
                    .IsUnicode(false)
                    .HasColumnName("user_id");

                entity.HasOne(d => d.Post)
                    .WithMany(p => p.Likes)
                    .HasForeignKey(d => d.PostId)
                    .HasConstraintName("FK_Like.post_id");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Likes)
                    .HasForeignKey(d => d.UserId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Like.user_id");
            });

            modelBuilder.Entity<Message>(entity =>
            {
                entity.ToTable("Message");

                entity.Property(e => e.MessageId).HasColumnName("message_id");

                entity.Property(e => e.Author).HasColumnName("author");

                entity.Property(e => e.Content)
                    .HasColumnType("text")
                    .HasColumnName("content");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("created_date");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.ReactId).HasColumnName("react_id");

                entity.Property(e => e.RoomId).HasColumnName("room_id");

                entity.HasOne(d => d.AuthorNavigation)
                    .WithMany(p => p.Messages)
                    .HasForeignKey(d => d.Author)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Message.author");
            });

            modelBuilder.Entity<Notify>(entity =>
            {
                entity.ToTable("Notify");

                entity.Property(e => e.NotifyId).HasColumnName("notify_id");

                entity.Property(e => e.CreateDate)
                    .HasColumnType("datetime")
                    .HasColumnName("create_date");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.NotifyContent)
                    .HasColumnType("text")
                    .HasColumnName("notify_content");

                entity.Property(e => e.NotifyTitle)
                    .HasColumnType("text")
                    .HasColumnName("notify_title");

                entity.Property(e => e.NotifyType).HasColumnName("notify_type");

                entity.Property(e => e.Status).HasColumnName("status");

                entity.Property(e => e.UserId)
                    .HasMaxLength(64)
                    .IsUnicode(false)
                    .HasColumnName("user_id");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Notifies)
                    .HasForeignKey(d => d.UserId)
                    .HasConstraintName("FK_NotifyPeople.user_id");
            });

            modelBuilder.Entity<NotifyUser>(entity =>
            {
                entity.ToTable("NotifyUser");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.NotifyId).HasColumnName("notify_id");

                entity.Property(e => e.NotifyUser1)
                    .HasMaxLength(64)
                    .IsUnicode(false)
                    .HasColumnName("notify_user");

                entity.HasOne(d => d.Notify)
                    .WithMany(p => p.NotifyUsers)
                    .HasForeignKey(d => d.NotifyId)
                    .HasConstraintName("FK_NotifyUser.notify_id");

                entity.HasOne(d => d.NotifyUser1Navigation)
                    .WithMany(p => p.NotifyUsers)
                    .HasForeignKey(d => d.NotifyUser1)
                    .HasConstraintName("FK_NotifyUserCreated.user_id");
            });

            modelBuilder.Entity<Permission>(entity =>
            {
                entity.ToTable("Permission");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.Key)
                    .HasColumnType("text")
                    .HasColumnName("key");

                entity.Property(e => e.Value)
                    .HasColumnType("text")
                    .HasColumnName("value");
            });

            modelBuilder.Entity<Person>(entity =>
            {
                entity.HasKey(e => e.UserId)
                    .HasName("PK__Person__B9BE370F31091E97");

                entity.ToTable("Person");

                entity.HasIndex(e => e.PhotoId, "UK_PersonAvatar_PhotoId")
                    .IsUnique();

                entity.Property(e => e.UserId)
                    .HasMaxLength(64)
                    .IsUnicode(false)
                    .HasColumnName("user_id");

                entity.Property(e => e.Age).HasColumnName("age");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.Description)
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .HasColumnName("description");

                entity.Property(e => e.Email)
                    .HasColumnType("text")
                    .HasColumnName("email");

                entity.Property(e => e.FullName)
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .HasColumnName("full_name");

                entity.Property(e => e.Gender).HasColumnName("gender");

                entity.Property(e => e.IsPrivate).HasColumnName("is_private");

                entity.Property(e => e.Location)
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .HasColumnName("location");

                entity.Property(e => e.Password)
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .HasColumnName("password");

                entity.Property(e => e.PhotoId).HasColumnName("photo_id");

                entity.Property(e => e.Status).HasColumnName("status");

                entity.Property(e => e.Username)
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .HasColumnName("username");

                entity.HasOne(d => d.Photo)
                    .WithOne(p => p.Person)
                    .HasForeignKey<Person>(d => d.PhotoId)
                    .HasConstraintName("FK_Person_Photo.post_id");
            });

            modelBuilder.Entity<Photo>(entity =>
            {
                entity.ToTable("Photo");

                entity.Property(e => e.PhotoId).HasColumnName("photo_id");

                entity.Property(e => e.Content)
                    .HasColumnType("text")
                    .HasColumnName("content");

                entity.Property(e => e.CreateDate)
                    .HasColumnType("datetime")
                    .HasColumnName("create_date");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.PostId).HasColumnName("post_id");

                entity.Property(e => e.Url)
                    .HasColumnType("text")
                    .HasColumnName("url");

                entity.Property(e => e.VisibilityId).HasColumnName("visibility_id");

                entity.HasOne(d => d.Post)
                    .WithMany(p => p.Photos)
                    .HasForeignKey(d => d.PostId)
                    .HasConstraintName("FK_Photo.post_id");
            });

            modelBuilder.Entity<Post>(entity =>
            {
                entity.ToTable("Post");

                entity.Property(e => e.PostId).HasColumnName("post_id");

                entity.Property(e => e.Author)
                    .HasMaxLength(64)
                    .IsUnicode(false)
                    .HasColumnName("author");

                entity.Property(e => e.Content)
                    .HasColumnType("text")
                    .HasColumnName("content");

                entity.Property(e => e.CreatedDate)
                    .HasColumnType("datetime")
                    .HasColumnName("created_date");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.Title)
                    .HasColumnType("text")
                    .HasColumnName("title");

                entity.Property(e => e.UpdateDate)
                    .HasColumnType("datetime")
                    .HasColumnName("update date");

                entity.Property(e => e.VisibilityId).HasColumnName("visibility_id");

                entity.HasOne(d => d.AuthorNavigation)
                    .WithMany(p => p.Posts)
                    .HasForeignKey(d => d.Author)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Post.author");
            });

            modelBuilder.Entity<React>(entity =>
            {
                entity.ToTable("React");

                entity.Property(e => e.ReactId).HasColumnName("react_id");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.Name)
                    .HasColumnType("text")
                    .HasColumnName("name");
            });

            modelBuilder.Entity<Role>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("Role");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Name)
                    .HasColumnType("text")
                    .HasColumnName("name");
            });

            modelBuilder.Entity<Room>(entity =>
            {
                entity.ToTable("Room");

                entity.Property(e => e.RoomId).HasColumnName("room_id");

                entity.Property(e => e.CreateDate)
                    .HasColumnType("datetime")
                    .HasColumnName("create_date");

                entity.Property(e => e.DeleteDate)
                    .HasColumnType("datetime")
                    .HasColumnName("delete_date");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.RoomTitle)
                    .HasColumnType("text")
                    .HasColumnName("room_title");
            });

            modelBuilder.Entity<RoomMember>(entity =>
            {
                entity.ToTable("RoomMember");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.LastMessage).HasColumnName("last_message");

                entity.Property(e => e.Member)
                    .HasMaxLength(64)
                    .IsUnicode(false)
                    .HasColumnName("member");

                entity.Property(e => e.Nickname)
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .HasColumnName("nickname");

                entity.Property(e => e.RoomId).HasColumnName("room_id");

                entity.HasOne(d => d.MemberNavigation)
                    .WithMany(p => p.RoomMembers)
                    .HasForeignKey(d => d.Member)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_RoomMember.member");

                entity.HasOne(d => d.Room)
                    .WithMany(p => p.RoomMembers)
                    .HasForeignKey(d => d.RoomId)
                    .HasConstraintName("FK_RoomMember.room_id");
            });

            modelBuilder.Entity<Setting>(entity =>
            {
                entity.ToTable("Setting");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.DateCreated)
                    .HasColumnType("datetime")
                    .HasColumnName("date_created");

                entity.Property(e => e.DateUpdated)
                    .HasColumnType("datetime")
                    .HasColumnName("date_updated");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.Key)
                    .HasColumnType("text")
                    .HasColumnName("key");

                entity.Property(e => e.Value)
                    .HasColumnType("text")
                    .HasColumnName("value");
            });

            modelBuilder.Entity<Template>(entity =>
            {
                entity.ToTable("Template");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Content)
                    .HasColumnType("text")
                    .HasColumnName("content");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.Key).HasColumnName("key");
            });

            modelBuilder.Entity<Visibility>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("Visibility");

                entity.Property(e => e.DeleteFlag).HasColumnName("delete_flag");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Key)
                    .HasColumnType("text")
                    .HasColumnName("key");

                entity.Property(e => e.Value)
                    .HasColumnType("text")
                    .HasColumnName("value");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
