package com.zowee.kefr.activity;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.powersocket.R;
import com.xpg.common.useful.StringUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.zowee.kefr.Base64;


//音乐魔饼

public class MusicMagicActivity extends BaseActivity implements OnClickListener{
	
	//返回图标
	private ImageView ivLogout;
	
	//设备标题
	private TextView dev_name;
	
	//设备型号
	private TextView pro_dev_id;
	//主路由SSID
	private TextView r_ssid;
	
	//设备SSID
	private TextView dev_ssid;
	
	//设备MAC地址
	private TextView mac_info;
	
	//固件版本
	private TextView version_info;
	
	//喜马拉雅
	private RelativeLayout ximalaya_info;
	
	private Button reboot;
	
	private Button reset;
	
	/** 确定是否解绑的对话框 */
	private Dialog RestartDialog,ResetDialog;
	
	private ProgressDialog progressDialogRefreshing;
	
	private Dialog mDisconnectDialog;
	
	private int resetint, resartint;
	
	private boolean isclick = false;
	
	private enum handler_key {

		UPDATE_UI,

		/** 设备断开连接 */
		DISCONNECTED,

		/** 接收到设备的数据 */
		RECEIVED,

		/** 获取设备状态 */
		GET_STATUE,

		/** 获取设备状态超时 */
		GET_STATUE_TIMEOUT,

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
		//开始SSID
		START,
		
		RESET,
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.musicmagicactivity);
		
		ivLogout   = (ImageView) findViewById(R.id.ivLogout);
		dev_name   = (TextView) findViewById(R.id.dev_name);
		pro_dev_id = (TextView) findViewById(R.id.pro_dev_id);
		r_ssid     = (TextView) findViewById(R.id.r_ssid);
		dev_ssid   = (TextView) findViewById(R.id.dev_ssid);
		mac_info   = (TextView) findViewById(R.id.mac_info);
		version_info = (TextView) findViewById(R.id.version_info);
		ximalaya_info = (RelativeLayout) findViewById(R.id.ximalaya_info);
		reboot      = (Button) findViewById(R.id.reboot);
		reset       = (Button) findViewById(R.id.reset);
		
		ivLogout.setOnClickListener(this);
		ximalaya_info.setOnClickListener(this);
		reboot.setOnClickListener(this);
		reset.setOnClickListener(this);
		
		//设置
		pro_dev_id.setText("SF610");
		dev_name.setText("音乐魔饼");
		
		statuMap = new ConcurrentHashMap<String, Object>();
		RestartDialog = DialogManager.getResetDialog(this, this);		
		ResetDialog   = DialogManager.getRestartDialog(this, this);
		
		progressDialogRefreshing = new ProgressDialog(MusicMagicActivity.this);
		progressDialogRefreshing.setMessage("正在更新状态,请稍后。");
		progressDialogRefreshing.setCancelable(false);
		
