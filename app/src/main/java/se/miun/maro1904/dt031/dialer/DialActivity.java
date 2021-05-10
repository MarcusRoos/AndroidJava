package se.miun.maro1904.dt031.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DialActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    SoundPlayer soundPlayer;
    private Button delButton, callButton;
    private TextView clicksTextView;
    Intent myIntent;
    List<DialpadButton> buttons = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    SharedPreferences prefStatus;
    boolean switchPrefValue;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settingsbutton) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SoundPlayer.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        myIntent = new Intent(Intent.ACTION_DIAL);
        delButton = findViewById(R.id.DelButton);
        callButton = findViewById(R.id.CallButton);
        buttons.add(findViewById(R.id.dialpadbutton0));
        buttons.add(findViewById(R.id.dialpadbutton1));
        buttons.add(findViewById(R.id.dialpadbutton2));
        buttons.add(findViewById(R.id.dialpadbutton3));
        buttons.add(findViewById(R.id.dialpadbutton4));
        buttons.add(findViewById(R.id.dialpadbutton5));
        buttons.add(findViewById(R.id.dialpadbutton6));
        buttons.add(findViewById(R.id.dialpadbutton7));
        buttons.add(findViewById(R.id.dialpadbutton8));
        buttons.add(findViewById(R.id.dialpadbutton9));
        buttons.add(findViewById(R.id.dialpadbuttonstar));
        buttons.add(findViewById(R.id.dialpadbuttonpound));
        clicksTextView = findViewById(R.id.numberArea);
        soundPlayer = SoundPlayer.getInstance(this);
        // Fetch boolean value of save number settings
        prefStatus = PreferenceManager.getDefaultSharedPreferences(this);
        switchPrefValue = prefStatus.getBoolean("saveNumber", false);

        for (int i=0; i<buttons.size(); i++) {
            buttons.get(i).setOnClickedListener(me -> {
                String aString = me.getTitle();
                updateClicks(aString);
            });
        }
    }


    private void updateClicks(String aString) {
        clicksTextView.append(aString);
    }

    @Override
    public void onClick(View v) {
        delButton.setOnClickListener(v1 -> {
            String deleteMe = clicksTextView.getText().toString();
            if (deleteMe.length() > 0) {
                clicksTextView.setText(deleteMe.substring(0, deleteMe.length() - 1));
            }
        });
        delButton.setOnLongClickListener(v12 -> {
            clicksTextView.setText("");
            return true;
        });

        callButton.setOnClickListener(callClicker -> {
            if (switchPrefValue){
                String a = clicksTextView.getText().toString();
                String s1 = sharedPreferences.getString("name", "");
                a += "\n" + s1;
                myEdit.putString("name", a);
                myEdit.apply();
            }
            if (isCallPhonePermissionGranted() && isLocationPhonePermissionGranted()) {
                calling();
            } else if (!isCallPhonePermissionGranted()){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
            else if (!isLocationPhonePermissionGranted()) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else if (!isCallPhonePermissionGranted() && isLocationPhonePermissionGranted()){
                calling();
            }
        });
    }



    private boolean isCallPhonePermissionGranted() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isLocationPhonePermissionGranted() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), R.string.granted, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.denied, Toast.LENGTH_SHORT).show();
            }
            calling();
        }
    }

    public void calling() {
        Context context = this;
        new Thread(() -> {

            CallHistory callHistory = new CallHistory();
            Date date = Calendar.getInstance().getTime();
            callHistory.setNumber(clicksTextView.getText().toString());
            callHistory.setDate(date.toString());
            callHistory.setLng("Null");
            callHistory.setLat("Null");

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (locationManager != null) {
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    callHistory.setLat(String.valueOf(location.getLatitude()));
                    callHistory.setLng(String.valueOf(location.getLongitude()));
                }
            }
            MainActivity.DATABASE.historyDao().insert(callHistory);
        }) .start();


        if (isCallPhonePermissionGranted()) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" +
                    clicksTextView.getText()));
            this.startActivity(intent);
        }
        else{
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" +
                    clicksTextView.getText()));
            this.startActivity(intent);
        }
    }


    @Override
    public boolean onLongClick(View v) {
        // Required stub
    return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        soundPlayer.destroy();
    }

    public void downloadButton(MenuItem item) {
        Intent intent = new Intent(this, DownloadActivity.class);
        intent.putExtra("url", getResources().getString(R.string.voicesLink));
        intent.putExtra("destination", getResources().getString(R.string.extractDirectory));
        this.startActivity(intent);
    }

    public void tester(String passMe){
        // Don't want to extract this string, solely for testing purposes, will delete at last assignment
        Toast toast = Toast.makeText(this,passMe,Toast.LENGTH_SHORT);
        toast.show();
    }

}
