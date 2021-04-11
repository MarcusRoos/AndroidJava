package se.miun.maro1904.dt031.dialer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;


class DialpadButton extends LinearLayout {
    String aTitle;
    String aMessage;
    TextView title;
    TextView message;
    Paint paint;
    Canvas canvas;
    public DialpadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setWillNotDraw(false);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.DialpadButton, 0, 0);
        aTitle = typedArray.getString(R.styleable.DialpadButton_title);
        aMessage = typedArray.getString(R.styleable.DialpadButton_message);
        typedArray.recycle();
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT, 80);

        params.gravity = Gravity.CENTER;


        paint = new Paint();
        paint.setARGB(50, 200, 200, 20);
        paint.setStyle(Paint.Style.FILL);

        title = new TextView(context);
        title.setLayoutParams(params);

        message = new TextView(context);
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
        title.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        message.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        message.setText(aMessage);
    }

}
