package com.yqm.base1.drawables;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

/**
*  圆形的Drawble
* 
*/
public class CircleDrawable extends Drawable {
	// 画笔
	private Paint mPaint;
	// 半径
	private int mRedius;
	// 位图
	private Bitmap mBitmap;

	private static Context mContext;

	private WeakReference<ImageView> mImg;

	public CircleDrawable(ImageView iv, Bitmap _bitmap) {
		mBitmap = _bitmap;
		init(iv);
	}

	public CircleDrawable(ImageView iv, int imgId) {
		Log.i("CircleDrawable","ImageView="+iv);
		mContext = iv.getContext();
		Log.i("CircleDrawable","mContext="+mContext);
		mBitmap=BitmapFactory.decodeResource(mContext.getResources(), imgId);
		Log.i("CircleDrawable","Bitmap="+mBitmap);
		init(iv);
	}

	private void init(ImageView iv) {
		mImg = new WeakReference<ImageView>(iv);
		// 创建着色器对象
		BitmapShader shader = new BitmapShader(mBitmap, TileMode.CLAMP, TileMode.CLAMP);
		// 创建画笔对象
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 抗锯齿/mPaint.setAntiAlias(true);
		// 设置着色器
		mPaint.setShader(shader);
		// 获取bitmap宽高的最小值
        mRedius = Math.min(mBitmap.getWidth(),mBitmap.getHeight());
	}
	
	
	
	public void setToTarget(){
		ImageView des = mImg.get();
		des.setImageDrawable(this);
	}

	@Override
	public void draw(Canvas canvas) {
	
		canvas.drawCircle(mRedius/2,mRedius/2 , mRedius/2, mPaint);
	}

	@Override
	public void setAlpha(int alpha) {
		// 设置画笔的透明度
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {

		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public int getIntrinsicHeight() {
		return mRedius;
	}

	@Override
	public int getIntrinsicWidth() {
		return mRedius;
	}

}
