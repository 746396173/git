package com.zowee.kefr.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.qr_codescan.MipcaActivityCapture;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.powersocket.R;
import com.xpg.common.system.IntentUtils;

public class AddDeviceErrorActivity extends BaseActivity implements OnClickListener{

	private ImageView ivLogout, close_dev;
	
	private Button newscan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_device_error_activity);
		
		ivLogout  = (ImageView) findViewById(R.id.ivLogout);
		close_dev = (ImageView) findViewById(R.id.close_dev);
		newscan   = (Button) findViewById(R.id.newscan);
		
		ivLogout.setOnClickListener(this);
		close_dev.setOnClickListener(this);
		newscan.setOnClickListener(this);
	}
	
	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()){

		case R.id.close_dev:
			//onBackPressed();
			finish();
			//关掉
			break;
		case R.id.ivLogout:
		case R.id.newscan:
			IntentUtils.getInstance().startActivity(AddDeviceErrorActivity.this, MipcaActivityCapture.class);
			finish();
			//重扫描
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		IntentUtils.getInstance().startActivity(AddDeviceErrorActivity.this, MipcaActivityCapture.class);
		finish();
	}
	
}
