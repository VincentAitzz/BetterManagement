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

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

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

        recyclerView = findViewById(R.id.recyclerView);
        chatAdapter = new ChatAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        String id = auth.getCurrentUser().getUid();

        mqttHelper = new MqttHelper(this, id);

        try {
            mqttHelper.connect("", "");

            mqttHelper.subscribe("vincentaitzz/chat", new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String msg = new String(message.getPayload());
                    runOnUiThread(() -> {
                        messages.add(msg);
                        chatAdapter.notifyItemInserted(messages.size() - 1);
                        recyclerView.scrollToPosition(messages.size() - 1);
                    });
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        txtMessage = findViewById(R.id.editTextMessage);
        btnSend = findViewById(R.id.buttonSend);

        btnSend.setOnClickListener(v -> {
            String message = txtMessage.getText().toString();
            if (!message.isEmpty()) {
                mqttHelper.publish("vincentaitzz/chat", message);
                messages.add(message);
                chatAdapter.notifyItemInserted(messages.size() - 1);
                recyclerView.scrollToPosition(messages.size() - 1);
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