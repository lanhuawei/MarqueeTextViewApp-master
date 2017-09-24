package com.example.administrator.marqueetextviewapp;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/11/28.
 */
public class MarqueeTextView extends LinearLayout {

    private Context mContext;
    private ViewFlipper viewFlipper;
    private View marqueeTextView;
    private ArrayList<String> textArrays;
    private MarqueeTextViewClickListener marqueeTextViewClickListener;

    private int mCurrPos;//当前的textView

    public MarqueeTextView(Context context) {
        super(context);
        mContext = context;
        initBasicView();
    }


    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initBasicView();
    }

    public void setTextArraysAndClickListener(ArrayList<String> textArrays, MarqueeTextViewClickListener marqueeTextViewClickListener) {
        this.textArrays = textArrays;
        this.marqueeTextViewClickListener = marqueeTextViewClickListener;
//        initMarqueeTextView(textArrays, marqueeTextViewClickListener);
    }

    public void initBasicView() {
        marqueeTextView = LayoutInflater.from(mContext).inflate(R.layout.marquee_textview_layout, null);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(marqueeTextView, layoutParams);
        viewFlipper = (ViewFlipper) marqueeTextView.findViewById(R.id.viewFlipper);
//        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
//        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
//        viewFlipper.startFlipping();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        moveNext();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 3000);
    }

    private void moveNext() {
        initMarqueeTextView(textArrays, this.mCurrPos, this.mCurrPos + 1);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
        viewFlipper.showNext();
    }

    public void initMarqueeTextView(ArrayList<String> textArrays,int current, int next) {
        if (textArrays.size() == 0) {
            return;
        }
        viewFlipper.removeAllViews();

        TextView textView = new TextView(mContext);
        if ((current < next) && (next > (textArrays.size() - 1))) {
            next = 0;
        } else if ((current > next) && (next < 0)) {
            next = textArrays.size() - 1;
        }
        textView.setText(textArrays.get(next));
        textView.setOnClickListener(marqueeTextViewClickListener);
        if (viewFlipper.getChildCount() > 1) {
            viewFlipper.removeViewAt(0);
        }
        viewFlipper.addView(textView, viewFlipper.getChildCount());
        mCurrPos = next;
    }



//    public void initMarqueeTextView(ArrayList<String> textArrays, MarqueeTextViewClickListener marqueeTextViewClickListener) {
//        if (textArrays.size() == 0) {
//            return;
//        }
//
//        int i = 0;
//        viewFlipper.removeAllViews();
//        while (i < textArrays.size()) {
//            TextView textView = new TextView(mContext);
//            textView.setText(textArrays.get(i));
//            textView.setOnClickListener(marqueeTextViewClickListener);
//            LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//            viewFlipper.addView(textView, lp);
//            i++;
//        }
//    }


    public int getmCurrPos() {
        return mCurrPos;
    }

    public void releaseResources() {
        if (marqueeTextView != null) {
            if (viewFlipper != null) {
                viewFlipper.stopFlipping();
                viewFlipper.removeAllViews();
                viewFlipper = null;
            }
            marqueeTextView = null;
        }
    }


}