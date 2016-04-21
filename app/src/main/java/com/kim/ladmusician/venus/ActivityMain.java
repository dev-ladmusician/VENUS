package com.kim.ladmusician.venus;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kim.ladmusician.venus.mqtt.MqttActionCallback;
import com.kim.ladmusician.venus.mqtt.MqttCallbackHandler;
import com.kim.ladmusician.venus.mqtt.MqttClientService;
import com.kim.ladmusician.venus.mqtt.MqttConfiguration;
import com.kim.ladmusician.venus.mqtt.MqttEnum;
import com.kim.ladmusician.venus.util.LogUtil;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityMain extends AppCompatActivity {
    private final String TAG = "ACTIVITY_MAIN";
    private SpeechRecognizer mRecognizer;
    private boolean mServiceBound;
    private MqttClientService mService;
    private Context mContext;
    
    @Bind(R.id.main_container)
    CoordinatorLayout mContainer;
    @Bind(R.id.main_content)
    TextView mContent;
    @Bind(R.id.main_mic)
    ImageView mImgMic;

    private int SET_TURN_ON_ORDER = 0;
    private int SET_TURN_OFF_ORDER = 1;
    private int SET_DONE = 2;
    private int CURRENT_STEP = SET_TURN_ON_ORDER;

    private String TURN_ON_ORDER = "";
    private String TURN_OFF_ORDER = "";

    private MqttAndroidClient mMqttAndroidClient;
    private final String HOST = "tcp://52.79.137.129:1883";
    private final String TOPIC = "/bs/98018d/device/operation/req/btn1";
    private final  String DEVICE_ID = "goqual";

    @OnClick({ R.id.main_mic})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_mic:
                runMic();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        bindService();

        mMqttAndroidClient = new MqttAndroidClient(
                mContext,
                HOST,
                DEVICE_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mRecognizer != null) mRecognizer.destroy();
    }

    void runMic() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(intent);
    }

    private void bindService() {
        if (!mServiceBound) {
            mContext.bindService(new Intent(mContext, MqttClientService.class),
                    mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            LogUtil.print(TAG, "SUCCESS BIND SERVICE");
            MqttClientService.LocalBinder binder = (MqttClientService.LocalBinder) service;
            mService = binder.getService();
            mServiceBound = true;
            mService.connectToBroker();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            LogUtil.print(TAG, "DISCONNECT SERVICE");
            mServiceBound = false;
        }
    };

    void setTurnOnOrder(String order) {
        TURN_ON_ORDER = order;
        CURRENT_STEP = SET_TURN_OFF_ORDER;
        Snackbar snackbar = Snackbar
                .make(mContainer, order + "로 불을 켜세요!", Snackbar.LENGTH_LONG);
        snackbar.show();
        mContent.setText("불끄기 명령어를 등록해주세요!");
    }
    void setTurnOffOrder(String order) {
        TURN_OFF_ORDER = order;
        CURRENT_STEP = SET_DONE;
        Snackbar snackbar = Snackbar
                .make(mContainer, order + "로 불을 끄세요!", Snackbar.LENGTH_LONG);
        snackbar.show();
        mImgMic.setImageResource(R.drawable.ic_mic);
        mContent.setText("이제 작동해보세요!");
    }

    private MqttConfiguration getMqttConfiguration() {
        return MqttConfiguration.getInstance(getApplicationContext());
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onRmsChanged(float rmsdB) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onResults(Bundle results) {
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            LogUtil.print(TAG, rs[0]);

            if (CURRENT_STEP == SET_TURN_ON_ORDER) {
                setTurnOnOrder(rs[0]);
            } else if (CURRENT_STEP == SET_TURN_OFF_ORDER) {
                setTurnOffOrder(rs[0]);
            } else {
                MqttAndroidClient client = getMqttConfiguration().getMqttAndroidClient();
                MqttConnectOptions options = getMqttConfiguration().createConnectionOptions();
                client.setCallback(new MqttCallbackHandler(getApplicationContext()));

                final MqttActionCallback connectionCallback =
                        new MqttActionCallback(
                                getApplicationContext(),
                                MqttEnum.CONNECT
                        );

                if (rs[0].equals(TURN_ON_ORDER)) {
                    try {
                        client.publish(TOPIC, "0".getBytes(), 0, false, null, connectionCallback);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                } else if (rs[0].equals(TURN_OFF_ORDER)) {
                    try {
                        client.publish(TOPIC, "1".getBytes(), 0, false, null, connectionCallback);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(mContainer, "올바르지 않은 명령어 입니다!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }
        @Override
        public void onReadyForSpeech(Bundle params) {
            LogUtil.print(TAG, "READY FOR SPEECH");
            Snackbar snackbar = Snackbar
                    .make(mContainer, "준비되었습니다. 말씀하세요!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        @Override
        public void onPartialResults(Bundle partialResults) {
            LogUtil.print(TAG, "PARTIAL RESULT");
        }
        @Override
        public void onEvent(int eventType, Bundle params) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onError(int error) {
            LogUtil.print(TAG, "ERROR");
        }
        @Override
        public void onEndOfSpeech() {
            LogUtil.print(TAG, "END OF SPEECH");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onBeginningOfSpeech() {
            LogUtil.print(TAG, "BEGINNING OF SPEECH");
        }
    };
}
