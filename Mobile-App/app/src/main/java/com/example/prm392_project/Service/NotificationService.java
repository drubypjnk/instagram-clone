package com.example.prm392_project.Service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.prm392_project.API.ApiService;
import com.example.prm392_project.Activity.MainActivity;
import com.example.prm392_project.Adapter.ActivityContainerAdapter;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.Model.ActivityContainer;
import com.google.gson.Gson;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.microsoft.signalr.TransportEnum;
import io.reactivex.rxjava3.core.Single;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private HubConnection mConnection;
    public IBinder myIBinder = new MyBinder();
    PendingIntent pendingIntent;
    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mConnection = HubConnectionBuilder.create(MyApp.BASE_URL+"notificationHub")
                .withHeader("userId", MyApp.GetUserId())
                .withTransport(TransportEnum.LONG_POLLING)
                .build();
//        Context context = getApplicationContext();
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("notification_clicked", true);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Create notification channel
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("startChanel","Start", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel1 = new NotificationChannel("messageChanel","Messages", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel2 = new NotificationChannel("commentChanel","Comments", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel3 = new NotificationChannel("reactChannel","Reacts", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel4 = new NotificationChannel("followChannel","Follows", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);
            notificationManager.createNotificationChannel(channel3);
            notificationManager.createNotificationChannel(channel4);
        }

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentText("Foreground Service is running")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setChannelId("startChanel").build();
//        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
        startForeground(1, notification);
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
//                .blockingAwait();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("01"
                    , "Channel name"
                    , NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(channel);
        }

        mConnection.on("ReceiveNotification", (result) ->{
            Gson gson = new Gson(); // khởi tạo Gson
            com.example.prm392_project.Model.Notification notify = gson.fromJson(result, com.example.prm392_project.Model.Notification.class);

            Context context = getApplicationContext();

            String channelId = "";
            int notificationId = 0;
            if(notify.getNotifyType() == 1){
                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("notification_type", "message");
                intent1.putExtra("targetId", notify.getNotifyTitle());
                pendingIntent = PendingIntent.getActivity(context, 1000, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                channelId = "messageChanel";
                notificationId = Integer.parseInt(notify.getNotifyTitle());
            } else if(notify.getNotifyType() == 2 ){
                Intent intent2 = new Intent(context, MainActivity.class);
                intent2.putExtra("notification_type", "post");
                intent2.putExtra("targetId", notify.getNotifyTitle());
                pendingIntent = PendingIntent.getActivity(context, 1001, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                channelId = "commentChanel";
                notificationId = Integer.parseInt(notify.getNotifyTitle());
            } else if(notify.getNotifyType() == 4 ){
                Intent intent4 = new Intent(context, MainActivity.class);
                intent4.putExtra("notification_type", "follow");
                intent4.putExtra("targetId", notify.getNotifyTitle());
                pendingIntent = PendingIntent.getActivity(context, 1003, intent4, PendingIntent.FLAG_UPDATE_CURRENT);
                channelId = "followChannel";
                notificationId = notify.getNotifyTitle().hashCode();
            }else if(notify.getNotifyType() == 3 ){
                Intent intent3 = new Intent(context, MainActivity.class);
                intent3.putExtra("notification_type", "post");
                intent3.putExtra("targetId", notify.getNotifyTitle());
                pendingIntent = PendingIntent.getActivity(context, 1002, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
                channelId = "reactChannel";
                notificationId = Integer.parseInt(notify.getNotifyTitle());
            }


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setContentText(notify.getNotifyContent())
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setChannelId(channelId)
                    .setContentIntent(pendingIntent);
//            if(notify.getNotifyType() == 1){
//                notificationBuilder.setContentTitle(notify);
//            }

            notificationManager.notify(notificationId, notificationBuilder.build());

            //When receive notification, reload ActivityList
            ApiService myApiService;
            myApiService = ApiService.apiService;
            myApiService.getActivities(MyApp.GetUserId()).enqueue(new Callback<List<ActivityContainer>>() {
                @Override
                public void onResponse(Call<List<ActivityContainer>> call, Response<List<ActivityContainer>> response) {
                    MyApp.activityContainers = response.body();
                }

                @Override
                public void onFailure(Call<List<ActivityContainer>> call, Throwable t) {
                }
            });
        }, String.class);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("closed", "closed1");
        if (mConnection != null && mConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            mConnection.stop();
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d("closed", "closed");
        if (mConnection != null && mConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            mConnection.stop();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myIBinder;
    }
    class MyBinder extends Binder{
        public NotificationService getServices(){
            return NotificationService.this;
        }
    }
}
