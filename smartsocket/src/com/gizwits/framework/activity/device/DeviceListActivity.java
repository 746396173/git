/**
 * Project Name:XPGSdkV4AppBase
 * File Name:DeviceListActivity.java
 * Package Name:com.gizwits.framework.activity.device
 * Date:2015-1-27 14:45:18
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gizwits.framework.activity.device;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
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
import android.widget.Toast;

import com.example.qr_codescan.MipcaActivityCapture;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.account.LoginActivity;
import com.gizwits.framework.activity.account.UserManageActivity;
import com.gizwits.framework.adapter.DeviceListAdapter;
import com.gizwits.framework.adapter.MenuDeviceAdapter;
import com.gizwits.framework.config.Configs;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.utils.StringUtils;
import com.gizwits.framework.widget.SlidingMenu;
import com.gizwits.framework.widget.SlidingMenu.SlidingMenuListener;

import com.gizwits.powersocket.R;
import com.gizwits.powersocket.activity.control.MainControlActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.zowee.kefr.activity.AllDeviceManageActivity;
import com.zowee.kefr.activity.MusicMagicActivity;
import com.zowee.kefr.activity.MusicManageActivity;
import com.zowee.kefr.activity.PrivacyActivity;
import com.zowee.kefr.activity.XiMaLaYaActivity;


// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc

/**
 * ClassName: Class DeviceListActivity. <br/>
 * 设备列表，用于显示当前账号环境下的所有设备<br/>
 * date: 2014-12-09 17:27:10 <br/>
 * 
 * @author StephenC
 */
@SuppressLint({ "CutPasteId", "HandlerLeak" })
public class DeviceListActivity extends BaseActivity implements SlidingMenuListener, OnClickListener, OnItemClickListener {

	private boolean isupdate;	
	/** The Constant TAG. */
	private static final String TAG = "DeviceListActivity";

	/**
	 * The iv TopBar leftBtn.
	 */
	private ImageView ivLogout, dev_manage_Logout;

	/** The iv add. */
	private ImageView ivAdd;

	/** The tv init date. */
	//private RefreshableListView lvDevices;
	
	private PullToRefreshListView mPullRefreshListView;

	/** The device list adapter. */
	private DeviceListAdapter deviceListAdapter;

	/** The progress dialog. */
	private ProgressDialog progressDialog;

	/** The dialog. */
	private Dialog dialog;
	
	/** 登陆设备超时时间 */
	private int LoginDeviceTimeOut = 60000;	

	/** 网络状态广播接受器. */
	private ConnecteChangeBroadcast mChangeBroadcast = new ConnecteChangeBroadcast();

	/**
	 * The boolean isExit.
	 */
	@SuppressWarnings("unused")
	private boolean isExit = false;
	
	private ImageView dev_Logout;
	
	@SuppressWarnings("unused")
	private TextView user_name,device_add;
	
	@SuppressWarnings("unused")
	private ImageView device_addone;
	
	//private boolean isWiFi = false;
	private boolean isrefresh = false;
	
	private SlidingMenu mView;
	
	private TextView username_manage;
	
	private ImageView register_name;
	
	private String unbinddid, unbindpasscode;
	
	private MenuDeviceAdapter mAdapter;
	
	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		/** The login start. */
		LOGIN_START,

		/**
		 * The login success.
		 */
		LOGIN_SUCCESS,

		/**
		 * The login fail.
		 */
		LOGIN_FAIL,

		/**
		 * The login timeout.
		 */
		LOGIN_TIMEOUT,

		/** The found. */
		FOUND,

		/**
		 * Exit the app.
		 */
		EXIT,
		
		LOGIN_SWICH,
		
		BIND_SUCCESS,
		
