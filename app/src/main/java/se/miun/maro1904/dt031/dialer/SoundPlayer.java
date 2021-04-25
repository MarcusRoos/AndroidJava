package se.miun.maro1904.dt031.dialer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
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
        String file = aContext.getFilesDir().getPath() + "/voices/mamacita_us/";
        ZERO = soundPool.load(file + "zero.mp3", 1);
        ONE = soundPool.load(file + "one.mp3", 1);
        TWO = soundPool.load(file + "two.mp3", 1);
        THREE = soundPool.load(file + "three.mp3", 1);
        FOUR = soundPool.load(file + "four.mp3", 1);
        FIVE = soundPool.load(file + "five.mp3", 1);
        SIX = soundPool.load(file + "six.mp3", 1);
        SEVEN = soundPool.load(file + "seven.mp3", 1);
        EIGHT = soundPool.load(file + "eight.mp3", 1);
        NINE = soundPool.load(file + "nine.mp3", 1);
        STAR = soundPool.load(file + "star.mp3", 1);
        POUND = soundPool.load(file + "pound.mp3", 1);
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
