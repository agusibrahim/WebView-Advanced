package ai.agusibrahim.wva.wv;
import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

public class WVDisableImage extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		web.clearCache(true);
		web.getSettings().setBlockNetworkImage(true);
		web.setWebViewClient(new WebViewClient());
		web.loadUrl(MYURL);
	}
}
