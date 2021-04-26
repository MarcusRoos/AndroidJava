package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DialActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private Button delButton, callButton;
    private TextView clicksTextView;
    Intent myIntent;
    List<DialpadButton> buttons = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        callButton.setOnClickListener(newColor -> {
            String phoneNumber = "tel:" + clicksTextView.getText().toString();
            String a = clicksTextView.getText().toString();
            String s1 = sharedPreferences.getString("name", "");
            a += "\n" + s1;
            myEdit.putString("name", a);
            myEdit.apply();
            if(phoneNumber.contains("#") || phoneNumber.contains("\u2733")){
                phoneNumber = phoneNumber.replace("#","%23");
                phoneNumber = phoneNumber.replace("\u2733","*");
            }


            Uri number = Uri.parse(phoneNumber);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        });

    }


    @Override
    public boolean onLongClick(View v) {
        // Required stub
    return false;
    }


}