		mDisconnectDialog = DialogManager.getDisconnectDialog(this, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogManager.dismissDialog(MusicMagicActivity.this, mDisconnectDialog);
				Intent intent = new Intent(MusicMagicActivity.this, DeviceListActivity.class);
				startActivity(intent);
				finish();
	
			}
		});
		
		refreshMainControl();	
	}
	
	@SuppressLint("HandlerLeak") 
	Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			
			handler_key key = handler_key.values()[msg.what];
			
			switch (key) {
			case RECEIVED:
				for (String myKey : deviceDataMap.keySet()) {
					Log.e("Map",
							"key=" + myKey + ",value="
									+ deviceDataMap.get(myKey));
				}
				if (deviceDataMap.get("data") != null) {
					try {
						inputDataToMaps(statuMap, (String) deviceDataMap.get("data"));
						handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
				break;
			case UPDATE_UI:
				//handler.removeMessages(handler_key.GET_STATUE_TIMEOUT.ordinal());
				if (statuMap != null && statuMap.size() > 0) {
					
					//处理数据
					String Dev_SSID = (String) statuMap.get(JsonKeys.DEV_SSID);
					
					if (!StringUtils.isEmpty(Dev_SSID)){
						//SetRounterSsid(strSsid);
						try {
							SetDevSsid(new String (Base64.decode(Dev_SSID)));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
										
					String R_Ssid = (String) statuMap.get(JsonKeys.WIFI_SSID);
					if (!StringUtils.isEmpty(R_Ssid)){
						
						try {
							SetRounterSsid(new String (Base64.decode(R_Ssid)));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					String FirmwareVeerion = (String) statuMap.get(JsonKeys.FIRMWAREVERION);
					if (!StringUtils.isEmpty(FirmwareVeerion)){
						
						try {
							SetVersionInfo(new String (Base64.decode(FirmwareVeerion)));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					int reset = 0,resart = 0;
					//重启
					String Reset = (String) statuMap.get(JsonKeys.RESET);
					if (!StringUtils.isEmpty(Reset)){
						reset = Integer.parseInt(Reset);							
					}
					
					Log.i("handler", "resetint = "+resetint);
					String Restart = (String) statuMap.get(JsonKeys.RESTART);
					if (!StringUtils.isEmpty(Restart)){							
						resart = Integer.parseInt(Restart);	
					}												
					
					if (reset == 1 ||  resart == 1){
						if (reset == 1){
							resetint = 1;
						}else{
							resartint = 1;
						}
						handler.sendEmptyMessage(handler_key.START.ordinal());
					}
					handler.removeMessages(handler_key.GET_STATUE_TIMEOUT.ordinal());
					DialogManager.dismissDialog(MusicMagicActivity.this, progressDialogRefreshing);
					
				}
				break;
			case GET_STATUE:
				mCenter.cGetStatus(mXpgWifiDevice);
				break;
			case LOGIN_FAIL:
			case DISCONNECTED:
			case GET_STATUE_TIMEOUT:
				//回到设备例表
				
				DialogManager.dismissDialog(MusicMagicActivity.this, progressDialogRefreshing);
				DialogManager.showDialog(MusicMagicActivity.this, mDisconnectDialog);
				//IntentUtils.getInstance().startActivity(MusicManageActivity.this,
						//DeviceListActivity.class);
				//finish();
				break;
			case LOGIN_SUCCESS:
				//handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
				//refreshMainControl();
				break;
			case START:
				
				if ((resartint == 0 && resetint == 0) || isclick == false)
					break;
				if (resartint == 1 ){
					resartint = 0;					
					Toast.makeText(MusicMagicActivity.this, "设备开始重启", Toast.LENGTH_SHORT).show();
				}else {
					resetint = 0;
					Toast.makeText(MusicMagicActivity.this, "设备开始恢复原厂", Toast.LENGTH_SHORT).show();
				}
				isclick = false;
				//IntentUtils.getInstance().startActivity(MusicManageActivity.this,
						//DeviceListActivity.class);
				//finish();
				refreshMainControl();
				break;
			case RESET:
				refreshMainControl();
				break;
		default:
			break;
		}
	}
		
	};

	private void SetDevSsid(String info){
		dev_ssid.setText(info);
	}
	//主路由ssid+pwd;
	private void SetRounterSsid(String info){	
		r_ssid.setText(info);
	}
		 
	private void SetDevMac(String info){		
		mac_info.setText(info);
	}
		
	private void SetVersionInfo(String info){
		version_info.setText(info);
	}
		
	private void refreshMainControl() {
			
		mXpgWifiDevice.setListener(deviceListener);
		SetDevMac(mXpgWifiDevice.getMacAddress());
		DialogManager.showDialog(this, progressDialogRefreshing);
		handler.sendEmptyMessageDelayed(handler_key.GET_STATUE_TIMEOUT.ordinal(), 5000);
		handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
	}
		
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshMainControl();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (mXpgWifiDevice != null && mXpgWifiDevice.isConnected()) {
			mCenter.cDisconnect(mXpgWifiDevice);//断开连接
			//DisconnectOtherDevice();//断开其它设备
		}
		finish();
	}
		
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.reboot:			
			//rebootdialog();
			DialogManager.showDialog(this, ResetDialog);
			break;
		case R.id.reset:						
			//resetdialog();
			DialogManager.showDialog(this, RestartDialog);				
			break;
		case R.id.ivLogout:			
			onBackPressed();
			break;			
		case R.id.reset_right_btn:
			DialogManager.dismissDialog(this, ResetDialog);
			mCenter.cReset(mXpgWifiDevice, 1);
			isclick = true;
			handler.sendEmptyMessage(handler_key.RESET.ordinal());
			break;
		case R.id.restart_right_btn:
			DialogManager.dismissDialog(this, RestartDialog);
			mCenter.cRestart(mXpgWifiDevice, 1);
			isclick = true;
			handler.sendEmptyMessage(handler_key.RESET.ordinal());
			break;
		case R.id.ximalaya_info:
			//喜马拉雅
			Intent intent = new Intent(MusicMagicActivity.this,
					XiMaLaYaActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	@Override
	protected void didDisconnected(XPGWifiDevice device) {
		// TODO Auto-generated method stub
		if (!device.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid()))
			return;
		handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
	}
	
	@Override
	protected void didReceiveData(XPGWifiDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		// TODO Auto-generated method stub
		if(!device.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid()))
			return;
		deviceDataMap = dataMap;
		handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
	}
	
	@Override
	protected void didLogin(XPGWifiDevice device, int result) {
		// TODO Auto-generated method stub
		if (result == 0) {
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		} else {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}
	}
	
	
		
}
