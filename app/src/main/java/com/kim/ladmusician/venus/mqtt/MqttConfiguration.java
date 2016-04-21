package com.kim.ladmusician.venus.mqtt;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;


/**
 * Created by ladmusician on 3/17/16.
 */
public class MqttConfiguration {
    private Context mContext = null;
    private static MqttConfiguration instance = null;
    private boolean mMqttConnection = false;
    private MqttAndroidClient mMqttAndroidClient = null;
    private MqttConnectOptions mMqttConnectOptions = null;

    private final int QOS = 0;
    private final boolean RETAIN = false;
    private final String HOST = "tcp://52.79.137.129:1883";
    private final int CONNECTION_TIMEOUT = 1000;
    private final int KEEP_ALIVE_INTERVAL = 20;
    private final boolean CLEAN_SESSION = false;
    private final  String DEVICE_ID = "goqual";

    public static MqttConfiguration getInstance(Context ctx) {
        if (instance == null) instance = new MqttConfiguration(ctx);
        return instance;
    }

    public MqttConfiguration(Context mContext) {
        this.mContext = mContext;
    }

    public MqttConnectOptions createConnectionOptions() {
        mMqttConnectOptions = new MqttConnectOptions();
        mMqttConnectOptions.setCleanSession(CLEAN_SESSION);
        mMqttConnectOptions.setUserName(DEVICE_ID);
        mMqttConnectOptions.setPassword(DEVICE_ID.toCharArray());
        mMqttConnectOptions.setConnectionTimeout(CONNECTION_TIMEOUT);
        mMqttConnectOptions.setKeepAliveInterval(KEEP_ALIVE_INTERVAL);

        return mMqttConnectOptions;
    }

    /** getter & setter **/
    public boolean isMqttConnection() {
        return mMqttConnection;
    }
    public void setMqttConnection(boolean MqttConnection) {
        this.mMqttConnection = MqttConnection;
    }
    public int getQOS() {
        return QOS;
    }
    public boolean isRETAIN() {
        return RETAIN;
    }
    public String getHOST() {
        return HOST;
    }
    public int getCONNECTION_TIMEOUT() {
        return CONNECTION_TIMEOUT;
    }
    public int getKEEP_ALIVE_INTERVAL() {
        return KEEP_ALIVE_INTERVAL;
    }
    public boolean isCLEAN_SESSION() {
        return CLEAN_SESSION;
    }
    public MqttAndroidClient getMqttAndroidClient() {
        if (mMqttAndroidClient == null) {
            this.mMqttAndroidClient = new MqttAndroidClient(
                    mContext,
                    HOST,
                    DEVICE_ID);
        }

        return mMqttAndroidClient;
    }
    public void setMqttAndroidClient(MqttAndroidClient mMqttAndroidClient) {
        this.mMqttAndroidClient = mMqttAndroidClient;
    }


    public String makeSubTopic(String macAddr) {
        return "/bs/" + macAddr + "/phone/operation/#";
    }
}
