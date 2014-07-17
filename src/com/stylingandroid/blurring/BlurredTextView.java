/*******************************************************************************
 * Copyright (c) 2014 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.stylingandroid.blurring;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.util.TimingLogger;
import android.widget.ImageView;
import android.widget.TextView;

public class BlurredTextView extends TextView {
	private long mFrameCount = 0;

	private Bitmap mOverlay = null;
	private Canvas mOverlayCanvas = null;
	private Bitmap mBlurredBackground = null;

	public BlurredTextView(Context context) {
		this(context, null, -1);
	}

	public BlurredTextView(Context context, AttributeSet attrs) {
		super(context, attrs, -1);
	}

	public BlurredTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mBlurredBackground != null) {
			mFrameCount++;
			mOverlayCanvas.drawBitmap(mBlurredBackground, -getLeft() - getTranslationX(), -getTop() - getTranslationY(), null);
			canvas.drawBitmap(mOverlay, 0, 0, null);
		}
		super.onDraw(canvas);
	}

	public long getFrameCount() {
		return mFrameCount;
	}

	public void setFrameCount(long frameCount) {
		this.mFrameCount = frameCount;
	}

	public void initBlur(ImageView bgd, float radius) {
		Bitmap background = null;
		if (bgd != null && bgd.getDrawable() != null) {
			Drawable drawable = bgd.getDrawable();
			if (drawable != null && drawable instanceof BitmapDrawable) {
				background = ((BitmapDrawable) drawable).getBitmap();
				if (background.isRecycled()) {
					background = null;
				}
			}
		}
		if(background != null) {
			TimingLogger tl = new TimingLogger(MainActivity.TAG, "Full Blur");
			RenderScript rs = RenderScript.create(getContext());
			tl.addSplit("RenderScript.create()");
			mBlurredBackground = Bitmap.createBitmap(background.getWidth(), background.getHeight(), background.getConfig());
			tl.addSplit("Bitmap.createBitmap()");
			Canvas canvas = new Canvas(mBlurredBackground);
			tl.addSplit("new Canvas()");
			canvas.drawBitmap(background, 0, 0, null);
			tl.addSplit("canvas.drawBitmap()");
			Allocation alloc = Allocation.createFromBitmap(rs, mBlurredBackground);
			tl.addSplit("Allocation.createFromBitmap()");
			ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, alloc.getElement());
			tl.addSplit("ScriptIntrinsicBlur.create()");
			blur.setInput(alloc);
			tl.addSplit("blur.setInput()");
			blur.setRadius(radius);
			tl.addSplit("blur.setRadius()");
			blur.forEach(alloc);
			tl.addSplit("blur.forEach()");
			alloc.copyTo(mBlurredBackground);
			tl.addSplit("alloc.copyTo");
			rs.destroy();
			tl.addSplit("rs.destroy()");
			tl.dumpToLog();
		}
		resize();
	}

	private void resize() {
		if (mBlurredBackground != null) {
			mOverlay = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
			mOverlayCanvas = new Canvas(mOverlay);
		}
	}

	public void cleanupBlur() {
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (w != oldw || h != oldh) {
			resize();
		}
	}
}
