package com.corenet.yohady.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.corenet.yohady.R;
public class CustomTextView extends AppCompatTextView {


    public CustomTextView(Context context) {
        super(context);

    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttr(context,attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttr(context,attrs);
    }

    private void initTextView(Context context,String fontName){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),fontName);
        this.setTypeface(typeface);
    }

    private void readAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomTextView);

        // Read the title and set it if any
        String fontName = a.getString(R.styleable.CustomTextView_font_name) ;
        if (fontName != null) {
            initTextView(context,fontName);
        }

        a.recycle();
    }
}
