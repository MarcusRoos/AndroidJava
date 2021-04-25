package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class DialActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private Button delButton, callButton;


    private TextView clicksTextView;

    List<DialpadButton> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

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

        callButton.setOnClickListener(v13 -> clicksTextView.setText("Call"));
    }


    @Override
    public boolean onLongClick(View v) {
        // Required stub
    return false;
    }


}