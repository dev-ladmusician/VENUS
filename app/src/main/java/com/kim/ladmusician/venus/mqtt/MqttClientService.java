package com.kim.ladmusician.venus.mqtt;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kim.ladmusician.venus.util.LogUtil;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/**
 * Created by ladmusician on 4/21/16.
 */
public class MqttClientService extends Service {
    private final String TAG = "SERVICE_MQTT";

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public MqttClientService getService() {
            return MqttClientService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private MqttConfiguration getMqttConfiguration() {
        return MqttConfiguration.getInstance(getApplicationContext());
    }

    /**
     * broker에 연결시도
     * MqttConfigure에 있는 client와 connectionOption가지고 연결
     * connectionCallback 연결하기
     */
    public void connectToBroker() {
        LogUtil.print(TAG, "START CONNECT TO BROKER");
        MqttAndroidClient client = getMqttConfiguration().getMqttAndroidClient();
        MqttConnectOptions options = getMqttConfiguration().createConnectionOptions();
        client.setCallback(new MqttCallbackHandler(getApplicationContext()));

        final MqttActionCallback connectionCallback =
                new MqttActionCallback(
                        getApplicationContext(),
                        MqttEnum.CONNECT
                );

        try {
            client.connect(options, null, connectionCallback);
        } catch (MqttSecurityException e) {
            getMqttConfiguration().setMqttConnection(false);
            e.printStackTrace();
            LogUtil.print(TAG, "MqttSecurityException - Failed to connect to broker: " + e.getMessage());
        } catch (MqttException e) {
            getMqttConfiguration().setMqttConnection(false);
            e.printStackTrace();
            LogUtil.print(TAG, "MqttException - Failed to connect to broker: " + e.getMessage());
        }
    }
}
