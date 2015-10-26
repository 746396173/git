package com.zowee.kefr.activity;

import java.util.List;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.onboarding.AirlinkActivity;
import com.gizwits.powersocket.R;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

public class ConfigDeviceNet extends BaseActivity implements OnClickListener{

	/** The tv ssid. */
	private TextView tvSsid;

	/** The et input psw. */
	private EditText etInputPsw;

	/** The tb psw flag. */
	private ToggleButton tbPswFlag;

	/** The btn next. */
	private Button btnNext;
	
	/**
     * The iv back.
     */
    private ImageView ivBack;

	/**  网络状态广播接受器. */
	ConnecteChangeBroadcast mChangeBroadcast = new ConnecteChangeBroadcast();
	
	/** The str ssid. */
	private String strSsid;
	
	/** The str psw. */
	private String strPsw;
	
	private String scanret;
	
	/** wifi */
	private byte AuthModeOpen = 0x00;
	private byte AuthModeShared = 0x01;
	private byte AuthModeAutoSwitch = 0x02;
	private byte AuthModeWPA = 0x03;
	private byte AuthModeWPAPSK = 0x04;
	private byte AuthModeWPANone = 0x05;
	private byte AuthModeWPA2 = 0x06;
	private byte AuthModeWPA2PSK = 0x07;   
	private byte AuthModeWPA1WPA2 = 0x08;
	private byte AuthModeWPA1PSKWPA2PSK = 0x09;
	
	private byte mAuthMode;

	/**
	 * 
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		/** The change wifi. */
		CHANGE_WIFI,

	}

	/** The handler. */
	Handler handler = new Handler() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			case CHANGE_WIFI:
				strSsid = NetworkUtils
						.getCurentWifiSSID(ConfigDeviceNet.this);
				//tvSsid.setText(getString(R.string.wifi_name) + strSsid);
				tvSsid.setText(strSsid);
				
				List<ScanResult> ScanResultlist = NetworkUtils.getCurrentWifiScanResult(ConfigDeviceNet.this);
				for (int i = 0, len = ScanResultlist.size(); i < len; i++){					
					ScanResult AccessPoint = ScanResultlist.get(i);	
					if (AccessPoint.SSID.equals(strSsid)){
						
						boolean WpaPsk = AccessPoint.capabilities.contains("WPA-PSK");
			        	boolean Wpa2Psk = AccessPoint.capabilities.contains("WPA2-PSK");
						boolean Wpa = AccessPoint.capabilities.contains("WPA-EAP");
			        	boolean Wpa2 = AccessPoint.capabilities.contains("WPA2-EAP");
						
						if (AccessPoint.capabilities.contains("WEP"))
						{
							//mAuthString = "OPEN-WEP";
							mAuthMode = AuthModeOpen;
							break;
						}

						if (WpaPsk && Wpa2Psk)
						{
							//mAuthString = "WPA-PSK WPA2-PSK";
							mAuthMode = AuthModeWPA1PSKWPA2PSK;
							break;
						}
						else if (Wpa2Psk)
						{
							//mAuthString = "WPA2-PSK";
							mAuthMode = AuthModeWPA2PSK;
							break;
						}
						else if (WpaPsk)
						{
							//mAuthString = "WPA-PSK";
							mAuthMode = AuthModeWPAPSK;
							break;
						}

						if (Wpa && Wpa2)
						{
							//mAuthString = "WPA-EAP WPA2-EAP";
							mAuthMode = AuthModeWPA1WPA2;
							break;
						}
						else if (Wpa2)
						{
							//mAuthString = "WPA2-EAP";
							mAuthMode = AuthModeWPA2;
							break;
						}
						else if (Wpa)
						{
							//mAuthString = "WPA-EAP";
							mAuthMode = AuthModeWPA;
							break;
						}				
						
						//mAuthString = "OPEN";
						mAuthMode = AuthModeOpen;
					}
				}
				break;

			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.aircondition.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_device_net);
		initdata();
		initViews();
		initEvents();
	}
	
	private void initdata(){
		
		if (getIntent() != null) {
            if (!StringUtils.isEmpty(getIntent().getStringExtra("ScanRet"))) {
            	scanret = getIntent().getStringExtra("ScanRet");
            } 
		}
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		tvSsid = (TextView) findViewById(R.id.tvSsid);
		etInputPsw = (EditText) findViewById(R.id.etInputPsw);
		tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
		btnNext = (Button) findViewById(R.id.btnNext);
		ivBack=(ImageView) findViewById(R.id.ivBack);
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		btnNext.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
                if (isChecked) {
                    //etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
                           // | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                	etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    //etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
                            //| InputType.TYPE_TEXT_VARIATION_PASSWORD);
                	etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
                             | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }

			}
		});
		
		 etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
                 | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNext:
			if (!NetworkUtils.isWifiConnected(this)){
				ToastUtils.showShort(this, getString(R.string.wifi_first));
				break;
			}
			
			if(StringUtils.isEmpty(strSsid)){
				ToastUtils.showShort(this, getString(R.string.wifi_first));
				break;
			}
			
			Intent intent = new Intent(ConfigDeviceNet.this,AirlinkActivity.class);
			intent.putExtra("ssid", strSsid);
			//intent.putExtra("mAuthMode", mAuthMode);
			strPsw = etInputPsw.getText().toString().trim();
			if(!StringUtils.isEmpty(strPsw)){
				intent.putExtra("psw", strPsw);
			}else{
				intent.putExtra("psw", "");
			}
			//Log.i("scanret", scanret);
			if (!StringUtils.isEmpty(scanret)){
				intent.putExtra("ScanRet", scanret);
			}else{
				intent.putExtra("ScanRet", "");
			}
			//intent.putExtra("mAuthMode", mAuthMode);
			intent.putExtra("mAuthMode", mAuthMode);
			startActivity(intent);
			finish();
			break;
		case R.id.ivBack:
			onBackPressed();
			break;
		}

	}

	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mChangeBroadcast, filter);
		if (NetworkUtils.isWifiConnected(this)) {
			handler.sendEmptyMessage(handler_key.CHANGE_WIFI.ordinal());
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	public void onPause() {
		super.onPause();
		unregisterReceiver(mChangeBroadcast);

	}

	@Override
	public void onBackPressed() {
		//startActivity(new Intent(AutoConfigActivity.this,SearchDeviceActivity.class));
		//返回用户管理
		finish();
	}

	/**
	 * 广播监听器，监听wifi连上的广播.
	 *
	 * @author Lien
	 */
	public class ConnecteChangeBroadcast extends BroadcastReceiver {

		/* (non-Javadoc)
		 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {

			boolean iswifi = NetworkUtils.isWifiConnected(context);
			Log.i("networkchange", "change" + iswifi);
			if (iswifi) {
				handler.sendEmptyMessage(handler_key.CHANGE_WIFI.ordinal());
			}
		}
	}
	
}
