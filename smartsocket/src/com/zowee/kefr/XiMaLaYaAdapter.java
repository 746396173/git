package com.zowee.kefr;

import java.util.List;
import com.gizwits.powersocket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class XiMaLaYaAdapter extends BaseAdapter{

	private List<String> name;
	
	private LayoutInflater mInflater;
	
	private Context context;
	
	public XiMaLaYaAdapter(Context context, List<String> name){
		this.context = context;
		this.name    = name;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return name.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		//String tv_name = name.get(arg0);
		ViewHolder holder;
		if (arg1 == null){
			holder = new ViewHolder();
			arg1 = mInflater.inflate(R.layout.ximalayaadapter, null);
			holder.tvName = (TextView) arg1.findViewById(R.id.tvName);
			holder.ivType = (ImageView) arg1.findViewById(R.id.icon);
			holder.ivArrow = (ImageView) arg1.findViewById(R.id.ivArrow);
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder) arg1.getTag();
		}
		
		holder.ivType.setImageResource(R.drawable.head_icon2);
		holder.tvName.setText(name.get(arg0));
		holder.ivArrow.setImageResource(R.drawable.return_ic);
		
		return arg1;
	}
	

	private static class ViewHolder {
			
		/** The iv type. */
		ImageView ivType;
		
		/** The tv name. */
		TextView tvName;
		
		/** The iv arrow. */
		ImageView ivArrow;
	}

}
