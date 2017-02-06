package com.quincy.getwordintextview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.quincy.getwordintextview.R;
import com.quincy.getwordintextview.view.ClickableTextView;

public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
	private TextView testText = null;
    private TextView aaa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		testText = (TextView)findViewById(R.id.test);
		testText.setText(getResources().getString(R.string.text), BufferType.SPANNABLE);
//		getEachWord(testText);
//		testText.setMovementMethod(LinkMovementMethod.getInstance());
        ClickableTextView ctv =  (ClickableTextView)findViewById(R.id.aaa);
	}

	public void getEachWord(TextView textView){        
        Spannable spans = (Spannable)(textView.getText());
        Integer[] indices = getIndices(                
                textView.getText().toString().trim(), ' ');        
        int start = 0;        
        int end = 0;          
        // to cater last/only word loop will run equal to the length of indices.length

        for (int i = 0; i <= indices.length; i++) {
            if(i < indices.length) {
                Log.d(TAG, "indices[" + i + "]" + indices[i]);
            }
            ClickableSpan clickSpan = getClickableSpan();                       
            // to cater last/only word            
            end = (i < indices.length ? indices[i] : spans.length());                        
            spans.setSpan(clickSpan, start, end,                    
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);                                    
            start = end + 1;        
        }        
        //改变选中文本的高亮颜色        
        textView.setHighlightColor(Color.BLUE);    
    }    
    private ClickableSpan getClickableSpan(){         
        return new ClickableSpan() {                
            @Override                
            public void onClick(View widget) {                    
                TextView tv = (TextView) widget;
                Log.d(TAG, "tv.getText() : " + tv.getText().toString());
                String s = tv                            
                        .getText()                            
                        .subSequence(tv.getSelectionStart(),                                    
                                tv.getSelectionEnd()).toString();                    
                Log.d(TAG, s + "/ start : " + tv.getSelectionStart() + "/ end : " + tv.getSelectionEnd());
            }                
            @Override                  
            public void updateDrawState(TextPaint ds) {                          
                ds.setColor(Color.BLACK);                        
                ds.setUnderlineText(false);                  
            }                                             
        };    
    }    
    
    public static Integer[] getIndices(String s, char c) {        
        int pos = s.indexOf(c, 0);        
        List<Integer> indices = new ArrayList<Integer>();        
        while (pos != -1) {            
            indices.add(pos);            
            pos = s.indexOf(c, pos + 1);        
        }        
        return (Integer[]) indices.toArray(new Integer[0]);    
    }

}
