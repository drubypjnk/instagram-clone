using Microsoft.AspNetCore.SignalR;
using System.Diagnostics;

namespace SE1611_NET_Group2_Project
{
    public class MessageHub : Hub
    {
        public override Task OnConnectedAsync()
        {
            //var httpCtx = Context.GetHttpContext();
            //var userId = httpCtx.Request.Headers["userId"].ToString();
            //string userId = Context.GetHttpContext().Request.Headers["userId"].ToString();
            string userId = "user";
            Groups.AddToGroupAsync(Context.ConnectionId, userId);
            return base.OnConnectedAsync();
        }
    }
}
