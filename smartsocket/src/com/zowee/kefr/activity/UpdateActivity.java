package com.zowee.kefr.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.powersocket.R;
import com.zowee.kefr.PieProgress;

public class UpdateActivity extends BaseActivity{

	private TextView update;
	
	private PieProgress updateprogress;
	
	private ProgressBar Progress;
	
	private Timer timer;
	
	private int count;

	private int saveprogress;
	
	private enum handler_key {
		
		TICK_TIME,
		
		TIMEOUT,
		
		UPDATE,
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateactivity);
		
		update = (TextView) findViewById(R.id.updateing);
		Progress       = (ProgressBar) findViewById(R.id.update_progress);
		updateprogress = (PieProgress) findViewById(R.id.pie_progress);
		
		update.setText("正在下载中，请必离开...");
		updatemanage.setprogress(updateprogress);
		updatemanage.setprogress_one(Progress);
		updatemanage.showDownloadDialog();
		count = 30;
		timer = new Timer();
		timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
            }
        }, 1000, 1000);
		//setprogress
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			
			case TICK_TIME:
				if (count > 0){
					count--;
					if (saveprogress != updatemanage.getprogressparameter()){
						saveprogress = updatemanage.getprogressparameter();
						count = 30;
						if (updatemanage.getprogressparameter() == 360){
							timer.cancel();
							handler.sendEmptyMessage(handler_key.UPDATE.ordinal());
						}
					}
				}else{
					timer.cancel();
					handler.sendEmptyMessage(handler_key.TIMEOUT.ordinal());
				}
				break;
			case TIMEOUT:
				updatemanage.setprogressparameter(false);
				update.setText("下载出错");
				break;
			case UPDATE:
				update.setText("下载完成.");
				//finish();
				break;
			
			}
		}
		
	};
	
	
	
	public void onBackPressed() {		
		finish();
	};
	
}
