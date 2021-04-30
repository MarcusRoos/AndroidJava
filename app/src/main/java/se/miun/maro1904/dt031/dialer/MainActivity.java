package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;


public class MainActivity extends AppCompatActivity {
    static final String stateDialog = "dialogState";
    boolean aboutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean loadFiles = false;
        try {
            loadFiles = Util.defaultVoiceExist(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!loadFiles){
            Util.copyDefaultVoiceToInternalStorage(this);
        }
        setContentView(R.layout.activity_main);
    }


    public void dialButton(View view) {
        Intent myIntent = new Intent(MainActivity.this, DialActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void calllistButton(View view) {
        Intent myIntent = new Intent(MainActivity.this, CallListActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void settingsButton(View view) {
        Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void mapsButton(View view) {
        Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void downloadButton(View view) {
        Intent myIntent = new Intent(MainActivity.this, DownloadActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void aboutButton(View view){
        if (!aboutDialog) {
            aboutDialog = true;
            String aboutMessage = "This app is supposed to mimic the keypad on a phone. " +
                    "The app will consist of activities to: \n\n" + getString(R.string.numbersToDial)
                    + '\n' + getString(R.string.previouslyDialed) + '\n'
                    + getString(R.string.changeSettings)
                    + '\n' + getString(R.string.showOnMap);
            AlertDialog.Builder aboutBuilder = new AlertDialog.Builder(this);
            aboutBuilder.setTitle("About");
            aboutBuilder.setMessage(aboutMessage);
            aboutBuilder.setCancelable(true);
            aboutBuilder.setNeutralButton(
                    "OK",
                    (dialog, id) -> dialog.cancel());

            AlertDialog about = aboutBuilder.create();
            about.show();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You've already viewed the about page.",
                    Toast.LENGTH_SHORT);

            toast.show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(stateDialog, aboutDialog);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        aboutDialog = savedInstanceState.getBoolean(stateDialog);
    }

}