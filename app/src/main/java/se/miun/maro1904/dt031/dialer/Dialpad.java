package se.miun.maro1904.dt031.dialer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;


public class Dialpad extends ConstraintLayout {
    public Dialpad(Context context) {
        super(context);
        init(context);
    }

    public Dialpad(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Dialpad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialpad, this, true);
    }
}