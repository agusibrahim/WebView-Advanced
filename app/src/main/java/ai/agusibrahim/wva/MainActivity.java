package ai.agusibrahim.wva;
import android.app.Activity;
import android.os.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;
import android.view.View;
import ai.agusibrahim.wva.wv.*;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener
{
	String[] menu=new String[]{"Basic & Enable Back", "Basic Web Events", "Load Progress", "Disable Images", "Block Content (Images and Styling)",
	"Load from String/Assets", "Custom Error", "JS Mod and Hook", "Source Viewer", "Form Interaction", "Youtube Downloader (experimental)", "Context Menu"
	,"Upload/Download Handle", "Web Screenshot"};
	private ArrayAdapter<String> adaptr;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lv=new ListView(this);
		setContentView(lv);
		adaptr=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
		lv.setAdapter(adaptr);
		lv.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int pos, long p4) {
		Class act = null;
		switch(pos){
			case 0:
				act=WVBasic.class;
				break;
			case 1:
				act=WVBasicEvent.class;
				break;
			case 2:
				act=WVLoadProgress.class;
				break;
			case 3:
				act=WVDisableImage.class;
				break;
			case 4:
				act=WVDisableImagePlus.class;
				break;
			case 5:
				act=WVLoadFromString.class;
				break;
			case 6:
				act=WVCustomError.class;
				break;
			case 7:
				act=WVJSDataTransaction.class;
				break;
			case 8:
				act=WVSourceViewer.class;
				break;
			case 9:
				act=WVFormInteract.class;
				break;
			case 10:
				act=WVYoutubeDl.class;
				break;
			case 11:
				act=WVContextMenu.class;
				break;
			case 12:
				act=WVUploadHandle.class;
				break;
			case 13:
				act=WVScreenshot.class;
				break;
		}
		startActivity(new Intent(this, act));
	}
}
