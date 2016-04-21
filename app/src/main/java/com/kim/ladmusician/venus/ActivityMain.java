package com.kim.ladmusician.venus;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;

import com.kim.ladmusician.venus.util.LogUtil;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {
    private final String TAG = "ACTIVITY_MAIN";
    private SpeechRecognizer mRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(intent);
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
        }
        @Override
        public void onReadyForSpeech(Bundle params) {
            LogUtil.print(TAG, "READY FOR SPEECH");
            // TODO Auto-generated method stub
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
