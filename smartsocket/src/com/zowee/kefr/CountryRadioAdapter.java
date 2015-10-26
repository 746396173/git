package com.zowee.kefr;

import java.util.List;

import com.gizwits.powersocket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CountryRadioAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	
	private Context context;
	
	private List<ProviceNameAndCode> pNameAndCodes;
	
	public CountryRadioAdapter(Context context, List<ProviceNameAndCode> pNameAndCodes){
		this.context   = context;
		this.pNameAndCodes  =  pNameAndCodes;
		this.mInflater = LayoutInflater.from(context);    
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pNameAndCodes.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return pNameAndCodes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (arg1 == null){
			holder = new ViewHolder();
			arg1 = mInflater.inflate(R.layout.countryradioadapter, null);
			holder.tvName = (TextView) arg1.findViewById(R.id.tv_name);
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder) arg1.getTag();
		}
		holder.tvName.setText(pNameAndCodes.get(arg0).getProviceName());
		return arg1;
	}
	
	private static class ViewHolder {	
		/** The tv name. */
		TextView tvName;
	}

}
