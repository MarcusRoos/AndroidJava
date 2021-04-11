package se.miun.maro1904.dt031.dialer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StyleableRes;

class DialpadButton extends LinearLayout {
    String aTitle;
    String aMessage;
    TextView title;
    TextView message;
    public DialpadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setWillNotDraw(false);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.dialpad_button, this);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.DialpadButton, 0, 0);
        aTitle = typedArray.getString(R.styleable.DialpadButton_title);
        aMessage = typedArray.getString(R.styleable.DialpadButton_message);
        typedArray.recycle();
        title = new TextView(context);
        message = new TextView(context);
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        title.setLayoutParams(params);
        message.setLayoutParams(params);
        addView(title);
        addView(message);
    }


    public String getTitle() {
        return aTitle;
    }


    public String getMessage() {
        return aMessage;
    }

    public void setTitle(String tmp) {
        aTitle = tmp;
        requestLayout();
    }

    public void setMessage(String tmp) {
        aMessage = tmp;
        requestLayout();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        title.setText(aTitle);
        message.setText(aMessage);
    }

}
