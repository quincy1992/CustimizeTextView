package com.quincy.getwordintextview.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */
public class ClickableTextView extends TextView {

    private final String TAG = "ClickableTextView";

    public ClickableTextView(Context context){
        super(context);
        Log.d(TAG, "ClickableTextView1");
//        splitWord();
//        this.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public ClickableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.d(TAG, "ClickableTextView2");
//        splitWord();
//        this.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "ClickableTextView3");
        this.setText(this.getText(), BufferType.SPANNABLE);
        splitWord();
        this.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void splitWord(){
        SpannableString spans = new SpannableString(this.getText());
        Integer[] indices = getIndices(
                this.getText().toString().trim(), ' ');
        int start = 0;
        int end = 0;
        // to cater last/only word loop will run equal to the length of indices.length

        for (int i = 0; i <= indices.length; i++) {
            if(i < indices.length) {
                Log.d(TAG, "indices[" + i + "]" + indices[i]);
            }
            ClickableSpan clickSpan = new CustomizeClickableSpan();
            // to cater last/only word
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        this.setText(spans);
        //改变选中文本的高亮颜色
//        this.setHighlightColor(Color.BLUE);
    }

    private class CustomizeClickableSpan extends  ClickableSpan{

        private TextPaint mTextPaint = null;

        public CustomizeClickableSpan(){
            Log.d(TAG, "CustomizeClickableSpan()");
        }

        public CustomizeClickableSpan(TextPaint tp){
            mTextPaint = tp;
        }

        public void setTextPaint(TextPaint tp){
            mTextPaint = tp;
        }

        @Override
        public void onClick(View widget) {
            TextView tv = (TextView) widget;
            Log.d(TAG, "tv.getText() : " + tv.getText().toString());
            String s = tv
                    .getText()
                    .toString()
                    .subSequence(tv.getSelectionStart(),
                            tv.getSelectionEnd()).toString();

        }
        @Override
        public void updateDrawState(TextPaint ds) {
            if(mTextPaint != null){
                ds.set(mTextPaint);
            } else {
//                ds.setColor(Color.RED);
                ds.setUnderlineText(false);
            }
        }
    }

    private Integer[] getIndices(String s, char c) {
        int pos = s.indexOf(c, 0);
        List<Integer> indices = new ArrayList<Integer>();
        while (pos != -1) {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return (Integer[]) indices.toArray(new Integer[0]);
    }

}
