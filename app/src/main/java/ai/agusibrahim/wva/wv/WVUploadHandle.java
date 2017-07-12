package ai.agusibrahim.wva.wv;
import ai.agusibrahim.wva.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.ValueCallback;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebView;
import android.provider.MediaStore;
import java.io.File;
import android.util.Log;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.os.Environment;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.webkit.DownloadListener;
import android.app.DownloadManager;
import android.webkit.URLUtil;

// upload file based on https://github.com/mgks/Os-FileUp
public class WVUploadHandle extends BaseActivity
{
	private ValueCallback<Uri> mUM;
	private String mCM;
    private ValueCallback<Uri[]> mUMA;
	private final static int FCR=1; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EnableJavascript();
		web.getSettings().setAllowFileAccess(true);
		if(Build.VERSION.SDK_INT >= 21){
            web.getSettings().setMixedContentMode(0);
            web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else if(Build.VERSION.SDK_INT >= 19){
            web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else if(Build.VERSION.SDK_INT < 19){
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
		web.setWebViewClient(new WebViewClient());
		web.setWebChromeClient(new WebChromeClient(){
				//For Android 3.0+
				public void openFileChooser(ValueCallback<Uri> uploadMsg){
					mUM = uploadMsg;
					Intent i = new Intent(Intent.ACTION_GET_CONTENT);
					i.addCategory(Intent.CATEGORY_OPENABLE);
					i.setType("*/*");
					startActivityForResult(Intent.createChooser(i,"File Chooser"), FCR);
				}
				// For Android 3.0+, above method not supported in some android 3+ versions, in such case we use this
				public void openFileChooser(ValueCallback uploadMsg, String acceptType){
					mUM = uploadMsg;
					Intent i = new Intent(Intent.ACTION_GET_CONTENT);
					i.addCategory(Intent.CATEGORY_OPENABLE);
					i.setType("*/*");
					startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FCR);
				}
				//For Android 4.1+
				public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
					mUM = uploadMsg;
					Intent i = new Intent(Intent.ACTION_GET_CONTENT);
					i.addCategory(Intent.CATEGORY_OPENABLE);
					i.setType("*/*");
					startActivityForResult(Intent.createChooser(i, "File Chooser"), FCR);
				}
				//For Android 5.0+
				public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    WebChromeClient.FileChooserParams fileChooserParams){
					if(mUMA != null){
						mUMA.onReceiveValue(null);
					}
					mUMA = filePathCallback;
					Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if(takePictureIntent.resolveActivity(getPackageManager()) != null){
						File photoFile = null;
						try{
							photoFile = createImageFile();
							takePictureIntent.putExtra("PhotoPath", mCM);
						}catch(IOException ex){
							Log.e(TAG, "Image file creation failed", ex);
						}
						if(photoFile != null){
							mCM = "file:" + photoFile.getAbsolutePath();
							takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
						}else{
							takePictureIntent = null;
						}
					}
					Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
					contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
					contentSelectionIntent.setType("*/*");
					Intent[] intentArray;
					if(takePictureIntent != null){
						intentArray = new Intent[]{takePictureIntent};
					}else{
						intentArray = new Intent[0];
					}

					Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
					chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
					chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
					chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
					startActivityForResult(chooserIntent, FCR);
					return true;
				}
			});
		// download handle based on https://stackoverflow.com/a/25250492
		web.setDownloadListener(new DownloadListener() {
				@Override
				public void onDownloadStart(final String url,final String userAgent,final String contentDisposition,final String mimetype,final long contentLength)
				{
					final String fileName=URLUtil.guessFileName(url,contentDisposition,mimetype);
					DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						request.allowScanningByMediaScanner();
						request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
					}
					request.setMimeType(mimetype);
					DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
					request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
					manager.enqueue(request);
				}
			});
		web.loadUrl("https://filebin.net/");
	}
	private File createImageFile() throws IOException{
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_"+timeStamp+"_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName,".jpg",storageDir);
    }
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(Build.VERSION.SDK_INT >= 21){
            Uri[] results = null;
            //Check if response is positive
            if(resultCode== RESULT_OK){
                if(requestCode == FCR){
                    if(null == mUMA){
                        return;
                    }
                    if(intent == null){
                        //Capture Photo if no image available
                        if(mCM != null){
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    }else{
                        String dataString = intent.getDataString();
                        if(dataString != null){
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        }else{
            if(requestCode == FCR){
                if(null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }
	@Override
	public void onBackPressed() {
		if(web.canGoBack())
			web.goBack();
		else
			super.onBackPressed();
	}
}
