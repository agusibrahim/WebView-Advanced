package ai.agusibrahim.wva.wv;
import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

public class WVCustomError extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		web.setWebViewClient(new WebViewClient(){
			@Override
			public void onReceivedError(android.webkit.WebView view, android.webkit.WebResourceRequest request, android.webkit.WebResourceError error) {
				web.loadData("<b>Error when load page,</b> "+error.getDescription()+"<br><i>sa ae lu</i><br><img style='width: 200px;' src='http://i0.kym-cdn.com/photos/images/newsfeed/000/101/781/Y0UJC.png'/>", "text/html", "UTF-8");
			}
		});
		web.loadUrl("http://goo.gle");
	}
}
