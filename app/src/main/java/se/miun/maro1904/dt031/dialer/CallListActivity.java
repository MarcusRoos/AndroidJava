package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class CallListActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        textView = findViewById(R.id.CalllistActivity);
        String value = sh.getString("name", null);
        if (value == null){
            textView.setText("No numbers exist");
        }
        else {
            String a = textView.getText().toString();
            String s1 = sh.getString("name", "");
            a += "\n" + s1;
            textView.append(a);
        }
    }


}