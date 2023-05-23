using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Insta_Server.Models;
using Insta_Server.DTO;
using Insta_Server.Serivce;

namespace Insta_Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RoomsController : ControllerBase
    {

        // GET: api/Rooms/getRooms/userId
        [HttpGet("getRooms/{userId}")]
        public async Task<ActionResult<IEnumerable<RoomDTO>>> GetRooms(string userId)
        {
            return RoomService.getRooms(userId);
        }

        // GET: api/Rooms/roomId
        [HttpGet("getRoom/{userId}/{roomId}")]
        public async Task<ActionResult<RoomDTO>> GetRoom(string userId, int roomId)
        {
            return RoomService.getRoom(userId, roomId);
        }

        // GET: api/Rooms/roomId
        [HttpGet("getRoomByUsers/{userId1}/{userId2}")]
        public async Task<ActionResult<RoomDTO>> GetRoomByUsers(string userId1, string userId2)
        {
            return RoomService.getRoomByUsers(userId1, userId2);
        }


        //// PUT: api/Rooms/5
        //[HttpPut("{id}")]
        //public async Task<IActionResult> PutRoom(int id, Room room)
        //{
        //    if (id != room.RoomId)
        //    {
        //        return BadRequest();
        //    }

        //    _context.Entry(room).State = EntityState.Modified;

        //    try
        //    {
        //        await _context.SaveChangesAsync();
        //    }
        //    catch (DbUpdateConcurrencyException)
        //    {
        //        if (!RoomExists(id))
        //        {
        //            return NotFound();
        //        }
        //        else
        //        {
        //            throw;
        //        }
        //    }

        //    return NoContent();
        //}

        // POST: api/Rooms
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        //[HttpPost]
        //public async Task<ActionResult<Room>> PostRoom(Room room)
        //{
        //    _context.Rooms.Add(room);
        //    try
        //    {
        //        await _context.SaveChangesAsync();
        //    }
        //    catch (DbUpdateException)
        //    {
        //        if (RoomExists(room.RoomId))
        //        {
        //            return Conflict();
        //        }
        //        else
        //        {
        //            throw;
        //        }
        //    }

        //    return CreatedAtAction("GetRoom", new { id = room.RoomId }, room);
        //}

        //// DELETE: api/Rooms/5
        //[HttpDelete("{id}")]
        //public async Task<IActionResult> DeleteRoom(int id)
        //{
        //    var room = await _context.Rooms.FindAsync(id);
        //    if (room == null)
        //    {
        //        return NotFound();
        //    }

        //    _context.Rooms.Remove(room);
        //    await _context.SaveChangesAsync();

        //    return NoContent();
        //}

        //private bool RoomExists(int id)
        //{
        //    return _context.Rooms.Any(e => e.RoomId == id);
        //}
    }
}
