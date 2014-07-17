package com.stylingandroid.blurring;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.util.TimingLogger;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.ImageView;
import com.NewCleanWeather.R;

public class MainActivity extends Activity {
	public static final String TAG = "Blurring";
	private ImageView mImage;
	private BlurredTextView mText;

	private ObjectAnimator mAnimator = null;
	private long mStart = 0;
	private long mFrameCount = 0;

	private OnPreDrawListener mPreDrawListener = new OnPreDrawListener() {
		@Override
		public boolean onPreDraw() {
			Drawable drawable = mImage.getDrawable();
			if (mAnimator != null && drawable != null && drawable instanceof BitmapDrawable) {
				Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
				if (bitmap != null) {
					mText.invalidate();
				}
			}
			mFrameCount++;
			return true;
		}
	};

	private ViewTreeObserver.OnGlobalLayoutListener mLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {
			ViewTreeObserver observer = mText.getViewTreeObserver();
			if (observer != null) {
				observer.addOnPreDrawListener(mPreDrawListener);
			}
		}
	};

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mImage = (ImageView) findViewById(R.id.image);
		mText = (BlurredTextView) findViewById(R.id.text);
		final Button button = (Button) findViewById(R.id.animate);
		if (mImage != null && mText != null) {
			ViewTreeObserver observer = mText.getViewTreeObserver();
			if (observer != null) {
				observer.addOnGlobalLayoutListener(
						mLayoutListener);
			}
		}
		mText.setText("Animated");
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAnimator == null) {
					button.setText(R.string.stop);
					startAnimation();
				} else {
					button.setText(R.string.start);
					stopAnimation();
				}
			}
		});
	}

	private void startAnimation() {
		mText.initBlur(mImage, 12);
		mStart = System.currentTimeMillis();
		mText.setFrameCount(0);
		mFrameCount = 0;
		mAnimator = ObjectAnimator.ofFloat(mText, "translationY", 0, -200);
		mAnimator.setDuration(1000);
		mAnimator.setRepeatMode(ValueAnimator.REVERSE);
		mAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mAnimator.start();
	}

	private void stopAnimation() {
		mText.cleanupBlur();
		mAnimator.cancel();
		float elapsed = (float) (System.currentTimeMillis() - mStart) / 1000.0f;
		long framecount = mText.getFrameCount();
		float fps = framecount / elapsed;
		Log.d(TAG, getString(R.string.framerate, elapsed, mFrameCount, framecount, fps));
		mAnimator = null;
	}

	private void blur(Bitmap bkg, View view, float radius) {

		Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(overlay);
		canvas.drawBitmap(bkg, -view.getLeft() - view.getTranslationX(), -view.getTop() - view.getTranslationY(), null);

		RenderScript rs = RenderScript.create(this);

		Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);

		ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());

		blur.setInput(overlayAlloc);

		blur.setRadius(radius);

		blur.forEach(overlayAlloc);

		overlayAlloc.copyTo(overlay);

		view.setBackground(new BitmapDrawable(getResources(), overlay));

		rs.destroy();
	}

	private void blurJava(Bitmap bkg, View view, int radius) {
		TimingLogger tl = new TimingLogger(TAG, "blur");

		Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		tl.addSplit("Bitmap.createBitmap()");

		Canvas canvas = new Canvas(overlay);
		tl.addSplit("new Canvas()");
		canvas.drawBitmap(bkg, -view.getLeft(), -view.getTop(), null);
		tl.addSplit("canvas.drawBitmap()");

		Bitmap blurred = fastblur(overlay, radius);
		tl.addSplit("fastblur()");

		view.setBackground(new BitmapDrawable(getResources(), blurred));
		tl.addSplit("view.setBackground()");

		tl.dumpToLog();
	}

	/*
	 * This method was copied from http://stackoverflow.com/a/10028267/694378.
	 * The only modifications I've made are to remove a couple of Log
	 * statements which could slow things down slightly.
	 */
	public Bitmap fastblur(Bitmap sentBitmap, int radius) {

		// Stack Blur v1.0 from
		// http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
		//
		// Java Author: Mario Klingemann <mario at quasimondo.com>
		// http://incubator.quasimondo.com
		// created Feburary 29, 2004
		// Android port : Yahel Bouaziz <yahel at kayenko.com>
		// http://www.kayenko.com
		// ported april 5th, 2012

		// This is a compromise between Gaussian Blur and Box blur
		// It creates much better looking blurs than Box Blur, but is
		// 7x faster than my Gaussian Blur implementation.
		//
		// I called it Stack Blur because this describes best how this
		// filter works internally: it creates a kind of moving stack
		// of colors whilst scanning through the image. Thereby it
		// just has to add one new block of color to the right side
		// of the stack and remove the leftmost color. The remaining
		// colors on the topmost layer of the stack are either added on
		// or reduced by one, depending on if they are on the right or
		// on the left side of the stack.
		//
		// If you are using this algorithm in your code please add
		// the following line:
		//
		// Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		bitmap.setPixels(pix, 0, w, 0, 0, w, h);

		return (bitmap);
	}
}
