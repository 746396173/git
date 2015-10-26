package com.zowee.kefr.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.utils.StringUtils;
import com.gizwits.powersocket.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.zowee.kefr.WifiList;
import com.zowee.kefr.WifiListAdapter;

public class WifiManageActivity extends BaseActivity implements OnClickListener{
	private EditText wifiPassword;
	private ToggleButton wifiPswFlag;
	private ProgressDialog progressDialogRefreshing;
	private String isssid,ispasswod;
	private String save_ssid;
	private Button router_opr;
	private ImageView ivBack;
	private Spinner wifi_list;
	//private TextView wifi_list_frist;
	
	private List<WifiList> dicts = new ArrayList<WifiList>();
	
	private WifiListAdapter wifiadapter;
	//AlertDialog.Builder builder;
	
	private enum handler_key {
		//更新UI
		UPDATE_UI,
		//收到数据
		RECEIVED,
		//断开	
		DISCONNECTED,
		/**
		 * The login success.
		 */
		SUCCESS,
		/**
		 * The login timeout.
		 */
		TIMEOUT,
		START,
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifimanageactivity);
		
		//router_ssid   = (EditText) findViewById(R.id.router_ssid);
		wifiPassword  = (EditText) findViewById(R.id.wifiPassword);
		wifiPswFlag   = (ToggleButton) findViewById(R.id.wifiPswFlag);
		router_opr    = (Button) findViewById(R.id.router_opr);
		ivBack        = (ImageView) findViewById(R.id.ivBack);
		wifi_list     = (Spinner) findViewById(R.id.wifi_list);
		//wifi_list_frist = 
		router_opr.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		
		dicts.add(new WifiList(1, "REW123"));
		dicts.add(new WifiList(2, "REW521"));
		
		wifiadapter = new WifiListAdapter(this, dicts);
		wifi_list.setAdapter(wifiadapter);
		
		
		progressDialogRefreshing = new ProgressDialog(WifiManageActivity.this);
		progressDialogRefreshing.setMessage("正在更新状态,请稍后。");
		progressDialogRefreshing.setCancelable(false);
		
		
		wifiPassword.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			
		wifiPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1) {
					wifiPassword.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                	wifiPassword.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
			}
		});
		
    	wifi_list.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				Toast.makeText(WifiManageActivity.this, wifi_list.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
    		
    		
    		
		});
		
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			
			handler_key key = handler_key.values()[msg.what];
			switch(key){
			
				case UPDATE_UI:
					//handler.removeMessages(handler_key.UPDATE_UI.ordinal());
					//router_ssid.setText(isssid);
					wifiPassword.setText(ispasswod);
					break;				
			    case RECEIVED:
			    	if (deviceDataMap.get("data") != null) {
			    		try {
							inputDataToMaps(statuMap, (String) deviceDataMap.get("data"));
							handler.sendEmptyMessage(handler_key.SUCCESS.ordinal());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	}
			    	break;
				case DISCONNECTED:
					//断开,对话框，回到设备例表
					//handler.removeMessages(handler_key.DISCONNECTED.ordinal());
					Intent intentone = new Intent(WifiManageActivity.this, DeviceListActivity.class);
					startActivity(intentone);
					finish();
					break;					
				case SUCCESS:
					//返回主页面
					//if ()
					//if (statuMap != null && statuMap.size() > 0) {
						//boolean isTurnOn = (Boolean) statuMap.get(JsonKeys.SSID);
						//if (isTurnOn == true){
						//	DialogManager.dismissDialog(WifiManageActivity.this, progressDialogRefreshing);
						//	handler.removeMessages(handler_key.TIMEOUT.ordinal());
							//跳到
						//	Intent intent = new Intent(WifiManageActivity.this, MusicManageActivity.class);
						//	startActivity(intent);
						//	finish();
						//}
					//}
										
					break;
				case TIMEOUT:
					//输入错误
					DialogManager.dismissDialog(WifiManageActivity.this, progressDialogRefreshing);
					//handler.removeMessages(handler_key.TIMEOUT.ordinal());
					dialog();
					break;
				case START:
					//router_ssid.setText(save_ssid);
					break;
			
			}
	
		}
		
	};
	
	protected void dialog( ){
		new AlertDialog.Builder(WifiManageActivity.this).setTitle("提示")
		.setMessage("你输入密码有误，请重输入...").setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
				handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
			
			}
		}).show();
	}
	

	
	//设备断开回调
	@Override
	protected void didDisconnected(XPGWifiDevice device) {
		// TODO Auto-generated method stub
		if (!device.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid()))
			return;
		
		handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
	}
	//接收到数据回调
	@Override
	protected void didReceiveData(XPGWifiDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		// TODO Auto-generated method stub
		if(!device.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid()))
			return;
		deviceDataMap = dataMap;
		handler.sendEmptyMessage(handler_key.SUCCESS.ordinal());
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
		
			case R.id.router_opr:
				if (StringUtils.isEmpty(wifi_list.getSelectedItem().toString()) ){
					Toast.makeText(WifiManageActivity.this, "没有可用路由!", Toast.LENGTH_SHORT).show();
				}else if (StringUtils.isEmpty(wifiPassword.getText().toString().trim())){
					Toast.makeText(WifiManageActivity.this, "请输入密码  !", Toast.LENGTH_SHORT).show();
				}else{
					//isssid = router_ssid.getText().toString().trim();
					ispasswod = wifiPassword.getText().toString().trim();
					DialogManager.showDialog(this, progressDialogRefreshing);//显示 进度条
					handler.sendEmptyMessageDelayed(handler_key.TIMEOUT.ordinal(), 6000);
				}
				break;
			case R.id.ivBack:
				finish();
				break;
		}
	}

	
}
