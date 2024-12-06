package com.vincentaitzz.bettermanagement.controllers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.helpers.ChatAdapter;
import com.vincentaitzz.bettermanagement.helpers.FirebaseManager;
import com.vincentaitzz.bettermanagement.helpers.MqttHelper;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private EditText txtMessage;
    private Button btnSend;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<String> messages = new ArrayList<>();

    private MqttHelper mqttHelper;
    private FirebaseManager auth;
    private String _ID;
    private static final String _BROKER = "tcp://broker.emqx.io:1883";
    private static final String _TOPIC_SUB = "aitzz/chat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chat);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = new FirebaseManager();

        _ID = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recyclerView);
        chatAdapter = new ChatAdapter(messages);
        btnSend = findViewById(R.id.btnSend);
        txtMessage = findViewById(R.id.txtMsg);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        mqttHelper = new MqttHelper();

        mqttHelper.setMessageListener(new MqttHelper.MessageListener() {
            @Override
            public void onMessageReceived(String topic, String message) {
                runOnUiThread(() -> {
                    messages.add("Tópico: [" + topic + "] : " + message);
                    chatAdapter.notifyItemInserted(messages.size() - 1);
                    recyclerView.scrollToPosition(messages.size() - 1);
                });
            }
        });

        mqttHelper.connect(_BROKER,_ID);
        mqttHelper.subscribe(_TOPIC_SUB);

        btnSend.setOnClickListener(v -> {
            String data = txtMessage.getText().toString();
            if(!data.isEmpty()){
                mqttHelper.publish(_TOPIC_SUB,data);
                messages.add("I: "+data);
                chatAdapter.notifyItemInserted(messages.size()-1);
                recyclerView.scrollToPosition(messages.size()-1);
                txtMessage.setText("");

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mqttHelper.disconnect();
    }
}