		BIND_FAILED,

	}

	/** The handler. */
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			
			case FOUND:	
				/**
				if (isrefresh == true){
					isrefresh = false;
					lvDevices.completeRefreshing();
				}else{
					
					lvDevices.isRefreshing();
				}
				*/
				//deviceListAdapter.notifyDataSetChanged();
				// Call onRefreshComplete when the list has been refreshed.
				//mPullRefreshListView.onRefreshComplete();
				break;
			case LOGIN_SUCCESS:
				DialogManager.dismissDialog(DeviceListActivity.this, progressDialog);
				if (Configs.PRODUCT_KEY[0].equals(mXpgWifiDevice.getProductKey())){
					//智能插座
					IntentUtils.getInstance().startActivity(DeviceListActivity.this, MainControlActivity.class);
					
				}else if (Configs.PRODUCT_KEY[1].equals(mXpgWifiDevice.getProductKey()) ||
						  Configs.PRODUCT_KEY[2].equals(mXpgWifiDevice.getProductKey()) || 
						  Configs.PRODUCT_KEY[3].equals(mXpgWifiDevice.getProductKey()) ||
						  Configs.PRODUCT_KEY[4].equals(mXpgWifiDevice.getProductKey())){
					//放大器
					IntentUtils.getInstance().startActivity(DeviceListActivity.this, MusicManageActivity.class);
					
				}
				
				/**
				else if (Configs.PRODUCT_KEY[1].equals(mXpgWifiDevice.getProductKey())){
					//音乐魔饼
					IntentUtils.getInstance().startActivity(DeviceListActivity.this, MusicMagicActivity.class);
				}	
				*/		
				break;
			case LOGIN_FAIL:
				DialogManager.dismissDialog(DeviceListActivity.this, progressDialog);
				ToastUtils.showShort(DeviceListActivity.this, "连接失败");
				break;
				
			case LOGIN_TIMEOUT:
				DialogManager.dismissDialog(DeviceListActivity.this, progressDialog);
				ToastUtils.showShort(DeviceListActivity.this, "连接失败");
				break;				
			case EXIT:
				isExit = false;
				break;			
			case BIND_SUCCESS:	
				//Log.i("BIND_SUCCESS", "BIND_SUCCESS");
				CurrDeviceMac = "";
				deviceListAdapter.SetDeviceMac(CurrDeviceMac);
				getList();
				break;				
			case BIND_FAILED:			
				if (unbinddid != null && unbindpasscode != null){
					//mCenter.cBindDevice(setmanager.getUid(), setmanager.getToken(),
							//unbinddid, unbindpasscode, "");
				}
				
				break;				
			default:
				break;
		
			}
			deviceListAdapter.changeDatas(deviceslist);
		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.activity_new_main_control);
		
		initViews();
		initEvents();
		if (getIntent() != null) {
			if (getIntent().getBooleanExtra("autoLogin", false)) {
				mCenter.cLogin(setmanager.getUserName(),
						setmanager.getPassword());//帐户登录
			}
		}
		
	
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		
		if (CurrDeviceMac == null || CurrDeviceMac.equals("")){		
			CurrDeviceMac = "";
			deviceListAdapter.SetDeviceMac(CurrDeviceMac);
		}else{
			deviceListAdapter.SetDeviceMac(CurrDeviceMac);
		}
		
		deviceListAdapter.changeDatas(new ArrayList<XPGWifiDevice>());
		
		if (getIntent().getBooleanExtra("isbind", false)) {

			mCenter.cBindDevice(setmanager.getUid(), setmanager.getToken(),
					getIntent().getStringExtra("did"), getIntent()
							.getStringExtra("passcode"), "");
		} else {
			getList();
		}
		
		refreshMenu();
		
		/**
		if (updatemanage.checkUpdate()){
			isupdate = true;
		}else{
			isupdate = false;			
		}
		*/
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mChangeBroadcast, filter);

	}
	
	private void refreshMenu() {
		initBindList();//获得绑定列表
		mAdapter.notifyDataSetChanged();
		
	}
		

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mChangeBroadcast);
	}

	/**
	 * Inits the views.
	 */
	
	
	private void initViews() {
		
		mView = (SlidingMenu) findViewById(R.id.new_main_layout);
		
		register_name =   (ImageView) findViewById(R.id.user_register_name);	//dev_add
		
		ivLogout = (ImageView) findViewById(R.id.ivLogout);//ivLogout
		
		dev_manage_Logout = (ImageView) findViewById(R.id.dev_Logout);//dev_Logout
		
		ivAdd = (ImageView) findViewById(R.id.ivadd_dev);//ivAdd
		
		dev_Logout  = (ImageView) findViewById(R.id.dev_Logout);
		//mPieProgress = (PieProgress) findViewById(R.id.pie_progress);
		//update       = (ImageView) findViewById(R.id.update);
		
		//lvDevices = (RefreshableListView) findViewById(R.id.lvDevices);//lvDevices
		mPullRefreshListView  = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		user_name = (TextView) findViewById(R.id.user_name);	//user_name
		username_manage = (TextView) findViewById(R.id.username_manage);//username_manage
		
		if (StringUtils.isEmpty(setmanager.getToken())){
			
			username_manage.setText("请登录");
			user_name.setText("请登录");
			register_name.setImageResource(R.drawable.login_03);
			dev_manage_Logout.setImageResource(R.drawable.login_03);
			
		}else{
			username_manage.setText(setmanager.getUserName());
			user_name.setText(setmanager.getUserName()+"  欢迎您！");
			register_name.setImageResource(R.drawable.login_05);
			dev_manage_Logout.setImageResource(R.drawable.login_05);
		}
		
		deviceListAdapter = new DeviceListAdapter(this, deviceslist);
		
		
		deviceListAdapter.SetOnBindDeviceItemClickListener(new DeviceListAdapter.OnBindDeviceClickListener() {
			
			@Override
			public void OnBindDeviceItemClick(DeviceListAdapter adapter,
					XPGWifiDevice tempDevice) {
				// TODO Auto-generated method stub
				
				if (CurrDeviceMac != null && tempDevice.getMacAddress().equals(CurrDeviceMac)){
					//CurrDeviceMac = null;
					unbinddid = tempDevice.getDid();
					unbindpasscode = tempDevice.getPasscode();
					mCenter.cBindDevice(setmanager.getUid(), setmanager.getToken(),
							tempDevice.getDid(), tempDevice.getPasscode(), "");
				}
			}
		});
		
		deviceListAdapter.SetOnLanDeviceItemClick(new DeviceListAdapter.OnLanDeviceClickListener() {
			
			@Override
			public void OnLanDeviceItemClick(DeviceListAdapter adapter,
					XPGWifiDevice tempDevice) {
				// TODO Auto-generated method stub				
				getList();//自动刷新
				refreshMenu();
			}
		});
		
		mAdapter = new MenuDeviceAdapter(this, bindlist);
		//lvDevices.setAdapter(deviceListAdapter);
		mPullRefreshListView.setAdapter(deviceListAdapter);
		
		/**
		lvDevices.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(RefreshableListView listView) {
				isrefresh = true;
				Log.i("onRefresh", "onRefresh");
				getList();//自动刷新

			}
		});
		*/
		
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				new GetDataTask().execute();
				
			}
			
		});
		
		/**
		updatemanage   = new UpdateManager(this);
		if (updatemanage.checkUpdate()){
			isupdate = true;
		}
		*/
		
		/**
		updatemanage   = new UpdateManager(this, mPieProgress);
		if (updatemanage.checkUpdate()){
			isupdate = true;
			//update.setImageResource(R.drawable.update_new);
			//update.setVisibility(View.VISIBLE);
			mPieProgress.setVisibility(View.GONE);
		}else{
			isupdate = false;
			//update.setVisibility(View.VISIBLE);
			mPieProgress.setVisibility(View.GONE);
		}
		*/
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("连接中，请稍候。");
		progressDialog.setCancelable(false);
		
		mView.setSlidingMenuListener(this);

	}
	

	/**
	 * Inits the events.
	 */
	
	
	private void initEvents() {
		
		ivLogout.setOnClickListener(this);
		dev_manage_Logout.setOnClickListener(this);
		ivAdd.setOnClickListener(this);
		//lvDevices.setOnItemClickListener(this);
		mPullRefreshListView.setOnItemClickListener(this);
		user_name.setOnClickListener(this);
		register_name.setOnClickListener(this);
		dev_Logout.setOnClickListener(this);	
		
	}
	
	
	public void onClickSlipBar(View V){
		
		if (!mView.isOpen())
			return;
		switch (V.getId()) {
		
		case R.id.rlDevice:
			if (StringUtils.isEmpty(setmanager.getToken())){
				Toast.makeText(DeviceListActivity.this, "请登录", Toast.LENGTH_SHORT).show();
			}else{
				
				IntentUtils.getInstance().startActivity(DeviceListActivity.this, AllDeviceManageActivity.class);
			}
			break;
		case R.id.rlAccount:
			if (StringUtils.isEmpty(setmanager.getToken())){
				Toast.makeText(DeviceListActivity.this, "请登录", Toast.LENGTH_SHORT).show();
			}else{
				IntentUtils.getInstance().startActivity(DeviceListActivity.this, UserManageActivity.class);
			}
			break;
		case R.id.privacy:
			if (StringUtils.isEmpty(setmanager.getToken())){
				Toast.makeText(DeviceListActivity.this, "请登录", Toast.LENGTH_SHORT).show();
			}else{
				IntentUtils.getInstance().startActivity(DeviceListActivity.this, PrivacyActivity.class);
			}
			break;
		case R.id.updated:
			if (StringUtils.isEmpty(setmanager.getToken())){
				Toast.makeText(DeviceListActivity.this, "请登录", Toast.LENGTH_SHORT).show();
			}else{
				
				//if (isupdate == true){
					//isupdate = false;
					//updatemanage.showDownloadDialog();
					//IntentUtils.getInstance().startActivity(DeviceListActivity.this, UpdateActivity.class);
					//IntentUtils.getInstance().startActivity(DeviceListActivity.this, XiMaLaYaActivity.class);
				//}
			}
			break;
		}
		
	}

	 
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	
	
	@Override
	public void onClick(View v) {
				
		switch (v.getId()) {

		case R.id.ivLogout:			
			mView.toggle();
			break;
		case R.id.dev_Logout:	
		case R.id.user_register_name:
		case R.id.user_name:
			if (!StringUtils.isEmpty(setmanager.getToken())){
				//注销
				if (dialog == null) {
					dialog = DialogManager.getLogoutDialog(DeviceListActivity.this, new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							setmanager.setToken("");
							setmanager.setUserName("");
							setmanager.setPassword("");
							setmanager.setUid("");
							
							DialogManager.dismissDialog(
									DeviceListActivity.this, dialog);
							ToastUtils.showShort(DeviceListActivity.this,
									"注销成功");
							IntentUtils.getInstance().startActivity(
									DeviceListActivity.this,
									LoginActivity.class);
							finish();
						}
					});
				}
				DialogManager.showDialog(DeviceListActivity.this, dialog);
			}else{
				//登录
				setmanager.setToken("");
				setmanager.setUserName("");
				setmanager.setPassword("");
				setmanager.setUid("");
				IntentUtils.getInstance().startActivity(
						DeviceListActivity.this,
						LoginActivity.class);
				finish();
			}
			break;
		case R.id.ivadd_dev:
			
			if (StringUtils.isEmpty(setmanager.getToken())){
				Toast.makeText(DeviceListActivity.this, "请登录", Toast.LENGTH_SHORT).show();
			}else{
				IntentUtils.getInstance().startActivity(
						DeviceListActivity.this,
						MipcaActivityCapture.class);
			}
			break;
	
		}

	}
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//Log.i("onItemClick", "onItemClick = "+position);
			
		XPGWifiDevice tempDevice = deviceListAdapter
				.getDeviceByPosition(position-1);
		if (tempDevice == null) {
			return;
		}
		
		if (tempDevice.isLAN()) {
			return;	
		
		} else {
			if (!tempDevice.isOnline()) {
				// TODO 离线
				Log.i(TAG,
						"离线设备:mac=" + tempDevice.getPasscode() + ";ip="
								+ tempDevice.getIPAddress() + ";did="
								+ tempDevice.getDid() + ";passcode="
								+ tempDevice.getPasscode());
			} else {
				// TODO 登陆设备
				Log.i(TAG,
						"远程登陆设备:mac=" + tempDevice.getPasscode() + ";ip="
								+ tempDevice.getIPAddress() + ";did="
								+ tempDevice.getDid() + ";passcode="
								+ tempDevice.getPasscode());
				loginDevice(tempDevice);
			}
			
		}
		

	}
	
	

	/**
	 * 登陆设备(先绑定设备)• 登录设备之前，需要先获取到XPGWifiDevice类，且该设备是在线的
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 */
	
	
	private void loginDevice(XPGWifiDevice xpgWifiDevice) {
		DialogManager.showDialog(DeviceListActivity.this, progressDialog);
		mXpgWifiDevice = xpgWifiDevice;
		mXpgWifiDevice.setListener(deviceListener);
		if(mXpgWifiDevice.isConnected()){
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		}else{
			handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(), LoginDeviceTimeOut);//在指定的时间后发送消息，指定的时间以ms为单位。
			mXpgWifiDevice.login(setmanager.getUid(), setmanager.getToken());//设备登录
			
		}
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didLogin(com.xtremeprog.
	 * xpgconnect.XPGWifiDevice, int)
	 */
	
	@Override
	protected void didLogin(XPGWifiDevice device, int result) {
		handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
		if (result == 0) {
			mXpgWifiDevice = device;
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		} else {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}

	}
	

	/**
	 * 处理获取设备列表动作
	 * 
	 * @return the list
	 */
	private void getList() {
		
		if (!StringUtils.isEmpty(setmanager.getToken())){
			String uid = setmanager.getUid();
			String token = setmanager.getToken();
			mCenter.cGetBoundDevices(uid, token);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didDiscovered(int,
	 * java.util.List)
	 */
	

	@Override
	protected void didDiscovered(int error, List<XPGWifiDevice> deviceList) {
		deviceslist = deviceList;
		//Log.i("BIND_SUCCESS", ""+deviceslist.size());
		handler.sendEmptyMessage(handler_key.FOUND.ordinal());
	}

	@Override
	protected void didDisconnected(XPGWifiDevice device) {
		if (mXpgWifiDevice.getDid().equals(device.getDid())) {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didUserLogin(int,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void didUserLogin(int error, String errorMessage, String uid,
			String token) {
		if (!uid.isEmpty() && !token.isEmpty()) {// 登陆成功
			setmanager.setUid(uid);
			setmanager.setToken(token);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
			
			exit();
	}
	
	/**
	 * 广播监听器，监听wifi连上的广播.
	 * 
	 * @author Lien
	 */
	public class ConnecteChangeBroadcast extends BroadcastReceiver {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.content.BroadcastReceiver#onReceive(android.content.Context,
		 * android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent
					.getAction())) {			
					getList();
			}
		}
	}

	@Override
	public void OpenFinish() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void CloseFinish() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	protected void didBindDevice(int error, String errorMessage, String did) {
		// TODO Auto-generated method stub	
				
		if (error == 0) {
			handler.sendEmptyMessage(handler_key.BIND_SUCCESS.ordinal());
		}else{
			handler.sendEmptyMessage(handler_key.BIND_FAILED.ordinal());
		}
		
	}	
	
	private class GetDataTask extends AsyncTask<XPGWifiDevice, XPGWifiDevice, XPGWifiDevice>{

		@Override
		protected XPGWifiDevice doInBackground(XPGWifiDevice... arg0) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
				getList();//自动刷新
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(XPGWifiDevice result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
		}
	}
		
	
}
