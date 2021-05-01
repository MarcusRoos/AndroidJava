package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.webkit.WebView;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView myWebView = new WebView(this);
        setContentView(myWebView);
        myWebView.loadUrl("https://dt031g.programvaruteknik.nu/dialer/voices/");
    }
}