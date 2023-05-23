// Please see documentation at https://docs.microsoft.com/aspnet/core/client-side/bundling-and-minification
// for details on configuring this project to bundle and minify static web assets.

// Write your JavaScript code.
$(() => {
    var connection = new signalR.HubConnectionBuilder().withUrl("/notificationHub").build();
    connection.start().then(function () {
        connection.invoke("Send", mySessionValue).catch(function (err) {
            return console.error(err.message);
        });
    }).catch(function (err) {
        return console.error(err.message);
    });

    connection.on("ReceiveNotification", function (result) {
        LoadData(result);
    });
    connection.on("ClientCountUpdated", (count) => {
        console.log(`There are now ${count} clients connected.`);
    });
    function LoadData(result) {
        var data = JSON.parse(result);
        if (data.NotifyTitle == 1) {
            $('#message-item').find('.item-svg-container-parent').append(`
                                    <div class="notification-dot-container">
                                        <div class="notification-dot-container2">
                                            <span class="dot"></span>
                                        </div>
                                    </div>`);
        } else {
            $('#notification-item').find('.item-svg-container-parent').append(`
                                    <div class="notification-dot-container">
                                        <div class="notification-dot-container2">
                                            <span class="dot"></span>
                                        </div>
                                    </div>`);
        }
    }
})