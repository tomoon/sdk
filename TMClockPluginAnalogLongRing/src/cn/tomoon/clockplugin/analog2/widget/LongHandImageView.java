package cn.tomoon.clockplugin.analog2.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import cn.tomoon.clockplugin.analog2.R;

public class LongHandImageView extends View {
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
	
	private int centerX;
	private int centerY;
	int DrawableWidth;
	int DrawableHeight;
	
	public LongHandImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initail(context,attrs);
	}
	@SuppressLint("Recycle")
	private void initail(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.handimageview);
		handDrawable = context.getResources().getDrawable(
				mTypedArray.getResourceId(R.styleable.handimageview_myhandsrc,
						R.drawable.ic_launcher));
		defaultHandDrawable = handDrawable;
		DrawableWidth = handDrawable.getMinimumWidth();
		DrawableHeight= handDrawable.getMinimumHeight();
	}
	
	private void GetDrawableSize() {
		mBoundsLeft = centerX-DrawableWidth/2;
		mBoundsTop=centerY-DrawableHeight/2;
		mBoundsRight=centerX+DrawableWidth/2;
		mBoundsBottom=centerY+DrawableHeight/2;
//		Log.d("tomoon", (mBoundsLeft+","+mBoundsTop+","+mBoundsRight+","+mBoundsBottom));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		centerX = getMeasuredWidth()/2;
		centerY = getMeasuredHeight()/2;
//		Log.d("-------onMeasure", ("centerX="+centerX+" , centerY="+centerY));
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		GetDrawableSize();
		canvas.rotate(mRotateDegrees, centerX, centerY);
		handDrawable.setBounds(mBoundsLeft, mBoundsTop, mBoundsRight, mBoundsBottom);
		handDrawable.draw(canvas);
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
