package ai.agusibrahim.wva.wv;

import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class WVJSDataTransaction extends BaseActivity
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
		web.setWebViewClient(new WebViewClient(){
				@Override
				public void onPageFinished(android.webkit.WebView view, java.lang.String url) {
					// Hook follow button
					web.loadUrl("javascript:document.getElementsByClassName('follow')[0].childNodes[1].onclick=function(e){e.preventDefault(); callme.setData(0, 'Follow Button Clicked');}");
				}
		});
		web.loadUrl(MYURL);
		btn1.setText("Set Address");
		btn.setText("Get Adderss");
		btn2.setText("Test Mod");
		btn.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					web.loadUrl("javascript:callme.setData(0, document.getElementsByClassName('details-item')[0].childNodes[2].nodeValue);");
				}
			});
		btn1.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					web.loadUrl("javascript:(function(){document.getElementsByClassName('details-item')[0].childNodes[2].nodeValue=\" Stockholm, Sweden\";})();");
				}
			});
		btn2.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					web.loadUrl("javascript:cc=document.getElementsByClassName('Counter');");
					web.loadUrl("javascript:(function(){ cc[0].innerHTML='1234';cc[1].innerHTML='100';cc[2].innerHTML='9999'; })();");
				}
			});
		Toast.makeText(this, "try to click follow button to test hooking",1).show();
	}

	@Override
	public void onReceiveValue(int tag, String data) {
		log(data, true);
	}
}
