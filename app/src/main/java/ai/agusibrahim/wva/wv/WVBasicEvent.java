package ai.agusibrahim.wva.wv;
import android.os.*;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Toast;
import ai.agusibrahim.wva.BaseActivity;

public class WVBasicEvent extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		web.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(android.webkit.WebView view, java.lang.String url, android.graphics.Bitmap favicon) {
				log("Web Start", true);
			}
			@Override
			public void onPageFinished(android.webkit.WebView view, java.lang.String url) {
				log("Web Finish", true);
			}
			@Override
			public void onReceivedError(android.webkit.WebView view, android.webkit.WebResourceRequest request, android.webkit.WebResourceError error) {
				log("Web Error", true);
			}
		});
		web.loadUrl(MYURL);
	}
}
