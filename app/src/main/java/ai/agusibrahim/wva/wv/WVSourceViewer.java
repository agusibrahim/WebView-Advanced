package ai.agusibrahim.wva.wv;

import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.app.AlertDialog;

public class WVSourceViewer extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button btn=new Button(this);
		button_container.addView(btn);
		EnableJavascript();
		web.setWebViewClient(new WebViewClient());
		web.loadUrl(MYURL);
		btn.setText("Get Source");
		btn.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					web.loadUrl("javascript:callme.setData(0, document.documentElement.outerHTML);");
				}
			});
	}

	@Override
	public void onReceiveValue(int tag, String data) {
		AlertDialog.Builder ad=new AlertDialog.Builder(this);
		ad.setTitle("Source Viewer");
		ad.setMessage(data);
		ad.show();
	}

	@Override
	public void onBackPressed() {
		if(web.canGoBack())
			web.goBack();
		else
			super.onBackPressed();
	}
}
