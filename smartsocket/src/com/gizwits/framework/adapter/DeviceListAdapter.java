/**
 * Project Name:XPGSdkV4AppBase
 * File Name:DeviceListAdapter.java
 * Package Name:com.gizwits.framework.adapter
 * Date:2015-1-27 14:46:51
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gizwits.framework.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gizwits.framework.config.Configs;
import com.gizwits.framework.sdk.SettingManager;
import com.gizwits.framework.utils.StringUtils;
import com.gizwits.powersocket.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;


import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * ClassName: Class DeviceListAdapter. <br/>
 * 设备列表适配器 <br/>
 * date: 2015-1-22 15:23:42 <br/>
 * 
 * @author Lien
 */
public class DeviceListAdapter extends BaseAdapter {

    
	/**
     * The Constant VIEW_TYPE_COUNT.
     */
    private static final int VIEW_TYPE_COUNT = 6;

    /**
     * The Constant VIEW_TYPE_LAN.
     */
    public static final int VIEW_TYPE_LAN = 0;

    /**
     * The Constant VIEW_TYPE_WAN.
     */
    public static final int VIEW_TYPE_WAN = 1;

    /**
     * The Constant VIEW_TYPE_OFFLINE.
     */
    public static final int VIEW_TYPE_OFFLINE = 2;

    /**
     * The Constant VIEW_TYPE_UNBIND.
     */
    public static final int VIEW_TYPE_UNBIND = 3;

    /**
     * The Constant VIEW_TYPE_HEADER.
     */
    public static final int VIEW_TYPE_HEADER = 4;

    /** The Constant VIEW_TYPE_EMPTY. */
    public static final int VIEW_TYPE_EMPTY = 5;

    /**
     * The set manager.
     */
    private SettingManager setManager;

    /**
     * The lan devices.
     */
    List<XPGWifiDevice> lanDevices;

    /**
     * The wan devices.
     */
    List<XPGWifiDevice> wanDevices;

    /**
     * The offline devices.
     */
    List<XPGWifiDevice> offlineDevices;

    /**
     * The un bind devices.
     */
    List<XPGWifiDevice> unBindDevices;

    /**
     * The m inflater.
     */
    private LayoutInflater mInflater;

//	/** The current devices. */
//	private List<XPGWifiDevice> currentDevices;

    /**
     * The items.
     */
    private List<TypeItem> items;

    /**
     * The context.
     */
    private Context context;
      
    private String Mac = "";
  //  private int mRightWidth = 0;
    
  //  private int numbers;
    private boolean isdevice = false;

