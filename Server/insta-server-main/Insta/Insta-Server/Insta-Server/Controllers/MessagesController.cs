using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Insta_Server.Models;
using Insta_Server.Serivce;
using Insta_Server.DTO;
using Microsoft.AspNetCore.SignalR;
using System.Reflection.Metadata;
using Insta_Server.Services;

namespace Insta_Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MessagesController : ControllerBase
    {
        private readonly InstaContext _context;
        private IHubContext<MessageHub> _messageHubContext;
        private IHubContext<NotificationHub> _hubContext;
        public MessagesController(InstaContext context, IHubContext<MessageHub> messageHubContext, IHubContext<NotificationHub> hubContext)
        {
            _context = context;
            _messageHubContext = messageHubContext;
            _hubContext = hubContext;
        }

        // GET: api/Messages
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Message>>> GetAllMessages()
        {
            _messageHubContext.Clients.Group("user").SendAsync("ReceiveMessage", "");
            return NoContent();
        }

        // GET: api/Messages
        [HttpGet("getMessages/{roomId}")]
        public async Task<ActionResult<IEnumerable<MessageDTO>>> GetMessages(int roomId)
        {
            return MessageService.GetMessages(roomId);
        }

        // GET: api/Messages/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Message>> GetMessage(int id)
        {
          if (_context.Messages == null)
          {
              return NotFound();
          }
            var message = await _context.Messages.FindAsync(id);

            if (message == null)
            {
                return NotFound();
            }

            return message;
        }

        // PUT: api/Messages/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutMessage(int id, Message message)
        {
            if (id != message.MessageId)
            {
                return BadRequest();
            }

            _context.Entry(message).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!MessageExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        //POST: api/Messages
        //To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<string>> PostMessage(string userId, int roomId, string messageContent)
        {
            if (_context.Messages == null)
            {
                return Problem("Entity set 'InstaContext.Messages'  is null.");
            }
            Message message = new Message()
            {
                Content = messageContent,
                CreatedDate = DateTime.Now,
                Author = _context.RoomMembers.FirstOrDefault(rm => rm.Room.RoomId == roomId && rm.Member == userId).Id,
                DeleteFlag = false
            };
            _context.Messages.Add(message);
            List<RoomMember> members = _context.RoomMembers.Where(rm => rm.Room.RoomId == roomId).ToList();
            foreach (RoomMember m in members)
            {
                _messageHubContext.Clients.Group(m.Member).SendAsync("ReceiveMessage", "");
            }
            await _context.SaveChangesAsync();

            NotificationService.Notificate(NotificationType.MessageNotification, userId, 0, roomId + "", _hubContext);
            return NoContent();
        }

        // DELETE: api/Messages/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteMessage(int id)
        {
            if (_context.Messages == null)
            {
                return NotFound();
            }
            var message = await _context.Messages.FindAsync(id);
            if (message == null)
            {
                return NotFound();
            }

            _context.Messages.Remove(message);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool MessageExists(int id)
        {
            return (_context.Messages?.Any(e => e.MessageId == id)).GetValueOrDefault();
        }
    }
}
