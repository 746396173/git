package com.zowee.kefr.activity;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.gizwits.framework.adapter.MenuDeviceAdapter;
import com.gizwits.framework.config.Configs;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.powersocket.R;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.zowee.kefr.Base64;

public class MusicManageActivity extends BaseActivity implements OnClickListener {
	
	private TextView r_ssid_name,r_ssid,dev_ssid_name,dev_ssid,version_info, mac_info, pro_dev_id;
	private ProgressDialog progressDialogRefreshing;
	private ImageView ivBack;
	//private MenuDeviceAdapter mAdapter;
	private boolean isclick = false;
	//private ListView lvDevice;
	private Dialog mDisconnectDialog;
	private String strSsid;
	private Button reboot,reset;
	private int resetint, resartint;
	private TextView dev_name;
	private ImageView press_id;
	
	private ImageView r_sign;
	
	private ImageView r_lock;
	
	private ImageView dev_lock;
	
	/** 确定是否解绑的对话框 */
	private Dialog RestartDialog,ResetDialog;
	
	private RelativeLayout music_ssid;
	
	private String ssid_pw;
	
	private final String pw= "&pw=";
	
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
		
		setContentView(R.layout.musicmanageactivity);
		
		r_ssid_name = (TextView) findViewById(R.id.r_ssid_name);
		dev_name    = (TextView) findViewById(R.id.dev_name);
		r_ssid = (TextView) findViewById(R.id.r_ssid);
		dev_ssid_name    = (TextView) findViewById(R.id.dev_ssid_name);
		dev_ssid     = (TextView) findViewById(R.id.dev_ssid);
		mac_info     = (TextView) findViewById(R.id.mac_info);
		version_info = (TextView) findViewById(R.id.version_info);
		ivBack       =(ImageView) findViewById(R.id.ivLogout);
		reboot       = (Button) findViewById(R.id.reboot);
		reset        = (Button) findViewById(R.id.reset);
		pro_dev_id   = (TextView) findViewById(R.id.pro_dev_id);
		music_ssid   = (RelativeLayout) findViewById(R.id.music_ssid);
		press_id     = (ImageView) findViewById(R.id.press_id);

		r_sign    = (ImageView) findViewById(R.id.r_sign);
		r_lock    = (ImageView) findViewById(R.id.r_lock);
		dev_lock  = (ImageView) findViewById(R.id.dev_lock);
		
		reset.setOnClickListener(this);		
		reboot.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		music_ssid.setOnClickListener(this);
		
		if (Configs.PRODUCT_KEY[1].equals(mXpgWifiDevice.getProductKey())){
			pro_dev_id.setText("SF610");
			dev_name.setText("音乐魔饼");
			press_id.setVisibility(View.INVISIBLE);
			
		}else if (Configs.PRODUCT_KEY[3].equals(mXpgWifiDevice.getProductKey())){				
			pro_dev_id.setText("SL611");
			dev_name.setText("WiFi放大器");
			r_sign.setVisibility(View.VISIBLE);
			r_lock.setVisibility(View.VISIBLE);
			dev_lock.setVisibility(View.VISIBLE);
			press_id.setVisibility(View.VISIBLE);
		}else if (Configs.PRODUCT_KEY[4].equals(mXpgWifiDevice.getProductKey())){
			pro_dev_id.setText("SL610");
			dev_name.setText("WiFi放大器");
			r_sign.setVisibility(View.VISIBLE);
			r_lock.setVisibility(View.VISIBLE);
			dev_lock.setVisibility(View.VISIBLE);
			press_id.setVisibility(View.VISIBLE);
		}
		statuMap = new ConcurrentHashMap<String, Object>();
		//mAdapter = new MenuDeviceAdapter(this, bindlist);
		
		strSsid = NetworkUtils.getCurentWifiSSID(MusicManageActivity.this);
		SetRounterSsid(strSsid);
		
