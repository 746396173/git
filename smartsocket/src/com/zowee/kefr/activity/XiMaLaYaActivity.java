package com.zowee.kefr.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.powersocket.R;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.zowee.kefr.XiMaLaYaAdapter;

public class XiMaLaYaActivity extends BaseActivity {
	
	//private XiMaLaYaImage mXiMaLaYaImage;
	
	//private Lives lives;
	
	//private List<Radio> radio;
	
	//private RefreshableListView lvDevices;
	
	//private RadioList radiolist;
	
	//private List<City> city = new ArrayList<City>();
	
	//private List<Province> province = new ArrayList<Province>();
	
	//private List<Integer> provinceCode = new ArrayList<Integer>();
	
	//private List<String> Imagetitle = new ArrayList<String>();
	
	//private HashMap<String, Object> mHashMap;
	//private Increments increments;
	
	//private static final long LESSTHENTENMINUTES = System.currentTimeMillis()-60*8*1000;
	
	//protected static List<BitmapAndUrl> Bitmap_Url = new ArrayList<BitmapAndUrl>();
	
	private ListView list;
	
	private List<String> name = new ArrayList<String>();
	
	private XiMaLaYaAdapter mximalayaadapter;
	
	/**
	private enum handler_key {
		
		UPDATE_UI,
		UPDATE,
		BITMAP,
		DISCONNECTED,
		RECEIVED,
	}
	
	@SuppressLint("HandlerLeak") 
	Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			handler_key key = handler_key.values()[msg.what];
			switch(key){
			case RECEIVED:
				//判断是否改变成功
				for (String myKey : deviceDataMap.keySet()) {
					Log.e("Map1",
							"key=" + myKey + ",value="
									+ deviceDataMap.get(myKey));
				}
				if (deviceDataMap.get("data") != null) {
					try {
						inputDataToMaps(statuMap, (String) deviceDataMap.get("data"));
						handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case UPDATE_UI:
				//返回数据
				break;
			case UPDATE:
				
				*/
				/**
				if (radio != null){
					for (int i = 0; i < radio.size(); i++){
						 Radio tmpradio = radio.get(i);
						 Log.i("data", "CoverUrlLarge="+tmpradio.getCoverUrlLarge());//电台封面大图
						 Log.i("data", "CoverUrlSmall="+tmpradio.getCoverUrlSmall());//电台封面小图
						 Log.i("data", "Kind="+tmpradio.getKind());//固定值 "radio"
						 Log.i("data", "ProgramName="+tmpradio.getProgramName());//正在直播的节目名称
						 Log.i("data", "RadioDesc"+tmpradio.getRadioDesc());//电台简介
						 Log.i("data", "RadioName="+tmpradio.getRadioName());//电台名称
						 Log.i("data", "Rate24AacUrl="+tmpradio.getRate24AacUrl());
						 Log.i("data", "Rate24TsUrl="+tmpradio.getRate24TsUrl());
						 Log.i("data", "Rate64AacUrl="+tmpradio.getRate64AacUrl());//使用这个
						 Log.i("data", "Rate64TsUrl="+tmpradio.getRate64TsUrl());
						 Log.i("data", "id="+tmpradio.getId());
						 Log.i("data", "RadioPlayCount="+tmpradio.getRadioPlayCount());//电台累计收听次数
						 Log.i("data", "ScheduleID="+tmpradio.getScheduleID());//正在直播的节目时间表ID
						 Log.i("data", "UpdatedAt="+tmpradio.getUpdatedAt());//声音更新时间，Unix毫秒数时间戳
						 Log.i("data", ""+tmpradio.getSupportBitRates());//支持的码率列表
						 GetImageString(tmpradio.getCoverUrlSmall(), tmpradio.getRate64AacUrl());
					 }
					handler.sendEmptyMessage(handler_key.BITMAP.ordinal());
				}
				*/
				
				/**
				if (province != null){
					for (int i = 0; i < province.size(); i++){
						Province tmp = province.get(i);
						 Log.i("data", "province="+tmp.getKind());
						 Log.i("data", "province="+tmp.getProvinceName());
						 Log.i("data", "province="+tmp.getCreatedAt());
						 Log.i("data", "province="+tmp.getId());
						 Log.i("data", "province="+tmp.getProvinceCode());
						 Log.i("data", tmp.getProvinceCode().toString());
						 provinceCode.add(tmp.getProvinceCode());
					}
					//handler.sendEmptyMessage(handler_key.BITMAP.ordinal());					
				}
				*/
			//	break;
		//	case BITMAP:
				/**
				Log.i("city", "provinceCode = "+provinceCode.size());
				if (provinceCode.size() > 0){
					//Log.i("Image_Bitmap", "Image_Bitmap"+Bitmap_Url.size());
					//去掉重复的数据
					getCityByProvinces();
				}
				lvDevices.completeRefreshing();
				*/
			//	break;
			//default:
			//	break;
			//}
	//	}
		
//	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ximalayaactivity);
		
		//lvDevices  = (RefreshableListView) findViewById(R.id.lvDevices);
		//mXiMaLaYaImage = new XiMaLaYaImage(XiMaLaYaActivity.this, Bitmap_Url);
		
		//Imagetitle.add("");
		
		//lvDevices.setAdapter(mXiMaLaYaImage);
		//lvDevices.setOnItemClickListener(this);
		
		/**
		lvDevices.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(RefreshableListView listView) {
				// TODO Auto-generated method stub
				//RefreshData();
				getProvince();
			}
		});
		*/
		//lives = new Lives();	
		//RefreshData();
		//getProvince();	
		
		list = (ListView) findViewById(R.id.tv);
		name.add("国家电台");
		name.add("省市电台");
		name.add("网络电台");
		
