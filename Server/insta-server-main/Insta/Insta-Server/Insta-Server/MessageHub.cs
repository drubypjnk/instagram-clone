using Insta_Server.Models;
using Microsoft.AspNetCore.SignalR;
using Microsoft.EntityFrameworkCore;
using System.Diagnostics;

namespace Insta_Server
{
    public class MessageHub : Hub
    {
        public override Task OnConnectedAsync()
        {
            string userId = Context.GetHttpContext().Request.Headers["userId"].ToString();
            Groups.AddToGroupAsync(Context.ConnectionId, userId);
            Debug.WriteLine("a");
            return base.OnConnectedAsync();
        }
        public async Task SendMessage(string content)
        {
            await Clients.Groups(Context.User.Identity.Name).SendAsync("ReceiveMessage", content);
        }
    }
}
