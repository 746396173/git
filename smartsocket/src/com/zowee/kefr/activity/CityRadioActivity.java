package com.zowee.kefr.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.powersocket.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ximalaya.sdk4j.model.Paging;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.live.Radio;
import com.ximalaya.sdk4j.model.dto.live.RadioList;
import com.xpg.common.useful.StringUtils;
import com.zowee.kefr.BitmapAndUrl;
import com.zowee.kefr.XiMaLaYaImage;

public class CityRadioActivity extends BaseActivity implements OnClickListener, OnItemClickListener{

	private ImageView ivLogout;
	
	//private TextView net_proble;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private RadioList radiolist;
	
	private List<Radio> radio = new ArrayList<Radio>();
	private List<Radio> radioSave = new ArrayList<Radio>();
	
	private final String IsState = "State";
	
	private String provinceCode;
	
	private int State;
	
	private XiMaLaYaImage mXiMaLaYaImage;
	
	protected static List<BitmapAndUrl> Bitmap_Url = new ArrayList<BitmapAndUrl>();
	
	/*************************************************/
	private ProgressDialog progressDialogRefreshing;
	
	
	
	//private BitmapAndUrl bitmapandurl;
	
	private enum handler_key {	
	
		/** 获取设备图片 */
		GET,
		
		GET_STATUE_TIMEOUT,
		
		GET_STATUE,
		
		BITMAP,

	}
	
	@SuppressLint("HandlerLeak") 
	Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			case GET_STATUE_TIMEOUT:
				DialogManager.dismissDialog(CityRadioActivity.this, progressDialogRefreshing);
				//提示网络不好
				break;
			case GET_STATUE:
				mXiMaLaYaImage.notifyDataSetChanged();
				mPullToRefreshListView.onRefreshComplete();
				DialogManager.dismissDialog(CityRadioActivity.this, progressDialogRefreshing);
				break;
			case BITMAP:
				break;
			}
		}
		
	};
	
	//lives
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cityradioactivity);
		
		init_data();
		ivLogout  = (ImageView) findViewById(R.id.ivLogout);
		
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mXiMaLaYaImage = new XiMaLaYaImage(CityRadioActivity.this, Bitmap_Url);
		mPullToRefreshListView.setAdapter(mXiMaLaYaImage);
		
		ivLogout.setOnClickListener(this);
		mPullToRefreshListView.setOnItemClickListener(this);
		progressDialogRefreshing = new ProgressDialog(CityRadioActivity.this);
		progressDialogRefreshing.setMessage("正在更新,请稍等...");
		progressDialogRefreshing.setCancelable(false);
		
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				//DialogManager.showDialog(CityRadioActivity。this, progressDialogRefreshing);
				new GetDataTask().execute();
			}
			
		});
		
		//自动刷新
		ini_radio();
		DialogManager.showDialog(this, progressDialogRefreshing);
		handler.sendEmptyMessageDelayed(handler_key.GET_STATUE_TIMEOUT.ordinal(), 2000);
		
	}
	
	private void init_data(){
		
		if (getIntent() != null) {
            if (!StringUtils.isEmpty(getIntent().getStringExtra(IsState))) {
            	String tmp = getIntent().getStringExtra(IsState);
            	//int len = tmp.length();
            	if (tmp.substring(0, 1).equals("1")){
            		State = 1;
            	}else if (tmp.substring(0, 1).equals("2")){
            		State = 2;
            	}else{
            		State = 3;
            	}
            	provinceCode = tmp.substring(1);
            //	Log.i("init_data", "init_data = "+State+"provinceCode = "+provinceCode);
            } 
		}
	}
	
	private void ini_radio(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					//radiolist.
					
					radiolist = lives.getRadioList(State, provinceCode, new Paging());
					Assert.assertTrue(radiolist != null && radiolist.getRadios() != null
							&& !radiolist.getRadios().isEmpty());
					radio = radiolist.getRadios();
					
					if (radio.size() > 0 && radio.size() != radioSave.size()){
						Bitmap_Url.clear();
						for (int i = 0; i < radio.size(); i++){
							Radio tmpradio = radio.get(i);
							//tmpradio.getCoverUrlLarge()大图
							//tmpradio.getProgramName()
							Bitmap_Url.add(
									new BitmapAndUrl(
									getImage(tmpradio.getCoverUrlSmall()),
									tmpradio.getRate24AacUrl(),
									tmpradio.getRadioPlayCount(),
									tmpradio.getUpdatedAt(),
									tmpradio.getProgramName(),
									tmpradio.getRadioName()));
							
						}
					}	
					radioSave = radio;
					handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
				} catch (XimalayaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	private Bitmap getImage(String htmlpath) throws ClientProtocolException, IOException{
		Bitmap bitmap = null;
		//HttpClient hc = new DefaultHttpClient(); 
		//HttpGet hg = new HttpGet(htmlpath);
		//HttpResponse hr = hc.execute(hg);
		//return BitmapFactory.decodeStream(hr.getEntity().getContent());
		
		//httpGet连接对象 
		HttpGet httpRequest = new HttpGet(htmlpath);
		//取得HttpClient 对象  
        HttpClient httpclient = new DefaultHttpClient();  
       //请求httpClient ，取得HttpRestponse  
        HttpResponse httpResponse = httpclient.execute(httpRequest); 
        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
        	//取得相关信息 取得HttpEntiy  
            HttpEntity httpEntity = httpResponse.getEntity(); 
            //获得一个输入流  
            InputStream is = httpEntity.getContent(); 
            System.out.println(is.available());
            bitmap = BitmapFactory.decodeStream(is);  
            is.close();            
        }
		return bitmap;
	}
	
	private void GetImageString(final String url, final String patch){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				
			}
		}).start();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.ivLogout:
			onBackPressed();
			break;
		}
	}	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//new GetDataTask().execute();
		
		//handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Bitmap_Url.clear();
		finish();
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(10000);
				//onResume();
				ini_radio();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mXiMaLaYaImage.notifyDataSetChanged();
			mPullToRefreshListView.onRefreshComplete();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.i("onItemClick", ""+arg2);
	}
	
	
}
