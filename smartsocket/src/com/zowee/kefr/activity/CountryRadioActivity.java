package com.zowee.kefr.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.powersocket.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.ximalaya.sdk4j.Lives;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.live.Province;
import com.xpg.common.useful.StringUtils;
import com.zowee.kefr.CountryRadioAdapter;
import com.zowee.kefr.ProviceNameAndCode;

@SuppressLint("HandlerLeak") 
public class CountryRadioActivity extends BaseActivity implements OnItemClickListener{

	private PullToRefreshGridView gvDetails;
	//private RefreshableListView lvDevices;
	
	//private ProgressDialog progressDialogRefreshing;
	
	private String State;
	
	private final String IsState = "State";
	
	private static List<Province> province = new ArrayList<Province>();
	
	private CountryRadioAdapter mCountryRadioAdapter;
	
	private static List<ProviceNameAndCode> prNameAndCodes = new ArrayList<ProviceNameAndCode>();
	
	private enum handler_key {	
		
		FOUND,
		
		/** 获取绑定列表 */
		GET_PROVINCE,

		/** 获取设备图片 */
		GET,
		
		GET_STATUE_TIMEOUT,
		
		GET_STATUE,

	}

	Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {
			
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			
			case GET_PROVINCE:				
				prNameAndCodes.clear();
				for (int i = 0; i < province.size(); i++){
					Province tmp = province.get(i);
					prNameAndCodes.add(new ProviceNameAndCode(tmp.getProvinceName(), tmp.getProvinceCode()));
					mCountryRadioAdapter.notifyDataSetChanged();
					gvDetails.onRefreshComplete();
				}
				break;
				//handler.removeMessages(handler_key.GET_STATUE_TIMEOUT.ordinal());
				//DialogManager.dismissDialog(CountryRadioActivity.this, progressDialogRefreshing);
				/**
			case GET_STATUE_TIMEOUT:
				DialogManager.dismissDialog(CountryRadioActivity.this, progressDialogRefreshing);
				//DialogManager.showDialog(CountryRadioActivity.this, mDisconnectDialog);
				break;
				*/
			default:
				break;
					
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.countryradioactivity);
		
		initdata();
		//initIndicator();
		//lvDevices  = (RefreshableListView) findViewById(R.id.lvDevices);
		gvDetails  = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		mCountryRadioAdapter = new CountryRadioAdapter(CountryRadioActivity.this, prNameAndCodes);
		gvDetails.setAdapter(mCountryRadioAdapter);
		
		lives = new Lives();		
		gvDetails.setOnItemClickListener(this);
		gvDetails.setOnRefreshListener(new OnRefreshListener<GridView> () {

			@Override
			public void onRefresh(PullToRefreshBase<GridView> refreshView) {
				// TODO Auto-generated method stub
				new GetDataTask().execute();
			}
			
		});
		
		//progressDialogRefreshing = new ProgressDialog(CountryRadioActivity.this);
		//progressDialogRefreshing.setMessage("正在更新状态,请稍后。");
		//progressDialogRefreshing.setCancelable(false);
				
		//RefreshData();
	}
	
	private void initdata(){
		
		if (getIntent() != null) {
            if (!StringUtils.isEmpty(getIntent().getStringExtra(IsState))) {
            	State = getIntent().getStringExtra(IsState);
            } 
		}
	}
	
	private void RefreshData(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					province.clear();
					province = lives.getProvinces();
					//Log.i("RefreshData", "RefreshData");
					/**
					if (ispreese == true){
						
						handler.sendEmptyMessage(handler_key.FOUND.ordinal());
					}else{
						handler.sendEmptyMessage(handler_key.GET_PROVINCE.ordinal());
					}
					*/
					handler.sendEmptyMessage(handler_key.GET_PROVINCE.ordinal());
					
				} catch (XimalayaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		RefreshData();
		//DialogManager.showDialog(this, progressDialogRefreshing);
		//handler.sendEmptyMessageDelayed(handler_key.GET_STATUE_TIMEOUT.ordinal(), 10000);
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
			
		Intent intent = new Intent(CountryRadioActivity.this, CityRadioActivity.class);
		intent.putExtra(IsState, ""+State+""+prNameAndCodes.get(arg2).getProviceCode().toString());//国家+code
		startActivity(intent);
	}
	
	private void initIndicator(){
		
		ILoadingLayout startLabels = gvDetails.getLoadingLayoutProxy();
		startLabels.setPullLabel("下拉");
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("放开，刷新...");// 下来达到一定距离时，显示的提示
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(2000);
				RefreshData();
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
			
			//mCountryRadioAdapter.notifyDataSetChanged();
			//gvDetails.onRefreshComplete();
		}
		
	}
}
