package ai.agusibrahim.wva.wv;

import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.webkit.WebView;
import android.view.View.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import android.widget.Toast;
import android.graphics.Picture;
import android.os.Build;
import android.os.AsyncTask;
import android.app.ProgressDialog;

public class WVScreenshot extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			WebView.enableSlowWholeDocumentDraw();
		}
		super.onCreate(savedInstanceState);
		Button ssbtn=new Button(this);
		button_container.addView(ssbtn);
		web.setWebViewClient(new WebViewClient());
		web.loadUrl(MYURL);
		ssbtn.setText("Capture Page");
		ssbtn.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					wvcapture(web);
				}
			});
	}

	@Override
	public void onBackPressed() {
		if(web.canGoBack())
			web.goBack();
		else
			super.onBackPressed();
	}
	private void wvcapture(final WebView webView){
		new AsyncTask(){
			Exception ex;
			File file;
			ProgressDialog pdlg;
			@Override
			protected Object doInBackground(Object[] p1) {
				Bitmap bm = Bitmap.createBitmap(webView.getMeasuredWidth(), webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
				Canvas bigcanvas = new Canvas(bm);
				Paint paint = new Paint();
				int iHeight = bm.getHeight();
				bigcanvas.drawBitmap(bm, 0, iHeight, paint);
				webView.draw(bigcanvas);
				if(bm!=null) {
					try {
						File path = new File(Environment.getExternalStorageDirectory(), "DCIM");
						OutputStream fOut = null;
						file = new File(path, String.format("web_%s.png", System.currentTimeMillis()));
						fOut = new FileOutputStream(file);
						bm.compress(Bitmap.CompressFormat.PNG, 50, fOut);
						fOut.flush();
						fOut.close();
						bm.recycle();
					} catch(Exception e) {
						ex = e;
					}
				} else {
					ex = new Exception("Bitmap is Null");
				}
				return null;
			}
			@Override
			protected void onPreExecute() {
				webView.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				webView.setDrawingCacheEnabled(true);
				webView.buildDrawingCache();
				pdlg = ProgressDialog.show(WVScreenshot.this, null, "Capturing...", true, false);
			}
			@Override
			protected void onPostExecute(Object p1) {
				pdlg.dismiss();
				if(ex==null) {
					Toast.makeText(WVScreenshot.this, "Saved to "+file.getPath(), 0).show();
				} else {
					Toast.makeText(WVScreenshot.this, "Could't saved, "+ex.getMessage(), 0).show();
				}
			}
		}.execute();
	}
}
