package ai.agusibrahim.wva.wv;
import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

public class WVBasic extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		web.setWebViewClient(new WebViewClient());
		web.loadUrl(MYURL);
	}

	@Override
	public void onBackPressed() {
		if(web.canGoBack())
			web.goBack();
		else
			super.onBackPressed();
	}
}
