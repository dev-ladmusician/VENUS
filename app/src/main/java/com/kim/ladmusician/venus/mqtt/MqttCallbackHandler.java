package com.kim.ladmusician.venus.mqtt;

import android.content.Context;

import com.kim.ladmusician.venus.util.LogUtil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by ladmusician on 4/21/16.
 */
public class MqttCallbackHandler implements MqttCallback {
    private final String TAG = "MQTT_CALLBACK_HADNLER";
    private Context mContext = null;

    public MqttCallbackHandler(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        LogUtil.print(TAG, "MQTT CONNECTION LOST");
        getMqttConfiguration().setMqttConnection(false);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        LogUtil.print(TAG, "MQTT MESSAGEARRIVED TOPIC : " + topic);
        LogUtil.print(TAG, "MQTT MESSAGEARRIVED MESSAGE : " + message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LogUtil.print(TAG, "MQTT DELIVERY COMPLETE");
    }

    private MqttConfiguration getMqttConfiguration() {
        return MqttConfiguration.getInstance(mContext);
    }
}