		RestartDialog = DialogManager.getResetDialog(this, this);		
		ResetDialog   = DialogManager.getRestartDialog(this, this);
		
		progressDialogRefreshing = new ProgressDialog(MusicManageActivity.this);
		progressDialogRefreshing.setMessage("正在更新状态,请稍后。");
		progressDialogRefreshing.setCancelable(false);
		
		mDisconnectDialog = DialogManager.getDisconnectDialog(this, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogManager.dismissDialog(MusicManageActivity.this, mDisconnectDialog);
				Intent intent = new Intent(MusicManageActivity.this, DeviceListActivity.class);
				startActivity(intent);
				finish();
	
			}
		});
		
		//refreshMenu();
		refreshMainControl();

	}
	
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			//处理收到的数据
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
						
						String sign = (String) statuMap.get(JsonKeys.SIGNAL);
						if (!StringUtils.isEmpty(sign)){
							SetRounterSign(Integer.parseInt(sign));
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
						DialogManager.dismissDialog(MusicManageActivity.this, progressDialogRefreshing);
						
					}
					break;
				case GET_STATUE:
					mCenter.cGetStatus(mXpgWifiDevice);
					break;
				case LOGIN_FAIL:
				case DISCONNECTED:
				case GET_STATUE_TIMEOUT:
					//回到设备例表
					
					DialogManager.dismissDialog(MusicManageActivity.this, progressDialogRefreshing);
					DialogManager.showDialog(MusicManageActivity.this, mDisconnectDialog);
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
						Toast.makeText(MusicManageActivity.this, "设备开始重启", Toast.LENGTH_SHORT).show();
					}else {
						resetint = 0;
						Toast.makeText(MusicManageActivity.this, "设备开始恢复原厂", Toast.LENGTH_SHORT).show();
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
	

	private void SetProduceName(String info){
		//r_ssid.setText(info);
		dev_ssid.setText(info);
	}
	private void SetVersionInfo(String info){
		version_info.setText(info);
	}
	
	private void SetRounterSign(int info){
		if (info == 0){
			r_sign.setImageResource(R.drawable.sign0);
		}else if (info == 1){
			r_sign.setImageResource(R.drawable.sign1);
		}else if (info == 2){
			r_sign.setImageResource(R.drawable.sign2);
		}else if (info == 3){
			r_sign.setImageResource(R.drawable.sign3);
		}else if (info == 4){
			r_sign.setImageResource(R.drawable.sign4);
		}
	}
	//主路由ssid+pwd;
	private void SetRounterSsid(String info){
		String tmp = "";
		//r_ssid.setText(info);		
		if (info.contains(pw)){
			r_ssid.setText(getParamFomeUrl_ssid(info, pw));	
			tmp = getParamFomeUrl_pw(info, pw);
			if (tmp.substring(0, 1).equals("1")){
				dev_lock.setImageResource(R.drawable.replock);
			}
		}else{
			r_ssid.setText(info);
		}
	}
	//设备ssid+pwd;
	private void SetDevSsid(String info){		
		String tmp = "";
		if (info.contains(pw)){			
			dev_ssid.setText(getParamFomeUrl_ssid(info, pw));
			tmp = getParamFomeUrl_pw(info, pw).trim();
			if (tmp.length() > 0){
				r_lock.setImageResource(R.drawable.replock);
			}
		}else{
			dev_ssid.setText(info);
		}
		ssid_pw = info; 
	}
	
	private String getParamFomeUrl_ssid(String url, String param) {
		String product_key = "";
		String subString = url;
		int endindex = subString.indexOf(param);
		if (endindex == -1) {
			product_key = subString;
		} else {
			product_key = subString.substring(0, endindex);
		}
		return product_key;
	}
	
	private String getParamFomeUrl_pw(String url, String param) {
			String product_key = "";
			int startindx = url.indexOf(param);
			startindx += 4;		
			product_key = url.substring(startindx);
			return product_key;
	}
	
	 
	private void SetDevMac(String info){
		
		mac_info.setText(info);
	}
	
	private void refreshMainControl() {
		
		mXpgWifiDevice.setListener(deviceListener);
		SetDevMac(mXpgWifiDevice.getMacAddress());
		DialogManager.showDialog(this, progressDialogRefreshing);
		handler.sendEmptyMessageDelayed(handler_key.GET_STATUE_TIMEOUT.ordinal(), 6000);
		handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
	}
	
	
	
	@Override
	protected void didDisconnected(XPGWifiDevice device) {
		// TODO Auto-generated method stub
		//断开
		
		if (!device.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid()))
			return;
		handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
	}
	
	@Override
	protected void didReceiveData(XPGWifiDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		// TODO Auto-generated method stub
		Log.i("didReceiveData", "did="+device.getDid());
		//收到数据
		if(!device.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid()))
			return;
		deviceDataMap = dataMap;
		handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
	}
	
	@Override
	protected void didLogin(XPGWifiDevice device, int result) {
		// TODO Auto-generated method stub
		//登录
		if (result == 0) {
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		} else {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}
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
	
	/**
	private void refreshMenu() {
		initBindList();//获得绑定列表
		mAdapter.setChoosedPos(-1);//没有正确的匹配设备
		for (int i = 0; i < bindlist.size(); i++) {
			if (bindlist.get(i).getDid()
					.equalsIgnoreCase(mXpgWifiDevice.getDid()))//当前设备(不考虑大小写)比较
				mAdapter.setChoosedPos(i);//当前匹配设备就是正要进行操作的设备
		}
		
		//当前绑定列表没有当前操作设备
		if(mAdapter.getChoosedPos()==-1){
			mAdapter.setChoosedPos(0);
			mXpgWifiDevice=mAdapter.getItem(0);
		}
		
		mAdapter.notifyDataSetChanged();
		
	}
	*/
	
	private void DisconnectOtherDevice() {
		for (XPGWifiDevice theDevice : bindlist) {
			if (theDevice.isConnected()
					&& !theDevice.getDid().equalsIgnoreCase(
							mXpgWifiDevice.getDid()))
			mCenter.cDisconnect(theDevice);//断开连接.
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//refreshMenu();
		refreshMainControl();
	}
	
	private void rebootdialog(){
		
		Dialog di = new AlertDialog.Builder(this)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				//发数据给设备，返回到设备例表
				mCenter.cRestart(mXpgWifiDevice, 1);
				isclick = true;
				//DialogManager.showDialog(this, progressDialogRefreshing);
				handler.sendEmptyMessage(handler_key.RESET.ordinal());
			}
		}).setNegativeButton("取消",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		}).setMessage("您确认要将设备重启吗？").show();
	}
	
	private void resetdialog(){
		
		Dialog di = new AlertDialog.Builder(this)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				//发数据给设备,返回设备例表
				mCenter.cReset(mXpgWifiDevice, 1);
				isclick = true;
				handler.sendEmptyMessage(handler_key.RESET.ordinal());
			}
		}).setNegativeButton("取消",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		}).setMessage("恢复原厂设置后设备将解除绑定，解除绑定后要重新扫描二维码配置，才能绑定设备。您确认要将设备恢复原厂设置吗？").show();
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
			case R.id.music_ssid:
				if (Configs.PRODUCT_KEY[3].equals(mXpgWifiDevice.getProductKey()) || 
						Configs.PRODUCT_KEY[4].equals(mXpgWifiDevice.getProductKey())){					
					Intent intent = new Intent(MusicManageActivity.this,
							DeviceSsidManageActivity.class);
					//Log.i("getIntent", ""+ssid_pw);
					intent.putExtra("DEVSSID&PW", ssid_pw);
					startActivity(intent);
				}
				//IntentUtils.getInstance().startActivity(MusicManageActivity.this, DeviceSsidManageActivity.class);
				break;
		}
	}
	
}
