package cn.tomoon.clockplugin.analog.widget;

import cn.tomoon.clockplugin.analog.AnalogClockActivity;
import cn.tomoon.clockplugin.analog.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class HandImageView extends View {
	/**
	 * author Kun Wang
	 * 2012-3-23 15:29:27
	 */
	private float mRotateDegrees = 0.0f;
	private Drawable handDrawable;
	private Drawable defaultHandDrawable;
	
	/**
	 */
	private int mBoundsLeft;
	private int mBoundsTop;
	private int mBoundsRight;
	private int mBoundsBottom;
	
	private static int centerX;
	private static int centerY;
	int DrawableWidth;
	int DrawableHeight;
	
	public int count=0;

	public HandImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		
		centerX = AnalogClockActivity.mRotateCenterX;
		centerY = AnalogClockActivity.mRotateCenterY;
		initail(context,attrs);
		
	}
	private void initail(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.handimageview);
		handDrawable = context.getResources().getDrawable(
				mTypedArray.getResourceId(R.styleable.handimageview_myhandsrc,
						R.drawable.ic_launcher));
		defaultHandDrawable = handDrawable;
		GetDrawableSize();
	}
	
	private void GetDrawableSize() {
		DrawableWidth = handDrawable.getMinimumWidth();
		DrawableHeight= handDrawable.getMinimumHeight();	
//		DrawableWidth = (int) (332);
//		DrawableHeight= (int) (332);	
		
		Log.d("tomoon", ("DrawableWidth="+DrawableWidth+" , DrawableHeight="+DrawableHeight));
		
		mBoundsLeft = centerX-DrawableWidth/2;
		mBoundsTop=centerY-DrawableHeight;
		mBoundsRight=centerX+DrawableWidth/2;
		mBoundsBottom=centerY+DrawableHeight/2;
		Log.d("tomoon", (mBoundsLeft+","+mBoundsTop+","+mBoundsRight+","+mBoundsBottom));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		DrawableWidth = getMeasuredWidth();
		DrawableHeight = getMeasuredHeight();
		Log.d("-------onMeasure", ("DrawableWidth="+DrawableWidth+" , DrawableHeight="+DrawableHeight));
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.rotate(mRotateDegrees, centerX, centerY);
		handDrawable.setBounds(mBoundsLeft, mBoundsTop, mBoundsRight, centerY);
		handDrawable.draw(canvas);
	
		count++;
	}
	
	/**
	 * @param angle
	 */
	public void RotateHanderWithAngle(float angle){
		mRotateDegrees = angle;
		this.invalidate();
	}
	/**
	 * PostRotateHanderWithAngle
	 * @param angle
	 */
	public void PostRotateHanderWithAngle(float angle){
		mRotateDegrees = angle;
		this.postInvalidate();
	}
	/**
	 */
	public void SetHandImageSrc(Context context,int src) {
		handDrawable = context.getResources().getDrawable(src);
		GetDrawableSize();
		this.invalidate();
	}
/**
 */
	public void SetHandImageAsDefault() {
		handDrawable=defaultHandDrawable;
		GetDrawableSize();
		this.invalidate();
	}
}
