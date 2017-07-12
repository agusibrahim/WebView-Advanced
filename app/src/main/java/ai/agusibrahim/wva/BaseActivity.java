package ai.agusibrahim.wva;

import android.app.*;
import android.os.*;
import android.webkit.WebView;
import android.widget.Button;
import android.webkit.WebViewClient;
import android.view.View;
import android.widget.Toast;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

public class BaseActivity extends Activity
{
	public WebView web;
	public LinearLayout button_container;
	public String MYURL="https://github.com/agusibrahim";
	public String TAG="WebViewAdv";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		web=(WebView) findViewById(R.id.mainWebView);
		button_container=(LinearLayout) findViewById(R.id.mainLinearLayout1);
    }
	public void log(String msg, boolean showtoast){
		if (showtoast) Toast.makeText(this, msg, 0).show();
		android.util.Log.d(TAG, msg);
	}
	public void EnableJavascript(){
		web.getSettings().setJavaScriptEnabled(true);
		web.addJavascriptInterface(new MyJS(), "callme");
	}
	public void onReceiveValue(int tag, String data){
		
	}
	
	private class MyJS{
		@JavascriptInterface
		public void setData(int tag, String data){
			onReceiveValue(tag, data);
		}
	}
} 
