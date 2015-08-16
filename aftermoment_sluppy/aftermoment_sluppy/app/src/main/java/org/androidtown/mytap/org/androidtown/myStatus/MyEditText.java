package org.androidtown.mytap.org.androidtown.myStatus;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by constant on 15. 7. 14..
 */
public class MyEditText extends EditText {

    private OnTextLengthListener onTextLengthListener=null;
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setOnTextLengthListener(OnTextLengthListener listener)
    {
        onTextLengthListener=listener;

    }
    public interface OnTextLengthListener{
        public abstract void onTextLength(int length);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(onTextLengthListener!=null)
            onTextLengthListener.onTextLength(text.length());
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }
}
