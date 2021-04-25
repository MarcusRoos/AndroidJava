package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DialActivity extends AppCompatActivity {
    private DialpadButton button2, button3, button4, button5, button6, button7, button8,
    button9, buttonStar, buttonPound;


    private TextView clicksTextView; // Display number of clicks on each button
    private String string = "";

    List<DialpadButton> buttons = new ArrayList<DialpadButton>();
    // To count number of times each button changes color (is clicked)
    private int button1Clicks;
    private int button2Clicks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

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
        string += aString;
        clicksTextView.append(aString);
    }

}