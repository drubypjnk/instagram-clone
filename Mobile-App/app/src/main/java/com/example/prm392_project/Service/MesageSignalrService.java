package com.example.prm392_project.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

import androidx.core.app.NotificationCompat;
import com.example.prm392_project.Activity.RoomActivity;
import com.example.prm392_project.Application.MyApp;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.microsoft.signalr.TransportEnum;

public class MesageSignalrService extends Service {
    private HubConnection mConnection;
    public IBinder mmessageIBinder = new MessageBinder();
    public MesageSignalrService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MesageSignalrService", "on create");
        mConnection = HubConnectionBuilder.create(MyApp.BASE_URL + "messageHub")
                .withHeader("userId", MyApp.GetUserId())
                .withTransport(TransportEnum.LONG_POLLING)
                .build();

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_02";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .setContentText("Message service")
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setChannelId("my_channel_02").build();
            startForeground(2, notification);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mConnection.start()
                .doOnComplete(() -> {
                    Log.d("SignalR", "success");
                })
                .doOnError((error) -> {
                    Log.d("SignalR", error.getMessage());
                });
        mConnection.on("ReceiveMessage", (result) ->{
            RoomActivity activity = RoomActivity.getInstance();
            if(activity != null){
                activity.loadMesage();
            }
            Log.d("SignalR", "load message");
        }, String.class);
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mConnection != null && mConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            mConnection.stop();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mmessageIBinder;
    }

    class MessageBinder extends Binder{
        public MesageSignalrService getServices(){
            return MesageSignalrService.this;
        }
    }
}