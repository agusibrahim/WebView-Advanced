package ai.agusibrahim.wva.wv;

import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WVFormInteract extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button btn=new Button(this);
		Button btn1=new Button(this);
		Button btn2=new Button(this);
		button_container.addView(btn);
		button_container.addView(btn1);
		button_container.addView(btn2);
		EnableJavascript();
		web.setWebViewClient(new WebViewClient());
		web.loadUrl("https://github.com/login?return_to=%2Fagusibrahim");
		btn.setText("Fill Form");
		btn1.setText("Fill&Submit");
		btn2.setText("Get Form Data");
		btn.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					web.loadUrl("javascript:(function(){ document.getElementsByName('login')[0].value='agusibrahim';document.getElementsByName('password')[0].value='agusibrahim1945'; })();");
				}
			});
		btn1.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					web.loadUrl("javascript:(function(){ document.getElementsByName('login')[0].value='agusibrahim';document.getElementsByName('password')[0].value='agusibrahim1945';document.getElementsByTagName('button')[1].click(); })();");
				}
			});
		btn2.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					web.loadUrl("javascript:callme.setData(0, document.getElementsByName('login')[0].value+'--'+document.getElementsByName('password')[0].value)");
				}
			});
	}

	@Override
	public void onReceiveValue(int tag, String data) {
		String[] cred=data.split("--");
		log("Username: "+cred[0]+"\nPassword: "+cred[1], true);
	}
	

	@Override
	public void onBackPressed() {
		if(web.canGoBack())
			web.goBack();
		else
			super.onBackPressed();
	}
}
