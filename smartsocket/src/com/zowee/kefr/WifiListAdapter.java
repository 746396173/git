package com.zowee.kefr;

import java.util.List;

import com.gizwits.powersocket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class WifiListAdapter extends BaseAdapter{

	 private LayoutInflater mInflater;
	 private Context context;
	 private List<WifiList> dicts;
	 
	 
	public WifiListAdapter(Context context, List<WifiList> dicts){
		 
		this.context = context;
		this.dicts   = dicts;
		mInflater = LayoutInflater.from(context);
		
	}
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dicts.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dicts.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;  
		
		if (arg1 == null){
			
			arg1 = mInflater.inflate(R.layout.wifi_list, arg2, false); 
			viewHolder = new ViewHolder();  
			viewHolder.mTextView = (TextView) arg1.findViewById(R.id.wifi_list_frist);  
			arg1.setTag(viewHolder);  
		}else{
			
			viewHolder = (ViewHolder) arg1.getTag();  
		}
		
		viewHolder.mTextView.setText(dicts.get(arg0).toString()); 
		return arg1;
	}
	
	private final class ViewHolder{
		
		TextView mTextView;  
	}	

}
