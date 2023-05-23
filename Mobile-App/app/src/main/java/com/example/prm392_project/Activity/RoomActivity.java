package com.example.prm392_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_project.Adapter.MessageAdapter;
import com.example.prm392_project.Application.MyApp;
import com.example.prm392_project.DTO.MessageBodyDTO;
import com.example.prm392_project.DTO.MessageDTO;
import com.example.prm392_project.DTO.RoomDTO;
import com.example.prm392_project.Interfaces.IApi;
import com.example.prm392_project.Model.Message;
import com.example.prm392_project.R;
import com.example.prm392_project.Service.MesageSignalrService;
import com.example.prm392_project.Utils.UtilImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomActivity extends AppCompatActivity {
    private static RoomActivity instance;
    private int room_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        instance = this;
        room_id = getIntent().getExtras().getInt("roomId");
        loadRoomInformation();
        connectToSignalR();
        loadMesage();
        ((EditText)findViewById(R.id.edt_message_input)).setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });
    }
    public void loadRoomInformation(){
        IApi.myApi.getRoom(MyApp.GetUserId(), room_id).enqueue(new Callback<RoomDTO>() {
            @Override
            public void onResponse(Call<RoomDTO> call, Response<RoomDTO> response) {
                if(response.isSuccessful()){
                    Log.d("call API [GET] Rooms/{roomId}", "data: " + response.body());
                    bindingRoomInformation(response.body());
                } else {
                    Log.d("call API [GET] Rooms/{roomId} failed", response.code() + ", " + response.message());
                }
            }
            @Override
            public void onFailure(Call<RoomDTO> call, Throwable t) {
                Log.d("call API [GET] Rooms/{roomId} failed", t.getMessage());
            }
        });
    }
    public static RoomActivity getInstance() { return instance; }

    public void loadMesage(){
        IApi.myApi.getMessages(room_id).enqueue(new Callback<List<MessageDTO>>() {
            @Override
            public void onResponse(Call<List<MessageDTO>> call, Response<List<MessageDTO>> response) {
                if(response.isSuccessful()){
                    Log.d("call API [GET] Rooms/{roomId}", "data: " + response.body());
                    bindingMessage(response.body());
                } else {
                    Log.d("call API [GET] Rooms/{roomId} failed", response.code() + ", " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<MessageDTO>> call, Throwable t) {
                Log.d("call API [GET] Rooms/{roomId} failed", t.getMessage());
            }
        });
    }
    public void connectToSignalR(){
        Log.d("Debug Point", "start connect to hub");
//        Intent intent = new Intent(this, MesageSignalrService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }
    }

    public void bindingRoomInformation(RoomDTO room){
        ((ImageView)RoomActivity.this.findViewById(R.id.imv_room_avatar)).setImageBitmap(UtilImage.getImageBase64(room.getReceiver().getAvartarImage().getContent()));
        ((TextView)RoomActivity.this.findViewById(R.id.tv_room_title)).setText(room.getRoom().getRoomTitle());
    }
    public void bindingMessage(List<MessageDTO> messages){
        MessageAdapter adapter = new MessageAdapter(RoomActivity.this, messages);
        RecyclerView rec = RoomActivity.this.findViewById(R.id.rec_messages_list);
        rec.setLayoutManager(new LinearLayoutManager(RoomActivity.this));
        rec.setAdapter(adapter);

        rec.scrollToPosition(messages.size() - 1);
    }

    public void receiveMessage(){
        loadMesage();
    }
    public void sendMessage(){
        IApi.myApi.sendMessages(MyApp.GetUserId(), room_id, ((EditText)findViewById(R.id.edt_message_input)).getText().toString()).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    Log.d("call API [POST] Message", "data: " + response.body());
                } else {
                    Log.d("call API [POST] Message failed", response.code() + ", " + response.message());
                }
                ((EditText) findViewById(R.id.edt_message_input)).setText("");
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d("call API [POST] Message failed", t.getMessage());
            }
        });
    }
}