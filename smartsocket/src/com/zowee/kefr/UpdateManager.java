package com.zowee.kefr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gizwits.powersocket.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;


/**
 *@author coolszy
 *@date 2012-4-26
 *@blog http://blog.92coding.com
 */

public class UpdateManager
{
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
	//HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private PieProgress mProgress;
	
	private ProgressBar Progress;
	
	private boolean isupdata;
	
	private final String JsonString = "";
	
	private final String ApkString = "";
	
	//private Dialog mDownloadDialog;

	@SuppressLint("HandlerLeak") 
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				Progress.setProgress(progress/360*100);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				cancelUpdate = false;
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context)
	{
		this.mContext = context;
	}

	public void setprogress(PieProgress mProgress){		
		this.mProgress = mProgress;
	}
	
	public void setprogress_one(ProgressBar Progress){		
		this.Progress = Progress;
	}
	
	public int getprogressparameter(){	
		return progress;
	}
	
	public void setprogressparameter(boolean cancelUpdate){
		this.cancelUpdate = cancelUpdate;
	}
	
	/**
	 * 检测软件更新
	 */
	public boolean checkUpdate()
	{
		isupdata = false;
		isUpdate();
		return isupdata;
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private void isUpdate()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int versionCode = getVersionCode(mContext);
				Log.i("VersionCode", "VersionCode = "+versionCode);
				HttpGet httpRequest = new HttpGet(JsonString);//下载json包路劲
				//取得HttpClient 对象  
		        HttpClient httpclient = new DefaultHttpClient();  
		       //请求httpClient ，取得HttpRestponse  
		        HttpResponse httpResponse;
		        
		        StringBuilder builder = new StringBuilder();  
		        
		        JSONArray jsonArray = null; 
		        
				try {
					httpResponse = httpclient.execute(httpRequest);
					 if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
						 
						 //BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));  
				        	//取得相关信息 取得HttpEntiy  
				            HttpEntity httpEntity = httpResponse.getEntity(); 
				            //获得一个输入流  
				            InputStream is = httpEntity.getContent(); 
				            System.out.println(is.available());
				           // bitmap = BitmapFactory.decodeStream(is);
				            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				            for (String s = reader.readLine(); s != null; s = reader.readLine()) {  
				            	 builder.append(s);
				            }
				            jsonArray = new JSONArray(builder.toString());  
				            if (jsonArray.length() > 0) { 
				            	JSONObject jsonObject = jsonArray.getJSONObject(0);  
				            	if (Integer.parseInt(jsonObject.getString("verCode")) > versionCode){
				            		isupdata = true;
				            	}else{
				            		isupdata = false;
				            	}
				            }
				            is.close();
				            //return isupdate;
				       }
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();      		
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context)
	{
		int versionCode = 0;
		try
		{
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo("com.gizwits.powersocket", 0).versionCode;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 显示软件下载对话框
	 */
	public void showDownloadDialog()
	{
		cancelUpdate = true;
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk()
	{
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 *@date 2012-4-26
	 *@blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread
	{
		@Override
		public void run()
		{			
			HttpClient client = new DefaultHttpClient();  
			HttpGet get = new HttpGet(ApkString);//下载apk路劲
			HttpResponse response;  
			try {
				response = client.execute(get);
				HttpEntity entity = response.getEntity();
				long length = entity.getContentLength();  
				InputStream is = entity.getContent();
				FileOutputStream fileOutputStream = null; 
				
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
					String sdpath = Environment.getExternalStorageDirectory() + "/";
					mSavePath = sdpath + "download";
					
					if (is != null) { 
						File apkFile = new File(mSavePath, getAppName(mContext));										
						fileOutputStream = new FileOutputStream(apkFile);						
						byte[] buf = new byte[1024]; 
						int ch = -1;
						int count = 0;
						//int tmp;
						 while (cancelUpdate) {
							 if ((ch = is.read(buf)) != -1){
								 
								 fileOutputStream.write(buf, 0, ch);
								 count += ch;
								 if (length > 0) { 
									 progress = (int) (count / length * 360);
									 mHandler.sendEmptyMessage(DOWNLOAD);
									// Log.i("cmd", ""+tmp+"%");
			                     } 
							 }else{
								 
								 fileOutputStream.flush(); 
								 if (fileOutputStream != null) {  
					                    fileOutputStream.close();  
					             }  
								 mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
								 break;
							 }
						}
							 
					 
					}
					
					//installApk();
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk()
	{
		File apkfile = new File(mSavePath, getAppName(mContext));
		if (!apkfile.exists())
		{
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
	
    public static String getAppName(Context context) {  
        String verName = context.getResources()  
        .getText(R.string.app_name).toString();  
        return verName;  
}  
	
}
