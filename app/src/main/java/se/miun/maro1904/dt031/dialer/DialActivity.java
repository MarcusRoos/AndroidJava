package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class DialActivity extends AppCompatActivity {
    private DialpadButton button0, button1, button2, button3, button4, button5, button6, button7, button8,
    button9, buttonStar, buttonPound;

    private TextView clicksTextView; // Display number of clicks on each button
    private String string = "";

    // To count number of times each button changes color (is clicked)
    private int button1Clicks;
    private int button2Clicks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        button0 = findViewById(R.id.dialpadbutton0);
        button1 = findViewById(R.id.dialpadbutton1);
        button2 = findViewById(R.id.dialpadbutton2);
        button3 = findViewById(R.id.dialpadbutton3);
        button4 = findViewById(R.id.dialpadbutton4);
        button5 = findViewById(R.id.dialpadbutton5);
        button6 = findViewById(R.id.dialpadbutton6);
        button7 = findViewById(R.id.dialpadbutton7);
        button8 = findViewById(R.id.dialpadbutton8);
        button9 = findViewById(R.id.dialpadbutton9);
        buttonStar = findViewById(R.id.dialpadbuttonstar);
        buttonPound = findViewById(R.id.dialpadbuttonpound);
        clicksTextView = findViewById(R.id.numberArea);


        button1.setOnClickedListener(new DialpadButton.OnClickedListener() {
            @Override
            public void onClick(DialpadButton me) {
                String string = me.getTitle();
                updateClicks(string);
            }
        });
    }

    private void updateClicks(String aString) {
        string += aString;
        clicksTextView.append(string);
    }

}