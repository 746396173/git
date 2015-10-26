package com.zowee.kefr.activity;


import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.config.Configs;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.powersocket.R;

import com.xpg.common.useful.StringUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.zowee.kefr.Base64;

@SuppressLint({ "HandlerLeak", "ResourceAsColor" })
public class DeviceSsidManageActivity  extends BaseActivity implements OnClickListener{
	
	private EditText repair_ssid, pwd_ssid;
	
	private LinearLayout input_route_name,input_pwd_name, input_pwd;
	
	private RelativeLayout chang_pwd_encryption;
	
	private ToggleButton change_route_name, change_pwd_name;
	
	private CheckBox rad_left, rad_right;

	
	private ToggleButton tbPswFlag;
	
	private String save_ssid;
	private String sava_pwd = "";
	private TextView dev_hint,route_name;
	private Button save_button, cancel;
	private ProgressDialog progressDialogRefreshing;
	private ImageView image_back;
	private boolean one = false;
	
	final private int MAX = 20;
	final private int MAXLENG = 16;
	final private int MINLENG = 8;
	
	private boolean isonClick = false;
	private boolean isencryption;
	
	private final String pw= "&pw=";
	
	private int IsInputLeng;
	
	private int IsInputLengs = 0;
	
	//private boolean IsShowPW = false;
	
	private enum handler_key {
		
		RECEIVED,
		UPDATE_UI,
		DISCONNECTED,
		PROGRESSDIALOG,
		TIMEOUT,
		FAIL,
		OK,
		FAILLEN,
		SUCCEED,
		START,
		PWDOK,
		PWDFAILL,
		PWDFAILS,
		PWD_EMPTY,
		ROUTER_EMPTY,
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.devicessidmanageactivity);
		
		input_route_name = (LinearLayout) findViewById(R.id.input_route_name);
		input_pwd_name   = (LinearLayout) findViewById(R.id.input_pwd_name);
		input_pwd        = (LinearLayout) findViewById(R.id.input_pwd);
		
		chang_pwd_encryption = (RelativeLayout) findViewById(R.id.chang_pwd_encryption);
		
		change_route_name = (ToggleButton) findViewById(R.id.change_route_name);
		change_pwd_name   = (ToggleButton) findViewById(R.id.change_pwd_name);
		
		rad_left = (CheckBox) findViewById(R.id.rad_left);
		rad_right = (CheckBox) findViewById(R.id.rad_right);
		
		repair_ssid = (EditText) findViewById(R.id.repair_ssid);
		pwd_ssid    =  (EditText) findViewById(R.id.pwd_update);

		dev_hint     = (TextView) findViewById(R.id.dev_hint);
		
		cancel = (Button) findViewById(R.id.cancel);
		save_button  = (Button) findViewById(R.id.save_button); 
		
		route_name  = (TextView) findViewById(R.id.route_name);
		image_back   = (ImageView) findViewById(R.id.ssid_Back);
		tbPswFlag    = (ToggleButton) findViewById(R.id.PswFlag);
		
		progressDialogRefreshing = new ProgressDialog(DeviceSsidManageActivity.this);
		progressDialogRefreshing.setMessage("正在更新状态,请稍后。");
		progressDialogRefreshing.setCancelable(false);
		
		
		if (getIntent() != null) {
			String tmp = getIntent().getStringExtra("DEVSSID&PW");
			Log.i("getIntent", "tmp = "+tmp);
			//route_name.setText(save_ssid);
			if (tmp.contains(pw)){
				save_ssid = getParamFomeUrl_ssid(tmp, pw);
				sava_pwd = getParamFomeUrl_pw(tmp, pw).trim();
				int len = sava_pwd.length();
				//IsInputLeng = len;
				//Log.i("afterTextChanged", "afterTextChanged0 = "+IsInputLeng);
				if (len > 0){
					isencryption = true;
					pwd_ssid.setText(sava_pwd.substring(0, len));
				}else{
					isencryption = false;
					sava_pwd = "";
					pwd_ssid.setText(sava_pwd);
				}
			}else{
				save_ssid = tmp;
			}
		}
		
