package ai.agusibrahim.wva.wv;

import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.view.*;
import android.view.ContextMenu.*;
import android.webkit.WebView.*;
import android.webkit.WebView;
import android.widget.*;

public class WVContextMenu extends BaseActivity implements MenuItem.OnMenuItemClickListener
{
	private ActionMode mActionMode = null;
	private final int MENU_IMAGE_SHARE=22;
	private final int MENU_IMAGE_SAVE=23;
	private final int MENU_LINK_SHARE=24;
	private final int MENU_LINK_NEWTAB=25;
	private final int MENUACTION_CUSTOM1=27;
	private final int MENUACTION_CUSTOM2=28;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerForContextMenu(web);
		EnableJavascript();
		web.setWebViewClient(new WebViewClient());
		web.loadUrl(MYURL);
		Toast.makeText(this, "Try to Longclick on Link/Images or selection a text", 1).show();
	}

	@Override
	public void onBackPressed() {
		if(web.canGoBack())
			web.goBack();
		else
			super.onBackPressed();
	}

	@Override
	public void onReceiveValue(int tag, String data) {
		Toast.makeText(this, "Section Text:\n"+data, 0).show();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		WebView.HitTestResult result = web.getHitTestResult();
		if (result.getType() == HitTestResult.ANCHOR_TYPE || result.getType() == HitTestResult.SRC_ANCHOR_TYPE) {
			menu.setHeaderTitle(result.getExtra());
			menu.add(0, MENU_LINK_NEWTAB, 0, "Open new Tab").setOnMenuItemClickListener(this);
			menu.add(0, MENU_LINK_SHARE, 0, "Share Link").setOnMenuItemClickListener(this);
		} else if (result.getType() == HitTestResult.IMAGE_TYPE || result.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
			menu.setHeaderTitle(result.getExtra());
			menu.add(0, MENU_IMAGE_SAVE, 0, "Save Image").setOnMenuItemClickListener(this);
			menu.add(0, MENU_IMAGE_SHARE, 0, "Share Image").setOnMenuItemClickListener(this);
		}
	}
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Toast.makeText(this, "Menu Clicked "+item.getItemId(), 0).show();
		if(item.getItemId()==MENUACTION_CUSTOM1||item.getItemId()==MENUACTION_CUSTOM2){
			web.loadUrl("javascript:callme.setData(0, window.getSelection().toString())");
		}
		return true;
	}
	@Override
	public void onActionModeStarted(ActionMode mode) {
		if (mActionMode == null) {
            mActionMode = mode;
            Menu menu = mode.getMenu();
            menu.clear();
			menu.add(0, MENUACTION_CUSTOM1, 0, "Hi Agus").setOnMenuItemClickListener(this);
			menu.add(0, MENUACTION_CUSTOM2, 0, "Translate").setOnMenuItemClickListener(this);
		}
	}
	@Override
    public void onActionModeFinished(ActionMode mode) {
        mActionMode = null;
        super.onActionModeFinished(mode);
    }
}
