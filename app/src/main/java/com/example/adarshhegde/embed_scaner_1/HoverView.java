package com.example.adarshhegde.embed_scaner_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class HoverView extends View {
	private Paint mPaint;
	private int mLeft, mTop, mRight, mBottom;
	
	public HoverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
	}
	
	public void update(int width, int height) {
		int centerX = width / 2;
		int centerY = height / 2;
        if ((centerX - 200) < 0 || (centerY - 200) < 0 || (centerX + 200) > width || (centerY + 200) > 0)
        {
            //For low resolution mobiles.
            Log.i("Hoview","Low resolution mobile");
            mLeft = centerX - 100;
            mRight = centerX + 100;
            mTop = centerY - 100;
            mBottom = centerY + 100;

        }
        else {
            mLeft = centerX - 200;
            mRight = centerX + 200;
            mTop = centerY - 200;
            mBottom = centerY + 200;
        }
		invalidate();
	}
	
	public int getHoverLeft() {
		return mLeft;
	}
	
	public int getHoverTop() {
		return mTop;
	}
	
	public int getHoverAreaWidth() {
		return mRight - mLeft;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

            canvas.drawRect(mLeft, mTop, mRight, mBottom, mPaint);
	}
}
