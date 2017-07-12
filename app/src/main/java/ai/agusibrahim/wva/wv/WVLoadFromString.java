package ai.agusibrahim.wva.wv;

import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WVLoadFromString extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button btn=new Button(this);
		button_container.addView(btn);
		web.setWebViewClient(new WebViewClient());
		web.loadData("Hello World Wide Web<br><br><i>Agus Ibrahim</i>", "text/html", "UTF-8");
		btn.setText("Load from Assets");
		btn.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					web.loadUrl("file:///android_asset/main.html");
				}
			});
	}
}
