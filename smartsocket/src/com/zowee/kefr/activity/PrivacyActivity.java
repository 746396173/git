package com.zowee.kefr.activity;




import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.powersocket.R;

public class PrivacyActivity extends BaseActivity implements OnClickListener{

	//private TextView readTextView;
	//private WebView wv;
	private WebView wv;
	
	private  WebSettings settings;
	 
	private ImageView ivLogout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.privacyactivity);
		
		//readTextView = (TextView) findViewById(R.id.readtextView);
		wv           = (WebView) findViewById(R.id.web);
		ivLogout     = (ImageView) findViewById(R.id.ivLogout);
		
		ivLogout.setOnClickListener(this);
		
		//settings = wv.getSettings();
		//settings.setSupportZoom(true);
		
		
		
		//InputStream inputStream = getResources().openRawResource(R.raw.brovi);
		//String string = TxtReader.getString(inputStream);
		//String string = TxtReader.readWord(inputStream);
		//readTextView.setText(string);
		/**
		try {
			
			InputStream is = null;
			AssetManager manager = getAssets();
			is = manager.open("brovi.xml");
			String string = TxtReader.getString(is);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //persons.xml保存在assets文件夹下  
		*/	
		settings = wv.getSettings();
		//settings.setSupportZoom(true);
		settings.setTextSize(WebSettings.TextSize.SMALLEST);
		wv.loadUrl("file:///android_asset/brovi.htm");
		 
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ivLogout:
			onBackPressed();
			break;
		}
	}

	
}
