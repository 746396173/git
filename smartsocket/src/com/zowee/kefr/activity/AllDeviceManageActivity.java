package com.zowee.kefr.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceManageDetailActivity;
import com.gizwits.framework.adapter.ManageListAdapter;
import com.gizwits.powersocket.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

public class AllDeviceManageActivity extends BaseActivity implements OnClickListener{

	  /**
     * The iv TopBar leftBtn.
     */
    private ImageView ivBack;

    /** The tv init date. */
    private ListView lvDevices;

    /** The Device device list. */
    private List<XPGWifiDevice> devices;

    /** The m adapter. */
    ManageListAdapter mAdapter;
    
    private TextView unbindname;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alldevicemanageactivty);
	
		 initViews();
	     initEvents();	     
	     initdata();
	}
	
	
	private void initdata(){
		
		initBindList();//获得绑定列表
		mAdapter.notifyDataSetChanged();
	}
	 /**
     * Inits the views.
     */
    private void initViews() {
      //  ivAdd = (ImageView) findViewById(R.id.bind_ivAdd);
    	unbindname = (TextView) findViewById(R.id.unbindname);
        ivBack = (ImageView) findViewById(R.id.bind_ivBack);
        lvDevices = (ListView) findViewById(R.id.Bind_Devices_list);
        mAdapter = new ManageListAdapter(AllDeviceManageActivity.this, bindlist);
       
        lvDevices.setAdapter(mAdapter);

    }
    
    
    /**
     * Inits the events.
     */
    private void initEvents() {
        lvDevices.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                XPGWifiDevice device = bindlist.get(position);
                
                Intent intent = new Intent(AllDeviceManageActivity.this,
                        DeviceManageDetailActivity.class);
                intent.putExtra("mac", device.getMacAddress());
                intent.putExtra("did", device.getDid());
                startActivity(intent);
            }
        });
        
        ivBack.setOnClickListener(this);
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
			case R.id.bind_ivBack:
				onBackPressed();
				break;
		}
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
		initBindList();
		mAdapter.notifyDataSetChanged();
		if (bindlist.size() > 0){
			unbindname.setVisibility(View.GONE);
			lvDevices.setVisibility(View.VISIBLE);
		}else{
			unbindname.setVisibility(View.VISIBLE);
			lvDevices.setVisibility(View.GONE);
		}
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();		
		finish();
	
	}
    
    
}
