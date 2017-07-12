package ai.agusibrahim.wva.wv;

import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.webkit.WebResourceResponse;
import java.util.List;
import java.util.ArrayList;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.DownloadManager;

public class WVYoutubeDl extends BaseActivity
{
	Button btn;
	List<Video> videos=new ArrayList<Video>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		btn=new Button(this);
		button_container.addView(btn);
		button_container.setVisibility(View.GONE);
		EnableJavascript();
		web.setWebViewClient(new WebViewClient(){
			@Override
			public android.webkit.WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, android.webkit.WebResourceRequest request) {
				if(request.getUrl().toString().matches(".*googlevideo.com/videoplayback.*")){
					Video vid=new Video(request.getUrl().toString());
					boolean isExists = false;
					for(Video v:videos){
						if(v.size==vid.size) isExists=true;
					}
					if(!isExists) videos.add(vid);
					new Handler(Looper.getMainLooper()).post(new Runnable(){
							@Override
							public void run(){
								button_container.setVisibility(View.VISIBLE);
							}
						});
					return new WebResourceResponse("", "", null);
				}
				return super.shouldInterceptRequest(view, request);
			}
			@Override
			public void onPageFinished(android.webkit.WebView view, java.lang.String url) {
				videos.clear();
				if(url.contains("watch?v="))
					new Handler().postDelayed(new Runnable(){
							@Override
							public void run() {
								web.loadUrl("javascript:document.getElementsByTagName('video')[0].play();");
							}
						}, 1000);
					//Toast.makeText(WVYoutubeDl.this, "Play Video First to Enable Download",0).show();
			}
		});
		web.setWebChromeClient(new WebChromeClient(){
				@Override
				public void onProgressChanged(WebView view, int progress) {
					new Handler(Looper.getMainLooper()).post(new Runnable(){
							@Override
							public void run(){
								button_container.setVisibility(View.GONE);
							}
						});
				}
			});
		web.loadUrl("https://youtube.com/");
		btn.setText("Download Video");
		btn.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					String[] vids=new String[videos.size()];
					for(int i=0;i<videos.size();i++){
						Video v=videos.get(i);
						vids[i]=(v.isAudioOnly?"Audio":"Video")+" ("+v.readableSize+")";
					}
					AlertDialog.Builder dlg=new AlertDialog.Builder(WVYoutubeDl.this);
					dlg.setTitle("Download");
					dlg.setItems(vids, new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface p1, int p2) {
								download_(videos.get(p2).url);
							}
						});
					dlg.show();
				}
			});
		showAlert();
	}

	@Override
	public void onBackPressed() {
		if(web.canGoBack())
			web.goBack();
		else
			super.onBackPressed();
	}
	private void showAlert(){
		new AlertDialog.Builder(this).setTitle("WARNING").setMessage("Downloading Youtube videos is against Google Terms of Service, do with your own risk.").show();
	}
	private String readableFileSize(long size) {
		if(size <= 0) return "0";
		final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		return new java.text.DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	private void download_(String url){
		DownloadManager dmgr = (DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);
		DownloadManager.Request request = new DownloadManager.Request(android.net.Uri.parse(url));
		request.setAllowedNetworkTypes(
			DownloadManager.Request.NETWORK_WIFI
			| DownloadManager.Request.NETWORK_MOBILE)
			.setAllowedOverRoaming(false).setTitle("Download")
			.setDescription("Downloading Video...")
			.setDestinationInExternalPublicDir("/Download/", System.currentTimeMillis()+".mp4");
		dmgr.enqueue(request);
	}
	public class Video{
		public String url;
		public boolean isAudioOnly;
		public long size;
		public String readableSize;
		public Video(String s){
			String ss=s.replaceAll("&range=[\\d-]*&","&");
			url=ss;
			isAudioOnly=ss.contains("mime=audio");
			size=Long.parseLong( ss.split("&clen=")[1].split("&")[0]);
			readableSize=readableFileSize(size);
		}
	}
}
