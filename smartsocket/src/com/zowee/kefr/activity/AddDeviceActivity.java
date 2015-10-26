package com.zowee.kefr.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ImageView;
import android.widget.ListView;


import com.example.qr_codescan.MipcaActivityCapture;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.powersocket.R;
import com.zowee.kefr.usermanangeadapter;

public class AddDeviceActivity extends BaseActivity{


	private usermanangeadapter userDeviceManageadapter;
    private ListView dev_add_list;
    private ImageView back;
    
    private List<String> list = new ArrayList<String>(Arrays.asList("音乐麼饼",  
            "音乐魔球", "智能插座"));
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_device_list);
		
		back        = (ImageView) findViewById(R.id.bind_ivBack);
		dev_add_list = (ListView) findViewById(R.id.add_device_list);
		userDeviceManageadapter = new usermanangeadapter(this, list);
		dev_add_list.setAdapter(userDeviceManageadapter);
		initEvents();
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	
 
    private void initEvents(){
    	
    	dev_add_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(AddDeviceActivity.this,
						MipcaActivityCapture.class);
				//intent.putExtra("number", arg2);
				intent.putExtra("number", arg2);
				startActivity(intent);
				//switch(arg2){
				//case 0:
					
						
				  //IntentUtils.getInstance().startActivity(AddDeviceActivity.this, MipcaActivityCapture.class);
						
				
				//	break;
			//	}
				
			}
		});
    }
    
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	finish();
    }
    

}
