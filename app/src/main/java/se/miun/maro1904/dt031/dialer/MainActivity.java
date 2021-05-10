package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    static final String stateDialog = "dialogState";
    boolean aboutDialog;
    public static CallHistoryDB DATABASE;
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
        DATABASE = Room.databaseBuilder(getApplicationContext(), CallHistoryDB.class, "callHistoryDB")
                .build();

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
        Intent intent = new Intent(this, DownloadActivity.class);
        intent.putExtra("url", getResources().getString(R.string.voicesLink));
        intent.putExtra("destination", getResources().getString(R.string.extractDirectory));
        startActivity(intent);
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