package cn.tomoon.clock.plugin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ScaleableView extends ImageView{
	private final String namespace = "http://tomoon.cn"; 
	private float mRotateDegrees; 
	private int mBackGroudDrawableId;
	private Drawable mBackGroudDrawable;
	private int mBackGroundWidth;
	private int mBackGroundHeight;
	private Context mContext;
	private float mScale;
	
	public ScaleableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext=context;
		mBackGroudDrawableId=attrs.getAttributeResourceValue(namespace, "background", R.drawable.green2);
		mBackGroudDrawable = context.getResources().getDrawable(
				mBackGroudDrawableId);		
		mRotateDegrees=attrs.getAttributeFloatValue(namespace, "rotateDegrees",0.0f);
		mScale=attrs.getAttributeFloatValue(namespace, "scale",1.0f);
		mScale = mScale > 1 ? 1.0f : mScale; 
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (mRotateDegrees==-90.0f) {
			canvas.rotate(mRotateDegrees, 0, 0);
			canvas.translate(-mBackGroundWidth*mScale, 0);
		}else if(mRotateDegrees==90.0f){
			canvas.rotate(mRotateDegrees, 0, 0);
			canvas.translate(0,-mBackGroundHeight*mScale);
		}
		else {
			canvas.rotate(mRotateDegrees, mBackGroundWidth*mScale/2, mBackGroundHeight*mScale/2);
		}
		mBackGroudDrawable.setBounds(0, 0, (int)(mBackGroundWidth*mScale), (int)(mBackGroundHeight*mScale));
		mBackGroudDrawable.draw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		mBackGroundHeight = mBackGroudDrawable.getMinimumHeight();
		mBackGroundWidth = mBackGroudDrawable.getMinimumWidth();
		
//		mBackGroundHeight=(int)(mBackGroudDrawable.getMinimumHeight()*mScale);
//		mBackGroundWidth=(int)(mBackGroudDrawable.getMinimumWidth()*mScale);
		
//		Log.d("mScale", mScale+"");
		
		if (mRotateDegrees==-90.0f) {
			setMeasuredDimension((int)(mBackGroundHeight*mScale), (int)(mBackGroundWidth*mScale));
			setMinimumWidth((int)(mBackGroundWidth*mScale));
			setMinimumHeight((int)(mBackGroundHeight*mScale));
		}else if(mRotateDegrees==90.0f){
			setMeasuredDimension((int)(mBackGroundHeight*mScale), (int)(mBackGroundWidth*mScale));
			setMinimumWidth((int)(mBackGroundWidth*mScale));
			setMinimumHeight((int)(mBackGroundHeight*mScale));
		}
		else {
			setMeasuredDimension((int)(mBackGroundWidth*mScale), (int)(mBackGroundHeight*mScale));
			setMinimumWidth((int)(mBackGroundWidth*mScale));
			setMinimumHeight((int)(mBackGroundHeight*mScale));
		}		
	}
	
	public void SetRotateDegrees(float degrees){
		mRotateDegrees =degrees;
		invalidate();
	}
	
	public void setImageSrc(int drawableID){
		mBackGroudDrawable = mContext.getResources().getDrawable(
				drawableID);
		invalidate();
	}
}