		init_edit();
		
		input_pwd.setOnClickListener(this);		
		save_button.setOnClickListener(this);
		cancel.setOnClickListener(this);
		image_back.setOnClickListener(this);
		one = true;
		repair_ssid.setEnabled(false);
		pwd_ssid.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		
		if (Configs.PRODUCT_KEY[0].equals(mXpgWifiDevice.getProductKey())){
			route_name.setText("");
		}else if (Configs.PRODUCT_KEY[1].equals(mXpgWifiDevice.getProductKey())){
			route_name.setText("");
		}else if (Configs.PRODUCT_KEY[2].equals(mXpgWifiDevice.getProductKey())){
			route_name.setText("");
		}else if (Configs.PRODUCT_KEY[3].equals(mXpgWifiDevice.getProductKey())){
			route_name.setText("放大器网络");
		}else if (Configs.PRODUCT_KEY[4].equals(mXpgWifiDevice.getProductKey())){
			route_name.setText("放大器网络");
		}
				
		tbPswFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1) {
					//IsShowPW = true;
					pwd_ssid.setInputType(InputType.TYPE_CLASS_TEXT
									| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				} else {
					//IsShowPW = false;
					pwd_ssid.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
			}
		});
		
		//更改密码
		change_pwd_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1 == true){
					chang_pwd_encryption.setVisibility(View.VISIBLE);
			    	if (isencryption == true){
			    		//加密
			    		isencryption = true;
				    	input_pwd.setVisibility(View.VISIBLE);
			    		rad_left.setChecked(true);
			    		rad_right.setChecked(false);
			    		//pwd_ssid.setInputType(InputType.TYPE_CLASS_TEXT
								//| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			    		
			    	}else{
			    		//不加密
			    		isencryption = false;
			    		rad_left.setChecked(false);
			    		rad_right.setChecked(true);
			    		input_pwd.setVisibility(View.GONE);
			    	}
			    	
			    	//pwd_ssid.setText(sava_pwd);
				}else{
					chang_pwd_encryption.setVisibility(View.GONE);
			    	input_pwd.setVisibility(View.GONE);
				}
			}
		});
		//更改路由名称
		change_route_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1 == true){
					repair_ssid.setEnabled(true);
					repair_ssid.setSelection(repair_ssid.getText().toString().length());
				}else{
					repair_ssid.setEnabled(false);
				}
			}
		});
		
		//加密
		rad_left.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1 == true){
					isencryption = true;
					rad_right.setChecked(false);
					input_pwd.setVisibility(View.VISIBLE);
					//pwd_ssid.setInputType(InputType.TYPE_CLASS_TEXT
							//| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}else{
					isencryption = false;
					//不加密
					input_pwd.setVisibility(View.GONE);
					rad_right.setChecked(true);					
				}
			}
		});
		
		//不加密
		rad_right.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1 == true){
					isencryption = false;
					input_pwd.setVisibility(View.GONE);
					rad_left.setChecked(false);
				}else{
					isencryption = true;
					rad_left.setChecked(true);
					input_pwd.setVisibility(View.VISIBLE);
					//pwd_ssid.setInputType(InputType.TYPE_CLASS_TEXT
							//| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					
				}
			}
		});
		
	}	
	
	private void init_edit(){
		 
		repair_ssid.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
				String editable = repair_ssid.getText().toString();  
					//String str = stringFilter(editable.toString());
				String str = editable.toString();
				
		         if(!editable.equals(str)){
		        	 //不合法！
		        	//  repair_ssid.setText(str);
		              //设置新的光标所在位置  
		        	 // repair_ssid.setSelection(str.length());
		        	 handler.sendEmptyMessage(handler_key.FAIL.ordinal());
		        	
		          }else{
		        	  handler.sendEmptyMessage(handler_key.OK.ordinal());
		          }
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				//repair_ssid.setText(arg0);
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
				//editStart = repair_ssid.getSelectionStart();  
				//editEnd = repair_ssid.getSelectionEnd();
				save_ssid = repair_ssid.getText().toString().trim();
				int tmp = Stringlength(save_ssid);
				if (tmp >= MAX){
					   handler.sendEmptyMessage(handler_key.FAILLEN.ordinal());
				}
			}
		});
		
		pwd_ssid.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				String editable = pwd_ssid.getText().toString();  
				//String str = stringFilter(editable.toString());
			String str = editable.toString();
			
	         if(!editable.equals(str)){
	        	 //不合法！
	        	//  repair_ssid.setText(str);
	              //设置新的光标所在位置  
	        	 // repair_ssid.setSelection(str.length());
	        	 handler.sendEmptyMessage(handler_key.FAIL.ordinal());
	        	
	          }else{
	        	  handler.sendEmptyMessage(handler_key.PWDOK.ordinal());
	          }
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				IsInputLengs = pwd_ssid.getSelectionEnd();
				Log.i("afterTextChanged", "afterTextChanged1 = "+IsInputLeng);
				if (IsInputLeng > MAXLENG){
					handler.sendEmptyMessage(handler_key.PWDFAILL.ordinal());
				}
			}
		});
		
	}
			 
	    /**
	     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	     * @param value 指定的字符串
	     * @return 字符串的长度
	     */
	  private  int Stringlength(String value) {
	        int valueLength = 0;
	        String chinese = "[\u0391-\uFFE5]";
	        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
	        for (int i = 0; i < value.length(); i++) {
	            /* 获取一个字符 */
	            String temp = value.substring(i, i + 1);
	            /* 判断是否为中文字符 */
	            if (temp.matches(chinese)) {
	                /* 中文字符长度为2 */
	                valueLength += 2;
	            } else {
	                /* 其他字符长度为1 */
	                valueLength += 1;
	            }
	        }
	        return valueLength;
	   }
	
	public static String stringFilter(String str)throws PatternSyntaxException{     
	      // 只允许字母、数字和汉字      
	      String   regEx  =  "[^a-zA-Z0-9\u4E00-\u9FA5]";                     
	      Pattern   p   =   Pattern.compile(regEx);     
	      Matcher   m   =   p.matcher(str);     
	      return   m.replaceAll("").trim();     
	  }
	
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			handler_key key = handler_key.values()[msg.what];
			
			switch(key){
				case RECEIVED:
					//判断是否改变成功
					for (String myKey : deviceDataMap.keySet()) {
						Log.e("Map1",
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
					break;
				case UPDATE_UI:
					
					if (statuMap != null && statuMap.size() > 0 ){
						
						String  Dev_SSID = (String) statuMap.get(JsonKeys.DEV_SSID);
						
						if (!StringUtils.isEmpty(Dev_SSID)){
							try {
								Dev_SSID = new String (Base64.decode(Dev_SSID));
								if (Dev_SSID.contains(pw)){
									sava_pwd = getParamFomeUrl_ssid(Dev_SSID, pw);
									if (getParamFomeUrl_pw(Dev_SSID, pw).length() > 6){
									
										isencryption = true;
									}else{
										isencryption = false;
										
									}
									//Log.i("getIntent", "getIntent"+sava_pwd);
								}else{
									sava_pwd = Dev_SSID;
									
								}
								repair_ssid.setText(sava_pwd);
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (isonClick == true){
							isonClick = false;
							handler.sendEmptyMessage(handler_key.SUCCEED.ordinal());
						}
						
						handler.removeMessages(handler_key.TIMEOUT.ordinal());
						DialogManager.dismissDialog(DeviceSsidManageActivity.this, progressDialogRefreshing);
						
					}
					break;
				case DISCONNECTED:
					//返回设备例表
					Intent intent = new Intent(DeviceSsidManageActivity.this, MusicManageActivity.class);
					startActivity(intent);
					finish();
					break;
				case PROGRESSDIALOG:
					refreshMainControl();
					break;
				case TIMEOUT:
					//失败
					isonClick = false;
					DialogManager.dismissDialog(DeviceSsidManageActivity.this, progressDialogRefreshing);
					dev_hint.setText("修改失败，请检查网络");
					dev_hint.setTextColor(Color.parseColor("#CC0000"));
					break;
				case OK:
					
					if (one == true ){
						one = false;
						break;
					}
					dev_hint.setText(""); 	
					save_button.setEnabled(true);
					break;
				case FAIL:
					//dev_hint.setText("请输入密码6到16位");
					//dev_hint.setTextColor(Color.parseColor("#CC0000"));
					save_button.setEnabled(false);
					break;
				case FAILLEN:
					dev_hint.setText("输入名称太长");
					dev_hint.setTextColor(Color.parseColor("#CC0000"));
					break;
				case SUCCEED:
					dev_hint.setText("修改成功");
					dev_hint.setTextColor(Color.parseColor("#CC0000"));
					break;
				case PWDOK:
					dev_hint.setText(""); 
					save_button.setEnabled(true);
					break;					
				case PWDFAILL:
					dev_hint.setText("请输入密码8到16位");
					dev_hint.setTextColor(Color.parseColor("#CC0000"));
					save_button.setEnabled(false);
					break;
				case PWDFAILS:
					dev_hint.setText("请输入密码8到16位");
					dev_hint.setTextColor(Color.parseColor("#CC0000"));
					save_button.setEnabled(false);
					break;
				case PWD_EMPTY:
					dev_hint.setText("请输入密码");
					dev_hint.setTextColor(Color.parseColor("#CC0000"));
					save_button.setEnabled(false);
					break;
				case ROUTER_EMPTY:
					dev_hint.setText("请输入名称");
					dev_hint.setTextColor(Color.parseColor("#CC0000"));
					save_button.setEnabled(false);
					break;
			default:
				break;
			
			}
					
		}
		
	};
	
	private void refreshMainControl() {
		
		DialogManager.showDialog(this, progressDialogRefreshing);
		handler.sendEmptyMessageDelayed(
				handler_key.TIMEOUT.ordinal(), 30000);
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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
			/**
		    case R.id.input_route_name:
		    	change_route_name.setChecked(true);
		    	repair_ssid.setEnabled(true);
		    	break;
		    case R.id.input_pwd_name:
		    	change_pwd_name.setChecked(true);
		    	chang_pwd_encryption.setVisibility(View.VISIBLE);
		    	//input_pwd.setVisibility(View.VISIBLE);
		    	break;
		    */
			case R.id.save_button:
				//发送数据给设备	
				if (StringUtils.isEmpty(repair_ssid.getText().toString().trim())){
					//路由名称不能为空
					//Toast.makeText(DeviceSsidManageActivity.this, "请输入名称", Toast.LENGTH_SHORT).show();
					handler.sendEmptyMessage(handler_key.ROUTER_EMPTY.ordinal());
					break;
				}
				
				if (isencryption == true){
					if (StringUtils.isEmpty(pwd_ssid.getText().toString())){
						//Log.i("onClick", "onClick"+pwd_ssid.getText().toString());
						//密码不能为空
						//Toast.makeText(DeviceSsidManageActivity.this, "加密方式密码请输入密码", Toast.LENGTH_SHORT).show();
						handler.sendEmptyMessage(handler_key.PWD_EMPTY.ordinal());
							break;
					}else if (pwd_ssid.getText().toString().length() < MINLENG){
						//Log.i("afterTextChanged", "afterTextChanged3 = "+IsInputLeng);
						handler.sendEmptyMessage(handler_key.PWDFAILS.ordinal());
						break;
					}
					mCenter.cDev_SSID(mXpgWifiDevice, repair_ssid.getText().toString().trim()+pw+pwd_ssid.getText().toString());
										
				}else{
					sava_pwd = "";
					pwd_ssid.setText(sava_pwd);
					mCenter.cDev_SSID(mXpgWifiDevice, repair_ssid.getText().toString().trim()+pw);
				
				}
				
				isonClick = true;
				DialogManager.showDialog(this, progressDialogRefreshing);
				handler.sendEmptyMessageDelayed(handler_key.TIMEOUT.ordinal(), 8000);
				break;
			case R.id.ssid_Back:
			case R.id.cancel:
				finish();
				break;
		}
	
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mXpgWifiDevice.setListener(deviceListener);
		
		handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
	}

}
