package com.kim.ladmusician.venus.mqtt;

import android.content.Context;

import com.kim.ladmusician.venus.util.LogUtil;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * Created by ladmusician on 4/21/16.
 */
public class MqttActionCallback implements IMqttActionListener {
    private final String TAG = "MQTT_CONNECTION_LISTENER";
    private MqttEnum mAction;
    private Context mContext;

    public MqttActionCallback(Context mContext, MqttEnum mAction) {
        this.mAction = mAction;
        this.mContext = mContext;
    }

    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        switch (mAction) {
            case CONNECT :
                connect();
                break;
            case DISCONNECT :
                //disconnect();
                break;
            case SUBSCRIBE :
                subscribe();
                break;
            case PUBLISH :
                publish();
                break;
        }
    }

    private void connect() {
        getMqttConfiguration().setMqttConnection(true);
        LogUtil.print(TAG, "SUCCESS CLIENT CONNECTION");
    }

    private void publish() {
        //if (mSwitch != null) {
        //RealmManager.createSwitchDAO().updateSwitch(mSwitch);
        //}
    }

    private  void subscribe() {
        LogUtil.print(TAG, "SUCCESS TO SUB");
    }

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
        switch (mAction) {
            case CONNECT :
                connect(throwable);
                break;
            case DISCONNECT :
                //disconnect(throwable);
                break;
            case SUBSCRIBE :
                //subscribe(throwable);
                break;
            case PUBLISH :
                publish(throwable);
                break;
        }
    }

    private void connect(Throwable exception) {
        getMqttConfiguration().setMqttConnection(false);
//        BusProvider.getEventBus().post(
//                new MqttConnectionEvent(MqttEnum.CONNECT, Constant.RESULT_FAIL)
//        );
        exception.printStackTrace();
        LogUtil.print(TAG, "MQTT CLIENT CONNECTION FAIL : " + exception.getMessage());
    }
    private void publish(Throwable exception) {
        LogUtil.print(TAG, exception.getMessage());
        //Toast.simpleToast(mContext, mContext.getString(R.string.MQTT_PUB_FAIL));
    }

    private  void subscribe(Throwable exception) {
        LogUtil.print(TAG, "FAIL TO SUB : " + exception.getMessage());
    }

    public MqttEnum getMqttEnum() {
        return this.mAction;
    }
    public void setMqttEnum(MqttEnum action) {
        this.mAction = action;
    }
    private MqttConfiguration getMqttConfiguration() {
        return MqttConfiguration.getInstance(mContext);
    }
}
