package com.vincentaitzz.bettermanagement.helpers;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttHelper {

    private static final String TAG = "MqttHandler";

    private MqttClient client;

    private MessageListener messageListener;

    public interface MessageListener {
        void onMessageReceived(String topic, String message);
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public void connect(String brokerUrl, String clientId) {
        try {
            MemoryPersistence persistence = new MemoryPersistence();

            client = new MqttClient(brokerUrl, clientId, persistence);

            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.e(TAG, "Conexión perdida: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    String receivedMessage = new String(message.getPayload());
                    Log.d(TAG, "Mensaje recibido en el tópico " + topic + ": " + receivedMessage);

                    if (messageListener != null) {
                        messageListener.onMessageReceived(topic, receivedMessage);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Entrega del mensaje completada.");
                }
            });

            client.connect(connectOptions);
            Log.i(TAG, "Conectado al broker: " + brokerUrl);

        } catch (MqttException e) {
            Log.e(TAG, "Error al conectar con el broker: " + e.getMessage(), e);
        }
    }

    public void disconnect() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                Log.i(TAG, "Desconectado del broker.");
            }
        } catch (MqttException e) {
            Log.e(TAG, "Error al desconectar: " + e.getMessage(), e);
        }
    }

    public void publish(String topic, String message) {
        try {
            if (client != null && client.isConnected()) {
                client.publish(topic, new MqttMessage(message.getBytes()));
                Log.i(TAG, "Mensaje publicado en el tópico " + topic + ": " + message);
            } else {
                Log.w(TAG, "El cliente no está conectado. No se puede publicar.");
            }
        } catch (MqttException e) {
            Log.e(TAG, "Error al publicar el mensaje: " + e.getMessage(), e);
        }
    }

    public void subscribe(String topic) {
        try {
            if (client != null && client.isConnected()) {
                client.subscribe(topic);
                Log.i(TAG, "Suscrito al tópico: " + topic);
            } else {
                Log.w(TAG, "El cliente no está conectado. No se puede suscribir.");
            }
        } catch (MqttException e) {
            Log.e(TAG, "Error al suscribirse al tópico: " + e.getMessage(), e);
        }
    }
}