    /**
     * Instantiates a new device list adapter.
     *
     * @param context the context
     * @param devices the devices
     */
    public DeviceListAdapter(Context context, List<XPGWifiDevice> devices) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        setManager = new SettingManager(context);
        lanDevices = new ArrayList<XPGWifiDevice>();
        wanDevices = new ArrayList<XPGWifiDevice>();
        offlineDevices = new ArrayList<XPGWifiDevice>();
        unBindDevices = new ArrayList<XPGWifiDevice>();
        changeDatas(new ArrayList<XPGWifiDevice>());
    }

    /**
     * ClassName: Class TypeItem. <br/>
     * <br/>
     * date: 2015-1-22 15:23:42 <br/>
     *
     * @author Lien
     */
    class TypeItem {

        /**
         * The item type.
         */
        int itemType;

        /**
         * Instantiates a new type item.
         *
         * @param itemType the item type
         */
        private TypeItem(int itemType) {
            this.itemType = itemType;
        }
    }

    /**
     * ClassName: Class DeviceTypeItem. <br/>
     * <br/>
     * date: 2015-1-22 15:23:42 <br/>
     *
     * @author Lien
     */
    class DeviceTypeItem extends TypeItem {

        /**
         * The xpg wifi device.
         */
        XPGWifiDevice xpgWifiDevice;

        /**
         * Instantiates a new device type item.
         *
         * @param type          the type
         * @param xpgWifiDevice the xpg wifi device
         */
        public DeviceTypeItem(int type, XPGWifiDevice xpgWifiDevice) {
            super(type);          
            this.xpgWifiDevice = xpgWifiDevice;
           
        }
    }
    
    
    class UnBindDeviceTypeItem extends TypeItem {

        /**
         * The xpg wifi device.
         */
        XPGWifiDevice xpgWifiDevice;

        /**
         * Instantiates a new device type item.
         *
         * @param type          the type
         * @param xpgWifiDevice the xpg wifi device
         */
        public UnBindDeviceTypeItem(int type, XPGWifiDevice xpgWifiDevice) {
            super(type);
            this.xpgWifiDevice = xpgWifiDevice;
        }
    }

    class LanDeviceTypeItem extends TypeItem {

        /**
         * The xpg wifi device.
         */
        XPGWifiDevice xpgWifiDevice;

        /**
         * Instantiates a new device type item.
         *
         * @param type          the type
         * @param xpgWifiDevice the xpg wifi device
         */
        public LanDeviceTypeItem(int type, XPGWifiDevice xpgWifiDevice) {
            super(type);
            this.xpgWifiDevice = xpgWifiDevice;
        }
    }
    
    
    /**
     *  
     * ClassName: Class EmptyTypeItem. <br/> 
     * <br/>
     * date: 2015-1-27 14:46:51 <br/> 
     *
     * @author Lien
     */
    class EmptyTypeItem extends TypeItem {
        
        /**
         * Instantiates a new empty type item.
         *
         * @param type the type
         */
        public EmptyTypeItem(int type) {
            super(type);

        }
    }

    /**
     * ClassName: Class HeaderTypeItem. <br/>
     * <br/>
     * date: 2015-1-22 15:23:42 <br/>
     *
     * @author Lien
     */
    class HeaderTypeItem extends TypeItem {

        /**
         * The label.
         */
        String label;

        /**
         * Instantiates a new header type item.
         *
         * @param label the label
         */
        public HeaderTypeItem(String label) {
            super(VIEW_TYPE_HEADER);
            this.label = label;
        }
    }

    /**
     * ViewHolder基类，itemView用于查找子view.
     *
     * @author Lien
     */
    class ViewHolder {

        /**
         * The item view.
         */
        View itemView;

        /**
         * Instantiates a new view holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView can not be null!");
            }
            this.itemView = itemView;
        }
    }

    /**
     * 设备列表ViewHolder.
     *
     * @author Lien
     */
    class DeviceViewHolder extends ViewHolder {
    	
    	/**
         * The background.
         */
        LinearLayout background;

        /**
         * The icon.
         */
        ImageView icon;

        /**
         * The name.
         */
        TextView name;

        /**
         * The statue.
         */
        TextView statue;

        /**
         * The arrow.
         */
        ImageView arrow;
        

        /**
         * Instantiates a new device view holder.
         *
         * @param view the view
         */
        public DeviceViewHolder(View view) {
            super(view);
            background = (LinearLayout) view.findViewById(R.id.bg);
            icon = (ImageView) view.findViewById(R.id.icon);
            arrow = (ImageView) view.findViewById(R.id.arrow);
            name = (TextView) view.findViewById(R.id.name);
            statue = (TextView) view.findViewById(R.id.statue);
        
        }
    }
    
    class UnBindDeviceViewHolder extends ViewHolder {
    	
    	/**
         * The background.
         */
        LinearLayout background;

        /**
         * The icon.
         */
        ImageView icon;

        /**
         * The name.
         */
        TextView name;

   
        
     
        public UnBindDeviceViewHolder(View view) {
            super(view);
            background = (LinearLayout) view.findViewById(R.id.bg);
            icon = (ImageView) view.findViewById(R.id.icon);
            name = (TextView) view.findViewById(R.id.name);
  
        }
    }
    
    class LanDeviceViewHolder extends ViewHolder {
    	
    	/**
         * The background.
         */
        LinearLayout background;

        /**
         * The icon.
         */
        ImageView icon;

        /**
         * The name.
         */
        TextView name;

   
        
     
        public LanDeviceViewHolder(View view) {
            super(view);
            background = (LinearLayout) view.findViewById(R.id.bg);
            icon = (ImageView) view.findViewById(R.id.icon);
            name = (TextView) view.findViewById(R.id.name);
  
        }
    }

    /**
     *  
     * ClassName: Class DeviceEmptyHolder. <br/> 
     * <br/>
     * date: 2015-1-27 14:46:52 <br/> 
     *
     * @author Lien
     */
    class DeviceEmptyHolder extends ViewHolder {
        
        /**
         * Instantiates a new device empty holder.
         *
         * @param view the view
         */
        public DeviceEmptyHolder(View view) {
            super(view);
        }
    }


    /**
     * 头部ViewHolder.
     *
     * @author Lien
     */
    
    class HeaderViewHolder extends ViewHolder {

      
       // TextView label;
        
        public HeaderViewHolder(View view) {
            super(view);
           // label = (TextView) view.findViewById(R.id.label);
        }
    }
    

    /**
     * Generate items.
     *
     * @return the list
     */
    private List<TypeItem> generateItems() {
        List<TypeItem> items = new ArrayList<TypeItem>();
       //items.add(new HeaderTypeItem("在线设备"));
        isdevice = false;
        if (unBindDevices.size() > 0) {
        	isdevice = true;
            for (XPGWifiDevice device : unBindDevices) {
                items.add(new UnBindDeviceTypeItem(VIEW_TYPE_UNBIND, device));
            }
           
        }
        
        if (lanDevices.size() > 0) {
        	isdevice = true;
        	 for (XPGWifiDevice device : lanDevices) {
        		 items.add(new LanDeviceTypeItem(VIEW_TYPE_LAN, device));
        	 }
        }
        
        if (wanDevices.size() > 0) {
        	isdevice = true;
        	/*
            for (XPGWifiDevice device : lanDevices) {
                items.add(new DeviceTypeItem(VIEW_TYPE_LAN, device));
            }
            */
            for (XPGWifiDevice device : wanDevices) {
                items.add(new DeviceTypeItem(VIEW_TYPE_WAN, device));
            } 
        }
        /**
        else {
            items.add(new EmptyTypeItem(VIEW_TYPE_EMPTY));
        }
        */
        //items.add(new HeaderTypeItem("离线设备"));
        if (offlineDevices.size() > 0) {
        	isdevice = true;
            for (XPGWifiDevice device : offlineDevices) {
                items.add(new DeviceTypeItem(VIEW_TYPE_OFFLINE, device));
            }
        } 
        /**
        else {
            items.add(new EmptyTypeItem(VIEW_TYPE_EMPTY));
        }
        */
        //items.add(new HeaderTypeItem("未绑定设备"));
       
        /**
        else {
            items.add(new EmptyTypeItem(VIEW_TYPE_EMPTY));
        }
        */
        if (isdevice != true){
        	items.add(new EmptyTypeItem(VIEW_TYPE_EMPTY));
        }
        return items;
    }

//    /**
//     * Generate items.
//     *
//     * @param devices the devices
//     * @return the list
//     */
//    private List<TypeItem> generateItems(List<XPGWifiDevice> devices) {
//        List<TypeItem> items = new ArrayList<TypeItem>();
//        int size = devices == null ? 0 : devices.size();
//        String currLabel;
//        String preLabel = "{";
//        XPGWifiDevice device;
//        for (int i = 0; i < size; i++) {
//            device = devices.get(i);
//            if (device.isBind(setManager.getUid()) && device.isLAN()) {
//                currLabel = "在线设备";
//                if (i == 0 || !currLabel.equals(preLabel)) {
//                    items.add(new HeaderTypeItem("在线设备"));
//                    preLabel = currLabel;
//                }
//                items.add(new DeviceTypeItem(VIEW_TYPE_LAN, device));
//            } else if (device.isBind(setManager.getUid()) && device.isOnline()) {
//                currLabel = "在线设备";
//                if (i == 0 || !currLabel.equals(preLabel)) {
//                    items.add(new HeaderTypeItem("在线设备"));
//                    preLabel = currLabel;
//                }
//                items.add(new DeviceTypeItem(VIEW_TYPE_WAN, device));
//            } else if (device.isBind(setManager.getUid()) && !device.isOnline()) {
//                currLabel = "离线设备";
//                if (i == 0 || !currLabel.equals(preLabel)) {
//                    items.add(new HeaderTypeItem("离线设备"));
//                    preLabel = currLabel;
//                }
//                items.add(new DeviceTypeItem(VIEW_TYPE_OFFLINE, device));
//            } else {
//                currLabel = "未绑定设备";
//                if (i == 0 || !currLabel.equals(preLabel)) {
//                    items.add(new HeaderTypeItem("未绑定设备"));
//                    preLabel = currLabel;
//                }
//                items.add(new DeviceTypeItem(VIEW_TYPE_UNBIND, device));
//            }
//        }
//        return items;
//    }


    /**
     * Change datas.
     *
     * @param devices the devices
     */
    public void changeDatas(List<XPGWifiDevice> devices) {
        lanDevices.clear();
        wanDevices.clear();
        offlineDevices.clear();
        unBindDevices.clear();

        for (XPGWifiDevice device : devices) {

            if (device.isLAN()) {
                if (device.isBind(setManager.getUid())) {
                    //lanDevices.add(device);
                	if (!Mac.equals("") && Mac != null && device.getMacAddress().equals(Mac)){
                		offlineDevices.add(device);
                	}
                } else {
                	if (!Mac.equals("") && Mac != null && device.getMacAddress().equals(Mac)){
                		unBindDevices.add(device);
                	}
                }
            } else {
                if (!device.isOnline()) {
                    offlineDevices.add(device);
                } else {
                    wanDevices.add(device);
                }
            }
        }
        this.items = generateItems();

        notifyDataSetChanged();
    }


    /* (non-Javadoc)
     * @see android.widget.BaseAdapter#getItemViewType(int)
     */
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) != null) {
            return items.get(position).itemType;
        }
        return super.getItemViewType(position);
    }

    /* (non-Javadoc)
     * @see android.widget.BaseAdapter#getViewTypeCount()
     */
    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        if (items != null && position > 0 && position < items.size()) {
            return items.get(position);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Gets the device by position.
     *
     * @param position the position
     * @return the device by position
     */
    public XPGWifiDevice getDeviceByPosition(int position) {
        if (items.get(position).itemType == VIEW_TYPE_HEADER||items.get(position).itemType == VIEW_TYPE_EMPTY) {
            return null;
        } else if (items.get(position).itemType == VIEW_TYPE_UNBIND ){
        	 UnBindDeviceTypeItem deviceTypeItem = (UnBindDeviceTypeItem) items
                     .get(position);
             return deviceTypeItem.xpgWifiDevice;
        }else if (items.get(position).itemType == VIEW_TYPE_LAN ){
        	LanDeviceTypeItem deviceTypeItem = (LanDeviceTypeItem) items
                    .get(position);
        	return deviceTypeItem.xpgWifiDevice;
        }else{
        	 DeviceTypeItem deviceTypeItem = (DeviceTypeItem) items
                     .get(position);
             return deviceTypeItem.xpgWifiDevice;
        }
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TypeItem item = items.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            // 根据不同的viewType，初始化不同的布局
            switch (getItemViewType(position)) {
            /*
                case VIEW_TYPE_HEADER:
                    viewHolder = new HeaderViewHolder(mInflater.inflate(
                            R.layout.device_list_item_header, null));
                    break;
			*/
                case VIEW_TYPE_LAN:
                	viewHolder = new LanDeviceViewHolder(mInflater.inflate(
                				R.layout.unbinddevicelist, null));    
                	break;
                case VIEW_TYPE_WAN:
                case VIEW_TYPE_OFFLINE:
                //case VIEW_TYPE_UNBIND:
                    viewHolder = new DeviceViewHolder(mInflater.inflate(
                            R.layout.device_list_item, null));
                    
                    break;
                //case VIEW_TYPE_LAN:
                case VIEW_TYPE_UNBIND:
                		viewHolder = new UnBindDeviceViewHolder(mInflater.inflate(
                				R.layout.unbinddevicelist, null));             	
                	break;
                case VIEW_TYPE_EMPTY:
                    viewHolder = new DeviceEmptyHolder(mInflater.inflate(
                            R.layout.device_list_item_empty, null));
                    break;

                default:
                    throw new IllegalArgumentException("invalid view type : "
                            + getItemViewType(position));
            }

            // 缓存header与item视图
            convertView = viewHolder.itemView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       
        /**
        // 根据初始化的不同布局，绑定数据
        if (viewHolder instanceof HeaderViewHolder) {
            //((HeaderViewHolder) viewHolder).label.setText(String.valueOf(((HeaderTypeItem) item).label));
        } else
        */
         if (viewHolder instanceof UnBindDeviceViewHolder){
        	
        	OnUnBindDeviceIem((UnBindDeviceViewHolder) viewHolder,
                    ((UnBindDeviceTypeItem) item).xpgWifiDevice);
        }else if (viewHolder instanceof DeviceViewHolder) {        	
            onBindDeviceItem((DeviceViewHolder) viewHolder,
                    ((DeviceTypeItem) item).xpgWifiDevice);
            
        }else if (viewHolder instanceof LanDeviceViewHolder){
        	
        	OnLanDeviceIem((LanDeviceViewHolder) viewHolder,
                     ((LanDeviceTypeItem) item).xpgWifiDevice);
        }
        return convertView;
    }
    
    private void OnLanDeviceIem(LanDeviceViewHolder viewHolder, XPGWifiDevice device){
	   	
    	String DeviceName="";
		if(StringUtils.isEmpty(device.getRemark())){
			String macAddress=device.getMacAddress();
			int size=macAddress.length();
			DeviceName=device.getProductName() + macAddress.substring(size-4, size);
		}else{
			DeviceName=device.getRemark();
		}
		DeviceName=StringUtils.getStrFomat(DeviceName, Configs.DEVICE_NAME_KEEP_LENGTH, true);
		viewHolder.name.setText(DeviceName);
		
		if (Configs.PRODUCT_KEY[1].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
    	}else if (Configs.PRODUCT_KEY[0].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon2);
    	}else if (Configs.PRODUCT_KEY[2].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
    	}else if (Configs.PRODUCT_KEY[3].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
    	}else if (Configs.PRODUCT_KEY[4].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
    	}
		 
		if (landevice != null){
			
			landevice.OnLanDeviceItemClick(this, device);
		}
    }
    
    
    private void OnUnBindDeviceIem(UnBindDeviceViewHolder viewHolder, XPGWifiDevice device){
    	   	
    	String DeviceName="";
		if(StringUtils.isEmpty(device.getRemark())){
			String macAddress=device.getMacAddress();
			int size=macAddress.length();
			DeviceName=device.getProductName() + macAddress.substring(size-4, size);
		}else{
			DeviceName=device.getRemark();
		}
		DeviceName=StringUtils.getStrFomat(DeviceName, Configs.DEVICE_NAME_KEEP_LENGTH, true);
		viewHolder.name.setText(DeviceName);
		
		if (Configs.PRODUCT_KEY[1].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
    	}else if (Configs.PRODUCT_KEY[0].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon2);
    	}else if (Configs.PRODUCT_KEY[2].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
    	}else if (Configs.PRODUCT_KEY[3].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
    	}else if (Configs.PRODUCT_KEY[4].equals(device.getProductKey())){
    		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
    	}
		    		   
		if (mbinddevice != null){    			
    			mbinddevice.OnBindDeviceItemClick(this, device);
    	}
  	
    }
	

    /**
     * On bind device item.
     *
     * @param viewHolder the view holder
     * @param device     the device
     */
    private void onBindDeviceItem(DeviceViewHolder viewHolder,
                                  XPGWifiDevice device) {//XPGWifiDevice
    	
    	String DeviceName="";
    	if(StringUtils.isEmpty(device.getRemark())){
    		String macAddress=device.getMacAddress();
			int size=macAddress.length();
    		DeviceName=device.getProductName() + macAddress.substring(size-4, size);
    	}else{
        	DeviceName=device.getRemark();
    	}
    	DeviceName=StringUtils.getStrFomat(DeviceName, Configs.DEVICE_NAME_KEEP_LENGTH, true);
    	viewHolder.name.setText(DeviceName);
    	
        if (device.isLAN()) {
        	
        	/**
            if (device.isBind(setManager.getUid())) {
              //  viewHolder.icon.setImageResource(R.drawable.device_icon_blue);
            	if (Configs.PRODUCT_KEY[1].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
            	}else if (Configs.PRODUCT_KEY[0].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon2);
            	}
            	
                viewHolder.name.setTextColor(context.getResources().getColor(
                        R.color.text_blue));
                viewHolder.statue.setText("局域网在线");
                viewHolder.arrow.setVisibility(View.VISIBLE);
                viewHolder.arrow.setImageResource(R.drawable.arrow_right_blue);
            } 
            */
        	
            /**
            else {
            	
            	
               // viewHolder.icon.setImageResource(R.drawable.device_icon_gray);
            	if (Configs.PRODUCT_KEY[1].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
            	}else if (Configs.PRODUCT_KEY[0].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon2);
            	}
                viewHolder.name.setTextColor(context.getResources().getColor(
                        R.color.text_gray));
                viewHolder.statue.setText("未绑定");
                viewHolder.arrow.setVisibility(View.VISIBLE);
                viewHolder.arrow.setImageResource(R.drawable.arrow_right_gray);
                           
            }
            */
        } else {
            if (!device.isOnline()) {
              //  viewHolder.icon.setImageResource(R.drawable.device_icon_gray);
            	if (Configs.PRODUCT_KEY[1].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon11down);
            	}else if (Configs.PRODUCT_KEY[0].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon2_down);
            	}else if (Configs.PRODUCT_KEY[2].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon11down);
            	}else if (Configs.PRODUCT_KEY[3].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon11down);
            	}else if (Configs.PRODUCT_KEY[4].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon11down);
            	}
                viewHolder.name.setTextColor(context.getResources().getColor(
                        R.color.text_gray));
                viewHolder.statue.setText("离线");
                viewHolder.arrow.setVisibility(View.INVISIBLE);
                viewHolder.background.setBackgroundResource(R.color.transparent);
                viewHolder.arrow.setImageResource(R.drawable.arrow_right_gray);                           
                
            } else {
              //  viewHolder.icon.setImageResource(R.drawable.device_icon_blue);
            	if (Configs.PRODUCT_KEY[1].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
            	}else if (Configs.PRODUCT_KEY[0].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon2);
            	}else if (Configs.PRODUCT_KEY[2].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
            	}else if (Configs.PRODUCT_KEY[3].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
            	}else if (Configs.PRODUCT_KEY[4].equals(device.getProductKey())){
            		viewHolder.icon.setImageResource(R.drawable.head_icon_11);
            	}
                viewHolder.name.setTextColor(context.getResources().getColor(
                        R.color.text_blue));
                viewHolder.statue.setText("在线");
                viewHolder.arrow.setVisibility(View.VISIBLE);
                viewHolder.arrow.setImageResource(R.drawable.arrow_right_blue);
            }
        }

    }
    //setmanager.getResbyMacAndDid(device.getMacAddress(), device.getDid())
    
    private OnBindDeviceClickListener mbinddevice = null;
    
    public interface OnBindDeviceClickListener{
    	
    	void OnBindDeviceItemClick(DeviceListAdapter adapter, XPGWifiDevice tempDevice);
    }
    
    public void SetOnBindDeviceItemClickListener(OnBindDeviceClickListener binddevice){
    	
    	this.mbinddevice = binddevice;
    	
    }
    
    public void SetDeviceMac(String Mac){
    	this.Mac = Mac;
    }
    
    private OnLanDeviceClickListener landevice = null;
    
    public interface OnLanDeviceClickListener{
    	
    	void OnLanDeviceItemClick(DeviceListAdapter adapter, XPGWifiDevice tempDevice);
    }
    
    public void SetOnLanDeviceItemClick(OnLanDeviceClickListener landevice){
    	this.landevice  = landevice;
    }
    
 }
