package ai.agusibrahim.wva.wv;

import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.webkit.WebResourceResponse;

public class WVDisableImagePlus extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		web.clearCache(true);
		web.getSettings().setBlockNetworkImage(true);
		web.setWebViewClient(new WebViewClient(){
			@Override
			public android.webkit.WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, android.webkit.WebResourceRequest request) {
				WebResourceResponse nullresp = new WebResourceResponse("", "", null); 
				log(request.getUrl().toString(), false);
				if(request.getUrl().toString().matches(".*\\.(ttf|woff|otf|css|ico|jpe?g|png|gif).*")){
					return nullresp;
				}
				
				return super.shouldInterceptRequest(view, request);
			}
		});
		web.loadUrl(MYURL);
	}
}
