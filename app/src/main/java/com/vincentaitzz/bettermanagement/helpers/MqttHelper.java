package com.vincentaitzz.bettermanagement.helpers;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.IMqttToken;

public class MqttHelper {
    private MqttAndroidClient mqttAndroidClient;

    public MqttHelper(Context context, String clientId) {
        String serverUri = "tcp://broker.emqx.io:1883"; // URI del broker EMQX
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
    }

    public void connect(String userName, String password) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);

        try {
            IMqttToken token = mqttAndroidClient.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // Conexión exitosa
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Conexión fallida
                    exception.printStackTrace();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message) {
        if (!mqttAndroidClient.isConnected()) {
            System.out.println("Error: No conectado al broker MQTT.");
            return;
        }

        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(2); // Calidad de servicio
            mqttAndroidClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic, MqttCallback callback) {
        try {
            mqttAndroidClient.setCallback(callback);
            mqttAndroidClient.subscribe(topic, 2); // Calidad de servicio
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (mqttAndroidClient.isConnected()) {
                mqttAndroidClient.disconnect();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return mqttAndroidClient.isConnected();
    }
}