package com.random.simplenotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by ziakhan on 18/09/16.
 */
public class LinedEditText extends EditText {

    private static Paint linePaint;

    static {
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
    }

   public LinedEditText(Context context)
    {

        super(context);

    }

   public LinedEditText(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect bounds = new Rect();
        int firstLineY = getLineBounds(0, bounds);

        /*int firstLineY=0;

        for(int i =0;i<getLineCount();i++)
        {
            firstLineY = (i + 1) * getLineHeight();
        }*/

        int lineHeight = getLineHeight();
        int totalLines = Math.max(getLineCount(), getHeight() / lineHeight);


        for (int i = 0; i < totalLines; i++) {
            int lineY = firstLineY + i * lineHeight;
            canvas.drawLine(bounds.left, lineY, bounds.right, lineY, linePaint);
        }

        super.onDraw(canvas);
    }
}
