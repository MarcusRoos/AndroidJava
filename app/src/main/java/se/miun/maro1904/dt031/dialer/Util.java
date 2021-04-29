package se.miun.maro1904.dt031.dialer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final String TAG = "Util";

    /**
     * The directory in which all voices for the Dialer app are stored (in the app-specific
     * internal storage location).
     */
    public static final String VOICE_DIR = "voices";

    /**
     * The default voice to be used by the Dialer app.
     */
    public static final String DEFAULT_VOICE = "mamacita_us";

    /**
     * The file extension for the default voice files used by the Dialer app.
     */
    public static final String DEFAULT_VOICE_EXTENSION = "mp3";

    /**
     * The resource ids for the default voice (in res/raw).
     */
    public static final int[] DEFAULT_VOICE_RESOURCE_IDS = {
            R.raw.zero, R.raw.one, R.raw.two, R.raw.three,
            R.raw.four, R.raw.five, R.raw.six, R.raw.seven,
            R.raw.eight, R.raw.nine, R.raw.star, R.raw.pound
    };

    /**
     * The file names for each sound in a voice, mapped to its corresponding button title.
     */
    public static final Map<String, String> DEFAULT_VOICE_FILE_NAMES= new HashMap<>();

    static {
        DEFAULT_VOICE_FILE_NAMES.put("0", "zero.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("1", "one.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("2", "two.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("3", "three.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("4", "four.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("5", "five.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("6", "six.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("7", "seven.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("8", "eight.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("9", "nine.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("*", "star.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("#", "pound.mp3");
    }

    /**
     * Returns the absolute path to the directory on the filesystem where app-specific files
     * are stored.
     * @param context an application context
     * @return The path of the directory holding application files.
     */
    public static File getInternalStorageDir(Context context) {
        return context.getFilesDir();
    }

    /**
     * Returns the absolute path to the directory containing the voice files for the
     * given voice name.
     * @param context an application context
     * @param voiceName the name of the voice to get the directory for
     * @return The path of the directory for holding the voice given by <code>voiceName</code>.
     */
    public static File getVoiceDir(Context context, String voiceName) {
        if (voiceName == null || voiceName.length() < 1) {
            voiceName = DEFAULT_VOICE;
        }

        return new File(getInternalStorageDir(context), VOICE_DIR + File.separator + voiceName);
    }

    /**
     * Returns the absolute path to the directory containing the default voice files.
     * @param context an application context
     * @return The path of the directory holding the default voice.
     */
    public static File getDefaultVoiceDir(Context context) {
        return getVoiceDir(context, DEFAULT_VOICE);
    }

    public static File getAbsoluteVoice(Context context) {
        return getVoiceDir(context, DEFAULT_VOICE + DEFAULT_VOICE_EXTENSION);
    }

    /**
     * Copies each resource in <code>DEFAULT_VOICE_RESOURCE_IDS</code> to the directory returned
     * by <code>getDefaultVoiceDir</code>.
     * @param context an application context
     * @return true if all files are copied, or false if an error occurs.
     */
    public static boolean copyDefaultVoiceToInternalStorage(Context context) {
        // Complete path to the dir of the default voice
        File voiceDir = getDefaultVoiceDir(context);

        if (!voiceDir.exists()) {
            if (!voiceDir.mkdirs()) {
                Log.e(TAG, "Could not create dir: " + voiceDir);
                return false;
            }
        }

        for (int resourceId: DEFAULT_VOICE_RESOURCE_IDS) {
            String filename = context.getResources().getResourceEntryName(resourceId) + "." + DEFAULT_VOICE_EXTENSION; // ex. "one.mp3"

            try (InputStream in = context.getResources().openRawResource(resourceId);
                    OutputStream out = new FileOutputStream(new File(voiceDir, filename))) {
                // Copy from in to out using a byte buffer
                byte[] buffer = new byte[2048];

                int bytesRead;
                while ((bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }

            } catch (IOException e) {
                Log.e(TAG, "Error copying file: " + e.getMessage());
                return false;
            }
        }

        return true; // all files have been copied
    }

    /**
     * Check if the default voice files from res/raw already exists in the directory returned
     * by <code>getDefaultVoiceDir</code>.
     * @param context an application context
     * @return true if the default voice exists in internal app-specific storage, false if not.
     */
    public static boolean defaultVoiceExist(Context context) {
        boolean existsOrNot = true;
        File file = new File(context.getFilesDir().getAbsolutePath() + "/" + VOICE_DIR + "/" + DEFAULT_VOICE + "/");
        for (String test : DEFAULT_VOICE_FILE_NAMES.values()){
            File tester = new File(file + "/" + test);
            if(tester.exists()){
                existsOrNot = true;
            }
            else{
                existsOrNot = false;
                break;
            }
        }
        return existsOrNot;
    }
}
