/**
 * Project Name:XPGSdkV4AppBase
 * File Name:AirlinkActivity.java
 * Package Name:com.gizwits.framework.activity.onboarding
 * Date:2015-1-27 14:45:48
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
package com.gizwits.framework.activity.onboarding;

import java.util.Timer;
import java.util.TimerTask;

import mediatek.android.IoTManager.IoTManagerNative;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qr_codescan.MipcaActivityCapture;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.powersocket.R;
import com.mediatek.elian.ElianNative;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.StringUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.zowee.kefr.activity.ConfigDeviceNet;


/**
 * ClassName: Class AirlinkActivity. <br/>
 * 配置结果
 * <br/>
 *
 * @author Lien
 */
@SuppressLint("HandlerLeak")
public class AirlinkActivity extends BaseActivity implements OnClickListener {

    /**
     * The btn config.
     */
    private Button btnConfig;

    /**
     * The btn retry.
     */
    private Button btnRetry;

    /**
     * The btn softap.
     */
   // private Button btnSoftap;

    /**
     * The iv back.
     */
    private ImageView ivBack;
    
    /**
     * The ll start config.
     */
    private LinearLayout llStartConfig;

    /**
     * The ll configing.
     */
    private LinearLayout llConfiging;

    /**
     * The ll config failed.
     */
    private LinearLayout llConfigFailed;

    /** The tv tick. */
    //private TextView tvTick;

    /** The secondleft. */
    int secondleft = 60;

    /** The timer. */
    private Timer timer;

    /** The str s sid. */
    private String strSSid;
    
    /** The str psw. */
    private String strPsw;
    
    private String ScanRet;
    
    /** The UI_STATE now. */
    private UI_STATE UiStateNow;
    //wifi
    private byte pAuthMode;
    private final byte kAuthMode = 0x00;
    private IoTManagerNative IoTManager;
    private boolean wififlash = false;
    
    private String product_type, product_mac;
    
    private String product_key,did,passcode;
    
	private boolean issuccess = false;
	
	private boolean isbind = false;
	
    private ElianNative elian;
    
   // private Dialog reshuffleDialog;
    /**
     * ClassName: Enum handler_key. <br/>
     * <br/>
     * date: 2014-11-26 17:51:10 <br/>
     *
     * @author Lien
     */
    private enum handler_key {

        /**
         * The tick time.
         */
        TICK_TIME,

        /**
         * The reg success.
         */
        CONFIG_SUCCESS,

        /**
         * The toast.
         */

        CONFIG_FAILED,
        
        START_BIND,
        
        BIND_SUCCESS,
        
        BIND_FAILED,

    }

