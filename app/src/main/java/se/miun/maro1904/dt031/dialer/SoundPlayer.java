package se.miun.maro1904.dt031.dialer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Handler;

public class SoundPlayer {

    private static SoundPlayer instance;
    private SoundPool soundPool;
    int ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, POUND, STAR;

    @SuppressLint("UseSparseArrays")
    private SoundPlayer(Context aContext) {
        soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build())
                .build();

        loadSounds(aContext);
    }

    public static SoundPlayer getInstance(Context aContext){
        if(instance == null){
            instance = new SoundPlayer(aContext);
        }
        return instance;
    }


    private void loadSounds(Context aContext){
        ZERO = soundPool.load(aContext, R.raw.zero, 1);
        ONE = soundPool.load(aContext, R.raw.one, 1);
        TWO = soundPool.load(aContext, R.raw.two, 1);
        THREE = soundPool.load(aContext, R.raw.three, 1);
        FOUR = soundPool.load(aContext, R.raw.four, 1);
        FIVE = soundPool.load(aContext, R.raw.five, 1);
        SIX = soundPool.load(aContext, R.raw.six, 1);
        SEVEN = soundPool.load(aContext, R.raw.seven, 1);
        EIGHT = soundPool.load(aContext, R.raw.eight, 1);
        NINE = soundPool.load(aContext, R.raw.nine, 1);
        STAR = soundPool.load(aContext, R.raw.star, 1);
        POUND = soundPool.load(aContext, R.raw.pound, 1);
    }


    public void playSound(DialpadButton dialpadButton)
    {
        String button = "";
        button = dialpadButton.getTitle();
        int id=0;
        switch (button){
            case "0":
                id = ZERO;
                break;
            case "1":
                id = ONE;
                break;
            case "2":
                id = TWO;
                break;
            case "3":
                id = THREE;
                break;
            case "4":
                id = FOUR;
                break;
            case "5":
                id = FIVE;
                break;
            case "6":
                id = SIX;
                break;
            case "7":
                id = SEVEN;
                break;
            case "8":
                id = EIGHT;
                break;
            case "9":
                id = NINE;
                break;
            case "\u2733":
                id = STAR;
                break;
            case "#":
                id = POUND;
                break;
        }
        soundPool.play(id, 1f, 1f, 1, 0, 1f);
    }

    public void destroy(){
        soundPool.release();
        soundPool = null;
        instance = null;
    }
}
