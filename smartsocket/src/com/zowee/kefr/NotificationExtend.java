package com.zowee.kefr;

import com.gizwits.powersocket.R;
import com.xpg.common.useful.ByteUtils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;

public class NotificationExtend {
	
	private Activity context;
	private boolean onoff;
	private boolean dealy;
	private boolean time_start;
	private boolean time_end;
	private String name;
	private int tmp;
	
    public NotificationExtend(Activity context, String name) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.name    = name;     
    }  
    
    
    public boolean isOnoff() {
		return onoff;
	}


	public void setOnoff(boolean onoff) {
		this.onoff = onoff;
	}


	public boolean isDealy() {
		return dealy;
	}


	public void setDealy(boolean dealy) {
		this.dealy = dealy;
	}


	public boolean isstartTime() {
		return time_start;
	}


	public void setstartTime(boolean time) {
		this.time_start = time;
	}

	public void setendTime(boolean time) {
		this.time_end = time;
	}
	public boolean isendTime() {
		return time_end;
	}
	public int getTmp() {
		return tmp;
	}


	public void setTmp(int tmp) {
		this.tmp = tmp;
	}


	// 显示Notification
    public void showNotification() {
    	String onoff_string = " ";
    	String dealy_string = " ";
    	String time_start_string = " ";
    	String time_end_string = " ";
        // 创建一个NotificationManager的引用
        NotificationManager notificationManager = (
                NotificationManager)context.getSystemService(
                		context.NOTIFICATION_SERVICE);      
        // 定义Notification的各种属性
        Notification notification = new Notification();
        notification.icon = R.drawable.head_icon2;
        notification.tickerText = name+"发来一条新的消息，请查看！";
        //
        notification.defaults = Notification.DEFAULT_SOUND; //默认提示音
        //手机振动
       // notification.defaults |= Notification.DEFAULT_VIBRATE; 
        long[] vibrate = {0,100,200,300}; 
        notification.vibrate = vibrate ;


        /*****************************************************************/
        // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
        //notification.flags |= Notification.FLAG_ONGOING_EVENT;
        //让声音、振动无限循环，直到用户响应  
        notification.flags |= Notification.FLAG_INSISTENT;
        // 表明在点击了通知栏中的"清除通知"后，此通知自动清除。
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.defaults = Notification.DEFAULT_LIGHTS;
        notification.ledARGB = Color.BLUE;
        notification.ledOnMS = 5000;
        // 设置通知的事件消息
        CharSequence contentTitle = name+"的信息"; // 通知栏标题
        for (int i = 0; i < 4; i++){
        	//ByteUtils.getBitFromShort(tmp, i);
        	if (i == 0 && ByteUtils.getBitFromShort(tmp, i)){
        		onoff_string = onoffsata();
        	}
        	if (i == 1 && ByteUtils.getBitFromShort(tmp, i)){
        		dealy_string = dealysata();	
        	}else if (i == 2 && ByteUtils.getBitFromShort(tmp, i)){

        		time_start_string = timestartsata();
        	}
        	if (i == 3 && ByteUtils.getBitFromShort(tmp, i)){
        		time_end_string  = timeendsata();
        	}
        }
        CharSequence contentText = ""+onoff_string+""+dealy_string+""+time_start_string+""+time_end_string; // 通知栏内容      
       // Intent notificationIntent = new Intent(context,context.getClass());
       // Context context = getApplicationContext();
       // Intent notificationIntent =new Intent(BringToFrontReceiver.ACTION_BRING_TO_FRONT);  

        Intent notificationIntent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
       // notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
       // notificationIntent.setAction(Intent.ACTION_MAIN);
        //notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
       // PendingIntent contentIntent = PendingIntent.getActivity(
        // context, 0, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
       // notification.setLatestEventInfo( context, contentTitle, contentText, contentIntent);
         //把Notification传递给NotificationManager
       notificationManager.notify(100, notification);
    }
	// 取消通知
    public void cancelNotification(){
        NotificationManager notificationManager = (
                NotificationManager) context.getSystemService(
                        android.content.Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }
	
    
    private String onoffsata(){
    	String stat;
    
    	if (onoff){
    		stat = "请注意：设备已经打开,";
    	}else{
    		stat = "请注意：设备已经关闭,";
    	}
    	return stat;
    }
    
    private String dealysata(){
    	String stat;
    	if (dealy){
    		stat = "延时开始时间到了!";
    	}else{
    		stat = "延时结束时间到了!";
    	}
    	return stat;
    }
    
    private String timestartsata(){
    	String stat = "";
    	if (time_start){
    		stat = "预约开始时间到了!";
    	}
    	return stat;
    }
    private String timeendsata(){
    	String stat = "";
    	if (time_end){
    		stat = "预约结束时间到了!";
    	}
    	return stat;
    } 
  

}
