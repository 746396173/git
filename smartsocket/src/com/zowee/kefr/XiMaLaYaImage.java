package com.zowee.kefr;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.gizwits.powersocket.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class XiMaLaYaImage extends BaseAdapter{

	private List<BitmapAndUrl> Bitmap_Url;
	
	/** The inflater. */
	private LayoutInflater inflater;
	
	/** The context. */
	private Context context;
	
	public XiMaLaYaImage(Context context, List<BitmapAndUrl> Bitmap_Url){
		
		this.Bitmap_Url = Bitmap_Url;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		//changeDatas(new ArrayList<BitmapAndUrl> ());
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Bitmap_Url.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return Bitmap_Url.get(arg0);
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
		BitmapAndUrl bitmap = Bitmap_Url.get(arg0);
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = inflater.inflate(R.layout.ximalayaadapter, null);
			holder.icon = (ImageView) arg1.findViewById(R.id.icon);
			holder.tvName = (TextView) arg1.findViewById(R.id.tvName);
			holder.tvTime = (TextView) arg1.findViewById(R.id.tvtime);
			holder.ivArrow = (ImageView) arg1.findViewById(R.id.ivArrow);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		
		holder.icon.setImageBitmap(bitmap.getBitmap());
		holder.tvName.setText(bitmap.getRadioName());
		holder.tvTime.setText(bitmap.getProgramName());
		holder.ivArrow.setImageBitmap(bitmap.getBitmap());
		return arg1;
	}

	private static class ViewHolder {
		
		/** The iv arrow. */
		ImageView icon;
		
		TextView tvName;
		
		TextView tvTime;
		
		ImageView ivArrow;
	}
	
	private String GetStrTime(Long cc_time){
		String tmpTime;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年mm月dd日hh时mm分ss秒");
		tmpTime = sdf.format(new Date(cc_time));
		return tmpTime;		
	}
}
