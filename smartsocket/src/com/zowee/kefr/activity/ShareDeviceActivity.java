package com.zowee.kefr.activity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.powersocket.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

public class ShareDeviceActivity extends BaseActivity{

	private ImageView ivLogout;
	
	/** 当前设备的实例 */
	private XPGWifiDevice xpgWifiDevice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.share_device_activity);
				
		initParams();	
		ivLogout = (ImageView) findViewById(R.id.ivLogout);	
		String tmp = "product_key="+xpgWifiDevice.getProductKey()+"&"+"did="+xpgWifiDevice.getDid()+"&"+"passcode="+xpgWifiDevice.getPasscode();
		Log.i("tmp", tmp);
		ImageView imageView = (ImageView) findViewById(R.id.share_device);  
		Bitmap qrcode = generateQRCode(tmp); 
		imageView.setImageBitmap(qrcode); 
		
		ivLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	
	private void initParams() {
		
		if (getIntent() != null) {
			
			String mac = getIntent().getStringExtra("mac");
			String did = getIntent().getStringExtra("did");
			xpgWifiDevice = findDeviceByMac(mac, did);
			xpgWifiDevice.setListener(deviceListener);
		}
	}
	
	private Bitmap bitMatrix2Bitmap(BitMatrix matrix) {  
        int w = matrix.getWidth();  
        int h = matrix.getHeight();  
        int[] rawData = new int[w * h];  
        for (int i = 0; i < w; i++) {  
            for (int j = 0; j < h; j++) {  
                int color = Color.WHITE;  
                if (matrix.get(i, j)) {  
                    color = Color.BLACK;  
                }  
                rawData[i + (j * w)] = color;  
            }  
        }  
        
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);  
        bitmap.setPixels(rawData, 0, w, 0, 0, w, h);  
        return bitmap;  
    }  
	
	private Bitmap generateQRCode(String content) {  
        try {  
            QRCodeWriter writer = new QRCodeWriter();  
            // MultiFormatWriter writer = new MultiFormatWriter();  
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 500, 500);  
            return bitMatrix2Bitmap(matrix);  
        } catch (WriterException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}
