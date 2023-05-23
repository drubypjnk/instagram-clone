using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.Options;
using System;
using System.Collections.Concurrent;
using System.Diagnostics;

namespace SE1611_NET_Group2_Project
{
    public class NotificationHub : Hub
    {
        public static ConcurrentDictionary<string, List<string>> ConnectedUsers = new ConcurrentDictionary<string, List<string>>();
        private static int _clientCount = 0;
        public async Task PushNotification(List<string> users, string notify)
        {
            await Clients.Users(users).SendAsync("ReceiveNotification", notify);
        }

        public async override Task OnConnectedAsync()
        {
            Trace.TraceInformation("MapHub started. ID: {0}", Context.ConnectionId);
            string authToken = Context.GetHttpContext().Request.Headers["Authorization"];
            var httpCtx = Context.GetHttpContext();
            var someHeaderValue = httpCtx.Request.Headers["userId"].ToString();
            var userName = someHeaderValue;
            var connectedId = Context.ConnectionId;

            // Try to get a List of existing user connections from the cache
            List<string> existingUserConnectionIds;
            ConnectedUsers.TryGetValue(userName, out existingUserConnectionIds);

            // happens on the very first connection from the user
            if (existingUserConnectionIds == null)
            {
                existingUserConnectionIds = new List<string>();
            }

            // First add to a List of existing user connections (i.e. multiple web browser tabs)
            existingUserConnectionIds.Add(Context.ConnectionId);


            // Add to the global dictionary of connected users
            ConnectedUsers.TryAdd(userName, existingUserConnectionIds);
            _clientCount++;
            await base.OnConnectedAsync();
            await Clients.All.SendAsync("ClientCountUpdated", _clientCount);
        }
        public async override Task OnDisconnectedAsync(Exception e)
        {
            var httpCtx = Context.GetHttpContext();
            var someHeaderValue = httpCtx.Request.Headers["userId"].ToString();
            var userName = someHeaderValue;

            List<string> existingUserConnectionIds;
            ConnectedUsers.TryGetValue(userName, out existingUserConnectionIds);

            // remove the connection id from the List 
            existingUserConnectionIds.Remove(Context.ConnectionId);

            // If there are no connection ids in the List, delete the user from the global cache (ConnectedUsers).
            if (existingUserConnectionIds.Count == 0)
            {
                // if there are no connections for the user,
                // just delete the userName key from the ConnectedUsers concurent dictionary
                List<string> garbage; // to be collected by the Garbage Collector
                ConnectedUsers.TryRemove(userName, out garbage);
            }
            _clientCount--;
            await base.OnDisconnectedAsync(e);
            await Clients.All.SendAsync("ClientCountUpdated", _clientCount);
        }
        public async Task Send(string message)
        {
            var token = message;
            // Use the token to identify the user
            var connectedId = Context.ConnectionId;

            // Try to get a List of existing user connections from the cache
            List<string> existingUserConnectionIds;
            ConnectedUsers.TryGetValue(token, out existingUserConnectionIds);

            // happens on the very first connection from the user
            if (existingUserConnectionIds == null)
            {
                existingUserConnectionIds = new List<string>();
            }

            // First add to a List of existing user connections (i.e. multiple web browser tabs)
            existingUserConnectionIds.Add(Context.ConnectionId);


            // Add to the global dictionary of connected users
            ConnectedUsers.TryAdd(token, existingUserConnectionIds);
        }
    }
}
