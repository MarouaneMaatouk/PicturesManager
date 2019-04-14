package com.example.android.zoomTableau;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TTS implements TextToSpeech.OnInitListener {
    private static TTS tts;
    private static TextToSpeech mTTs;
    static boolean mTTsLoaded = false;
    private Context mContext;

    private String read;
    private TTS(Context mContext) {
        this.mContext = mContext;
        mTTs = new TextToSpeech(mContext, this);
    }

    public static TTS getTTSinstance(Context mContext) {
        if(mTTsLoaded)
            return new TTS(mContext) ;
        else
            return tts;
    }

    public synchronized void readText(String text) {
        if(text == null) mTTs.speak("Aucune description disponible", TextToSpeech.QUEUE_FLUSH, null);
        else mTTs.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int i) {
        if(i == TextToSpeech.SUCCESS) {
            int result = mTTs.setLanguage(Locale.FRENCH);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported");
                mTTsLoaded = false;
            }
            else {
                mTTsLoaded = true;
                Log.d("mTTs", "Successfully loaded");
            }
        }
        else {
            Log.e("TTS" , "Init failed");
            mTTsLoaded = false;
        }
    }

}