    /**
     * The handler.
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler_key key = handler_key.values()[msg.what];
            switch (key) {
            
            	case START_BIND:
            		isbind = true;
            		mCenter.cBindDevice(setmanager.getUid(), setmanager.getToken(), did, passcode, "");
            		break;

            	case BIND_SUCCESS:
            	case BIND_FAILED:
            		if (issuccess == false){
            			Toast.makeText(AirlinkActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
            		}else{
            			Toast.makeText(AirlinkActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
            		}
            		Intent intent = new Intent(AirlinkActivity.this,DeviceListActivity.class);
            		startActivity(intent);
            		finish();
            		break;
                case TICK_TIME:
                    secondleft--;
                    if (secondleft <= 0) {
                        timer.cancel();
                        sendEmptyMessage(handler_key.CONFIG_FAILED.ordinal());
                    } else {
                       // tvTick.setText(secondleft + "");
                    }
                    break;

                case CONFIG_SUCCESS:
                	if (wififlash == true){
                		wififlash = false;
                		if (product_type.substring(0, 2).equals("SF") || product_type.substring(0, 2).equals("SL")){
                			elian.StopSmartConnection();
                		}else{                		
                			IoTManager.StopSmartConnection();
                		}
                	}
                    IntentUtils.getInstance().startActivity(AirlinkActivity.this,
                            DeviceListActivity.class);
                    finish();
                    break;


                case CONFIG_FAILED:
                	if (wififlash == true){
                		wififlash = false;
                		if (product_type.substring(0, 2).equals("SF") || product_type.substring(0, 2).equals("SL")){
                			elian.StopSmartConnection();
                		}else{                		
                			IoTManager.StopSmartConnection();
                		}
                	}
                    showLayout(UI_STATE.Result);
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
        setContentView(R.layout.activity_airlink);
        
        initViews();
        initEvents();
        initData();
    }


    /**
     * Inits the events.
     */
    private void initEvents() {

        btnConfig.setOnClickListener(this);
        btnRetry.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    /**
     * Inits the views.
     */
    private void initViews() {
    	
        btnConfig = (Button) findViewById(R.id.btnConfig);//开始配置
        btnRetry = (Button) findViewById(R.id.btnRetry);//重试
      //  btnSoftap = (Button) findViewById(R.id.btnSoftap);
      //  tvTick = (TextView) findViewById(R.id.tvTick);//显示时间
        ivBack=(ImageView) findViewById(R.id.ivBack);
        llStartConfig = (LinearLayout) findViewById(R.id.llStartConfig);//配置模式
        llConfiging = (LinearLayout) findViewById(R.id.llConfiging);//配置进行中模式
        llConfigFailed = (LinearLayout) findViewById(R.id.llConfigFailed);//配置出错模式
    }

    /**
     * Inits the data.
     */
    private void initData() {
        if (getIntent() != null) {
           
            if (!StringUtils.isEmpty(getIntent().getStringExtra("ScanRet"))){
            	ScanRet = getIntent().getStringExtra("ScanRet");
            	
            	if (ScanRet.contains("product_key=") && ScanRet.contains("did=")
        				&& ScanRet.contains("passcode=")) {

            		product_key = getParamFomeUrl(ScanRet, "product_key");
            		did = getParamFomeUrl(ScanRet, "did");
            		passcode = getParamFomeUrl(ScanRet, "passcode");
            		Log.i("initData", ScanRet);
            		handler.sendEmptyMessage(handler_key.START_BIND.ordinal());
            	}else{
            		
            		 if (!StringUtils.isEmpty(getIntent().getStringExtra("ssid"))) {
                         strSSid = getIntent().getStringExtra("ssid");
                     }
                     if (!StringUtils.isEmpty(getIntent().getStringExtra("psw"))) {
                         strPsw = getIntent().getStringExtra("psw");
                     } else {
                         strPsw = "";
                     }
            	
            		product_mac = getParamFomeUrl_product_mac(ScanRet, "_");
            	
            		product_type  = getParamFomeUrl_product_type(ScanRet, "_");

            	}
            	
            }
            pAuthMode = getIntent().getByteExtra("mAuthMode", kAuthMode);
           
    		IoTManager = new IoTManagerNative();
    		boolean result = ElianNative.LoadLib();//lib
    		if (!result){
    			
    		}
    		elian = new ElianNative();
    		
    		 startAirlink();
        }
    }
    
    
    private String getParamFomeUrl(String url, String param) {
		String product_key = "";
		int startindex = url.indexOf(param + "=");
		startindex += (param.length() + 1);
		String subString = url.substring(startindex);
		int endindex = subString.indexOf("&");
		if (endindex == -1) {
			product_key = subString;
		} else {
			product_key = subString.substring(0, endindex);
		}
		return product_key;
	}
    
    private String getParamFomeUrl_product_mac(String url, String param) {
		String product_key = "";
		int startindx = url.indexOf(param);
		startindx += 1;		
		product_key = url.substring(startindx);
		return product_key;
	}

    
    private String getParamFomeUrl_product_type(String url, String param) {
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
    
    private enum UI_STATE{
    	Ready,Setting,Result;
    }
    
    private void showLayout(UI_STATE ui){
    	UiStateNow=ui;
    	switch(ui){
    	case Ready:
    		llStartConfig.setVisibility(View.VISIBLE);
            llConfiging.setVisibility(View.GONE);
            llConfigFailed.setVisibility(View.GONE);
            ivBack.setVisibility(View.VISIBLE);
    		break;
    	case Setting:
    		llStartConfig.setVisibility(View.GONE);
            llConfiging.setVisibility(View.VISIBLE);
            llConfigFailed.setVisibility(View.GONE);
            ivBack.setVisibility(View.GONE);
    		break;
    	case Result:
    		llConfigFailed.setVisibility(View.VISIBLE);
            llConfiging.setVisibility(View.GONE);
            llStartConfig.setVisibility(View.GONE);
            ivBack.setVisibility(View.VISIBLE);
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
            case R.id.btnConfig://开始配置
                startAirlink();
                break;
            case R.id.btnRetry: //重试
            	IntentUtils.getInstance().startActivity(
            			AirlinkActivity.this,
						MipcaActivityCapture.class);
            	finish();
                break;
            case R.id.ivBack:
            	onBackPressed();
            	break;
        }

    }

    /**
     * Start airlink.
     */
    private void startAirlink() {
        secondleft = 120;
        //tvTick.setText(secondleft + "");
        showLayout(UI_STATE.Setting);
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
            }
        }, 1000, 1000);
       
        if (wififlash == false){
        	
        	wififlash = true;
        	if (product_type != null){
        		//if (product_type.equals("SF610") || product_type.equals("SF611")){
        		if (product_type.substring(0, 2).equals("SF") || product_type.substring(0, 2).equals("SL")){
        			elian.InitSmartConnection(null, 0, 1);
        			elian.StartSmartConnection(strSSid, strPsw, product_mac);
        		}else{
        			IoTManager.StartSmartConnection(strSSid, strPsw, (byte)pAuthMode);
        		}
        	}
        }
    }

    @Override
	public void onBackPressed() {
    	switch(UiStateNow){
    	case Result:
    	case Ready:
    		//startActivity(new Intent(AirlinkActivity.this,AutoConfigActivity.class));
    		Intent intent = new Intent(AirlinkActivity.this,ConfigDeviceNet.class);
    		intent.putExtra("ScanRet", ScanRet);
    		startActivity(intent);
        	finish();
    		break;
    	case Setting:
    		break;
    	}
    	
	}
    
    @Override
    protected void didBindDevice(int error, String errorMessage, String did) {
    	// TODO Auto-generated method stub
    	Log.i("errorMessage", errorMessage);
    	if (isbind == true){
    		if (error == 0){
    			issuccess = true;
    			handler.sendEmptyMessage(handler_key.BIND_SUCCESS.ordinal());
    		}else{
    			issuccess = false;
    			handler.sendEmptyMessage(handler_key.BIND_FAILED.ordinal());
    		}
    	
    	}
    }

	/* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#didSetDeviceWifi(int, com.xtremeprog.xpgconnect.XPGWifiDevice)
     */
    @Override
    protected void didSetDeviceWifi(int error, XPGWifiDevice device) {
    	   	
    		if (error == 0) {
    			
    			if ( device.getMacAddress().equals(product_mac)){
    				CurrDeviceMac = device.getMacAddress();
    				handler.sendEmptyMessage(handler_key.CONFIG_SUCCESS.ordinal());
    			}
    		} else {
    			handler.sendEmptyMessage(handler_key.CONFIG_FAILED.ordinal());
    		}    	
    }
}
