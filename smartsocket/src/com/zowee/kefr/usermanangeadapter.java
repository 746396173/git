package com.zowee.kefr;

import java.util.List;

import com.gizwits.powersocket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class usermanangeadapter extends BaseAdapter{

	
	private LayoutInflater mInflater;
	private Context mContext;  
    private List<String> mDatas;
    
    public usermanangeadapter(Context context, List<String> mDatas) {
		// TODO Auto-generated constructor stub
    	
    	this.mContext = context;
    	this.mDatas   = mDatas;
    	mInflater = LayoutInflater.from(context);
	}
	    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDatas.get(arg0);
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
	        if (arg1 == null)  
	        {  
	        	arg1 = mInflater.inflate(R.layout.user_manage_adapter, arg2, false);  
	            viewHolder = new ViewHolder();  
	            viewHolder.mTextView = (TextView) arg1.findViewById(R.id.user_list_name);  
	            arg1.setTag(viewHolder);  
	        } else  
	        {  
	            viewHolder = (ViewHolder) arg1.getTag();  
	        }  
	        viewHolder.mTextView.setText(mDatas.get(arg0));  
	        return arg1;
	}
	
	private final class ViewHolder  
    {  
        TextView mTextView;  
    }  

}
