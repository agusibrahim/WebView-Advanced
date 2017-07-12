package ai.agusibrahim.wva.wv;
import ai.agusibrahim.wva.BaseActivity;
import android.os.*;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.view.View;
import android.webkit.WebViewClient
;

public class WVLoadProgress extends BaseActivity
{
	CharSequence oldtitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		oldtitle=getTitle();
		web.setWebViewClient(new WebViewClient());
		web.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int progress) {
				setTitle(String.format("Loading... %s%%",progress));
				if(progress==100) setTitle(oldtitle);
			}
		});
		web.loadUrl(MYURL);
	}
}
