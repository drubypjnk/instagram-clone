// Please see documentation at https://docs.microsoft.com/aspnet/core/client-side/bundling-and-minification
// for details on configuring this project to bundle and minify static web assets.

// Write your JavaScript code.
$(() => {
    document.querySelector('.footer').outerHTML = ''
    LoadRooms();
    var connection = new signalR.HubConnectionBuilder().withUrl("/messageHub").build();
    connection.start();

    connection.on("ReceiveMessage", function (message) {
        getMessage(message);
    });

    function roomRowFormat(v) {
        let last_message_content = v.lastMassage ? v.lastMassage.Content : '';
        let last_message_time = v.lastMassage ? timeSince(new Date(v.lastMassage.CreatedDate)) : '';
        return `<div id="room-row-${v.room.RoomId}" onClick="window.location.href = '/messages?roomId=${v.room.RoomId}'">
                    <div class="p-2 room-btn">
                        <img src="${v.receiver.avartarImage.Url}"></img>
                        <div class="ms-3">
                            <h5 class="mb-1">${v.receiver.Username}</h5>
                            <span class="last-message d-flex">
                                <p class="last-message-content">${last_message_content}</p>
                                <p class="message-time ms-2">${last_message_time}</p>
                            </span>
                        </div>
                    </div>
                </div>`;
    }
    function sortRoom(list) {
        return list.sort((a, b) => {
            if (a.lastMassage && b.lastMassage) {
                return (new Date(a.lastMassage.CreatedDate) > new Date(b.lastMassage.CreatedDate)) ? -1 : 1;
            } else if (b.lastMassage) {
                return 1;
            } else {
                return -1;
            }
        })
    }

    function LoadRooms() {
        var row = '';
        $.ajax({
            url: '/api/Room',
            method: 'GET',
            data: {
                userId: user_id
            },
            success: (result) => {
                console.log(result);
                sortRoom(JSON.parse(result)).forEach((v) => {
                    row += roomRowFormat(v)
                })

                $("#rooms-container").html(row);
            },
            error: (error) => {
                console.log(error);
            }
        })
    }

    document.querySelector('input[name="message_text"]').addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });

    document.querySelector('input[name="message_text"]').addEventListener('input', function (e) {
        if (document.querySelector('input[name="message_text"]').value !== "") {
            document.querySelector('#send-btn').style = ""
        } else {
            document.querySelector('#send-btn').style = "display: none"
        }
    });
    document.querySelector('#send-btn').addEventListener('click', function (e) {
        sendMessage();
    });

    function sendMessage() {
        let box_input = document.querySelector('input[name="message_text"]');
        $.ajax({
            url: '/api/Message',
            method: 'POST',
            headers:
            {
                "RequestVerificationToken": token
            },
            data: {
                roomId: room_id,
                messageContent: box_input.value
            },
            success: (result) => {
                box_input.value = '';
                document.querySelector('#send-btn').style = "display: none";
            },
            error: (error) => {
                console.log(error);
            }
        })
    }

    function getMessage(message) {
        if (message.roomId == room_id) {
            let justify_content = message.sender != user_id ? "other-message" : "my-message";
            let row = `<div class="${justify_content}">
                            <div class="px-3 p-1 my-1 border border-2 rounded-pill">
                                ${message.messageContent}
                            </div>
                        </div>`
            document.querySelector('#messages-container').innerHTML += row;
            document.querySelector('#messages-container').scroll({ top: $('#messages-container')[0].scrollHeight, behavior: "smooth" })
        }
        //$(`#room-row-${message.roomId} .last-message`).html(`<p class="last-message-content">${message.messageContent}</p><p class="message-time ms-2">a minutes ago</p>`);
        LoadRooms()
    }

    document.querySelector('#messages-container').scroll({ top: $('#messages-container')[0].scrollHeight, behavior: "smooth" })

    function timeSince(date) {

        var seconds = Math.floor((new Date() - date) / 1000);

        var interval = seconds / 31536000;

        if (interval > 1) {
            return Math.floor(interval) + " years";
        }
        interval = seconds / 2592000;
        if (interval > 1) {
            return Math.floor(interval) + " months";
        }
        interval = seconds / 86400;
        if (interval > 1) {
            return Math.floor(interval) + " days";
        }
        interval = seconds / 3600;
        if (interval > 1) {
            return Math.floor(interval) + " hours";
        }
        interval = seconds / 60;
        if (interval > 1) {
            return Math.floor(interval) + " minutes";
        }
        return Math.floor(seconds) + " seconds";
    }
})