		mximalayaadapter = new XiMaLaYaAdapter(XiMaLaYaActivity.this, name);
		list.setAdapter(mximalayaadapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(XiMaLaYaActivity.this, CountryRadioActivity.class);
				intent.putExtra("State", ""+arg2);
				startActivity(intent);
				
				/**
				switch(arg2){
				case 0:
					//国家
					Intent intentone = new Intent(XiMaLaYaActivity.this, CountryRadioActivity.class);
					intentone.putExtra("State", "0");
					startActivity(intentone);
					break;
				case 1:
					//省市
					Intent intentone = new Intent(XiMaLaYaActivity.this, CountryRadioActivity.class);
					intentone.putExtra("State", "0");
					startActivity(intentone);
					break;
				case 2:
					//网络
					Intent intentone = new Intent(XiMaLaYaActivity.this, CountryRadioActivity.class);
					intentone.putExtra("State", "0");
					startActivity(intentone);
					break;
				
				}
				*/
			}
			
		});
	}
	
	
	
	/**
	//根据电台类型、省份代码（由上面的<code>getProvinces()</code>获得）
	private void getRadioList() throws XimalayaException{
	*/
		//radioType 1-国家台，2-省市台，3-网络台
		// provinceCode 省市代码，如果radioType为2则必须指定该参数
		// paging 分页参数
		/**
		RadioList radioList = lives.getRadioList(1, "110000", new Paging());
		Assert.assertTrue(radioList != null && radioList.getRadios() != null
				&& !radioList.getRadios().isEmpty());
		radio = radioList.getRadios();
		
		handler.sendEmptyMessage(handler_key.UPDATE.ordinal());
		*/
	//}
	
	/**
	// 获取直播省市列表
	private void getProvince(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}).start();
		*/
		
		/**
		 *  private Long id;                // 省市ID
			private String kind;            // DTO实体类型
			private Integer provinceCode;   // 省市代码，比如110000
			private String provinceName;    // 省市名称
			private Long createdAt;         // 更新时间
	
		 * 
		 */
		
	//}
	/**
	//获取某省份城市列表
	private void getCityByProvinces(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
			*/
				// TODO Auto-generated method stub	
				/**
			    try {
					city.clear();
					for (int i = 0; i < provinceCode.size(); i++){
						city = lives.getCityByProvince(provinceCode.get(i));
						//provinceCode.get(i)
						for (int j = 0; j < city.size(); i++){							
							City citytmp = city.get(j);
							Log.i("city", "city = "+citytmp.getCityCode()); // 城市code (国家行政规划的城市代码)
							Log.i("city", "city = "+citytmp.getCityName());
							Log.i("city", "city = "+citytmp.getKind());     // DTO实体类型
							Log.i("city", "city = "+citytmp.getCreatedAt());// 更新时间
							Log.i("city", "city = "+citytmp.getId());       // 省市ID
							Log.i("city", "city = "+citytmp.getUpdateAt()); // 更新时间
							
						}
					}
			    } catch (XimalayaException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
			    }
				*/
			//}

		//}).start();
		//城市code (国家行政规划的城市代码)
		
	//}
	
	/**
	// 获取某个城市下的电台列表。
	private void getRadiosByCity(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//分页参数，可选，不填则为默认值
				//city = lives.getRadiosByCity(1, new Paging());
			}
		}).start();
		
	}
	*/
	
	/**
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws XimalayaException 
	*/
	
	private Bitmap getImage(String htmlpath) throws ClientProtocolException, IOException{
		Bitmap bitmap = null;
		//HttpClient hc = new DefaultHttpClient(); 
		//HttpGet hg = new HttpGet(htmlpath);
		//HttpResponse hr = hc.execute(hg);
		//return BitmapFactory.decodeStream(hr.getEntity().getContent());
		
		//httpGet连接对象 
		HttpGet httpRequest = new HttpGet(htmlpath);
		//取得HttpClient 对象  
        HttpClient httpclient = new DefaultHttpClient();  
       //请求httpClient ，取得HttpRestponse  
        HttpResponse httpResponse = httpclient.execute(httpRequest); 
        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
        	//取得相关信息 取得HttpEntiy  
            HttpEntity httpEntity = httpResponse.getEntity(); 
            //获得一个输入流  
            InputStream is = httpEntity.getContent(); 
            System.out.println(is.available());
            bitmap = BitmapFactory.decodeStream(is);  
            is.close();            
        }
		return bitmap;
	}
	
	private void GetImageString(final String url, final String patch){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				/**
				try {
					Bitmap_Url.add(new BitmapAndUrl(getImage(url), patch));
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			}
		}).start();
	}	

	
	/**
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mXpgWifiDevice.setListener(deviceListener);
		//mXiMaLaYaImage.changeDatas(new );
	}
	
	@Override
	protected void didDisconnected(XPGWifiDevice device) {
		// TODO Auto-generated method stub
		super.didDisconnected(device);
		if (!device.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid()))
			return;
		handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
	}
	
	@Override
	protected void didReceiveData(XPGWifiDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		// TODO Auto-generated method stub
		super.didReceiveData(device, dataMap, result);
		
		if(!device.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid()))
			return;
		deviceDataMap = dataMap;
		handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
	}

	*/
	
	/**
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.i("onItemClick", "onItemClick = "+arg2);
		Log.i("onItemClick", ""+Bitmap_Url.get(arg2).getUrl());
		String tmp;
		int len;
		tmp = Base64.encode(Bitmap_Url.get(arg2).getUrl().getBytes());
		
		len = tmp.length();
		Log.i("onItemClick", "onItemClick11 = "+tmp);
		Log.i("onItemClick", "onItemClick22 = "+len);		
		//mCenter.cXiMaLaYa(mXpgWifiDevice, tmp);
		
	}
	*/
	
}
