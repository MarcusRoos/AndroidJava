package se.miun.maro1904.dt031.dialer;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;


class DialpadButton extends LinearLayout {
    SoundPlayer soundPlayer = SoundPlayer.getInstance(getContext());
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

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.DialpadButton, 0, 0);
        aTitle = typedArray.getString(R.styleable.DialpadButton_title);
        aMessage = typedArray.getString(R.styleable.DialpadButton_message);
        typedArray.recycle();
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT, 80);

        params.gravity = Gravity.CENTER;

        title = new TextView(context);
        title.setLayoutParams(params);

        message = new TextView(context);
        message.setLayoutParams(params);

        title.setTextColor(Color.RED);
        message.setTextColor(Color.BLUE);

        addView(title);
        addView(message);

        this.setOnClickListener(view -> myAnimate());
    }


    public String getTitle() {
        return aTitle;
    }


    public String getMessage() {
        return aMessage;
    }

    public void setTitle(String tmp) {
        if (tmp.length() > 1)
        {
            aTitle = tmp.substring(0, 1);
        }
        else
        {
            aTitle = tmp;
        }
        requestLayout();
    }

    public void setMessage(String tmp) {
        if (tmp.length() > 4)
        {
            aMessage = tmp.substring(0, 4);
        }
        else
        {
            aMessage = tmp;
        }
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

    private void myAnimate(){
        Integer colorFrom = (Color.RED);
        Integer colorFrom2 = (Color.BLUE);
        Integer colorTo = (Color.WHITE);
        Integer colorTo2 = (Color.WHITE);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        ValueAnimator colorAnimation2 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom2, colorTo2);
        colorAnimation.addUpdateListener(animator -> title.setTextColor((Integer)animator.getAnimatedValue()));
        colorAnimation2.addUpdateListener(animator -> message.setTextColor((Integer)animator.getAnimatedValue()));
        colorAnimation.start();
        colorAnimation2.start();
    }

    private void myAnimateUp(){
        Integer colorFrom = (Color.WHITE);
        Integer colorTo = (Color.RED);
        Integer colorTo2 = (Color.BLUE);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        ValueAnimator colorAnimation2 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo2);
        colorAnimation.addUpdateListener(animator -> title.setTextColor((Integer)animator.getAnimatedValue()));
        colorAnimation2.addUpdateListener(animator -> message.setTextColor((Integer)animator.getAnimatedValue()));
        colorAnimation.start();
        colorAnimation2.start();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                soundPlayer.playSound(this);
                myAnimate();
                break;
            case MotionEvent.ACTION_UP:
                myAnimateUp();
                break;
        }
        return true;
    }
}
