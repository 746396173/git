package com.zowee.kefr.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.account.LoginActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.powersocket.R;
import com.xpg.common.system.IntentUtils;
import com.zowee.kefr.usermanangeadapter;

public class UserDeviceManageActivity extends BaseActivity{

	//private List<String> list = new ArrayList<String>();
	private List<String> list = new ArrayList<String>(Arrays.asList("用户名称",  
            "设备管理", "隐私政策", "检查更新"));
	private ListView listname;
	private ImageView ivLogout;
	private usermanangeadapter userDeviceManageadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userdevicemanage);
		
		listname = (ListView) findViewById(R.id.usr_list);
		ivLogout = (ImageView) findViewById(R.id.ivLogout);
		userDeviceManageadapter = new usermanangeadapter(this, list);
		listname.setAdapter(userDeviceManageadapter);
		ivLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		listname.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			switch(arg2){

				case 0:
					//登录
					setmanager.setToken("");
					setmanager.setUserName("");
					setmanager.setPassword("");
					setmanager.setUid("");
					IntentUtils.getInstance().startActivity(
							UserDeviceManageActivity.this,
							LoginActivity.class);
					finish();
					break;
				case 1:
					IntentUtils.getInstance().startActivity(
							UserDeviceManageActivity.this,
							AllDeviceManageActivity.class);
					break;
				case 2:
					break;
				case 3:
					break;
					
				}
				
			}
			
		});
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
	}
	
	
}
