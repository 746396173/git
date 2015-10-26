package com.ximalaya.sdk4j;

import java.util.Date;
import java.util.List;

import android.util.Log;

import com.ximalaya.sdk4j.http.HttpParameter;
import com.ximalaya.sdk4j.model.DTOValidateUtil;
import com.ximalaya.sdk4j.model.Paging;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.live.City;
import com.ximalaya.sdk4j.model.dto.live.Program;
import com.ximalaya.sdk4j.model.dto.live.Province;
import com.ximalaya.sdk4j.model.dto.live.Radio;
import com.ximalaya.sdk4j.model.dto.live.RadioList;
import com.ximalaya.sdk4j.model.dto.live.Schedule;
import com.ximalaya.sdk4j.util.StringUtil;

/**
 * 直播相关接口
 * @author will
 *
 */
public class Lives extends Ximalaya {

	/**
	 * 
	 */
	private static final long serialVersionUID = 523186493386608032L;
	
	/**
	 * 获取直播省市列表
	 * @return
	 * @throws XimalayaException
	 */
	public List<Province> getProvinces() throws XimalayaException {
		return Province.constructProvinces(
				CLIENT.get(String.format("%s/live/provinces", BASE_URL), assembleHttpParams()));
	}
	
	/**
	 * 根据电台类型、省份代码（由上面的<code>getProvinces()</code>获得）
	 * 获取直播电台列表
	 * @param radioType 1-国家台，2-省市台，3-网络台
	 * @param provinceCode 省市代码，如果radioType为2则必须指定该参数
	 * @param paging 分页参数
	 * @return
	 * @throws XimalayaException
	 */
	public RadioList getRadioList(int radioType, String provinceCode, Paging paging) throws XimalayaException {
		DTOValidateUtil.validateRadioTypeAndProvinceCode(radioType, provinceCode);
		paging = paging == null ? new Paging(): paging;
		
		HttpParameter[] specificParams = null;
		if(!StringUtil.isEmpty(provinceCode)) {
			specificParams = new HttpParameter[4];
			specificParams[0] = new HttpParameter("radio_type", radioType);
			specificParams[1] = new HttpParameter("province_code", provinceCode);
			specificParams[2] = new HttpParameter("page", paging.getPage());
			specificParams[3] = new HttpParameter("count", paging.getCount());
		}
		else {
			specificParams = new HttpParameter[3];
			specificParams[0] = new HttpParameter("radio_type", radioType);
			specificParams[1] = new HttpParameter("page", paging.getPage());
			specificParams[2] = new HttpParameter("count", paging.getCount());
		}
		return Radio.constructRadioList(
				CLIENT.get(String.format("%s/live/radios", BASE_URL),
						    assembleHttpParams(specificParams)));
	}
	
	/**
	 * 根据直播电台ID获取今天的节目时间表
	 * @param radioID 直播电台ID
	 * @return
	 * @throws XimalayaException
	 */
	@SuppressWarnings("deprecation")
	public List<Schedule> getSchedules(long radioID) throws XimalayaException {
		return getSchedules(radioID, new Date().getDay());
	}
	
	/**
	 * 根据直播电台ID获取指定weekday的节目时间表
	 * @param radioID 直播电台ID
	 * @param weekday 0-星期天，1-星期一，...，6-星期六
	 * @return
	 * @throws XimalayaException
	 */
	public List<Schedule> getSchedules(long radioID, int weekday) throws XimalayaException {
		DTOValidateUtil.validateRadioID(radioID);
		DTOValidateUtil.validateWeekday(weekday);
		HttpParameter[] specificParameters = new HttpParameter[2];
		specificParameters[0] = new HttpParameter("radio_id", radioID);
		specificParameters[1] = new HttpParameter("weekday", weekday);
		return Schedule.constructSchedules(CLIENT.get(String.format("%s/live/schedules", BASE_URL),
			assembleHttpParams(specificParameters)));
	}
	
	/**
	 * 根据直播电台ID获取对应直播电台正在直播的节目详情
	 * @param radioID 直播电台ID
	 * @return
	 * @throws XimalayaException
	 */
	public Program getPlayingProgram(long radioID) throws XimalayaException {
		DTOValidateUtil.validateRadioID(radioID);
		return new Program(
			CLIENT.get(String.format("%s/live/get_playing_program", BASE_URL), 
					assembleHttpParams(new HttpParameter[] { new HttpParameter("radio_id", radioID) })));
	}
	
	/**
	 * 获取某省份城市列表。
	 * @param provinceCode	省份code (国家行政规划的省代码)
	 * @return
	 * @throws XimalayaException
	 */
	public List<City> getCityByProvince(int provinceCode)throws XimalayaException{
		HttpParameter[] specificParameters = new HttpParameter[1];
		specificParameters[0] = new HttpParameter("province_code", provinceCode);
		return City.constructCities(
			CLIENT.get(String.format("%s/live/cities", BASE_URL), assembleHttpParams(specificParameters)));
	}

	/**
	 * 获取某个城市下的电台列表。
	 * @param cityCode	城市code (国家行政规划的城市代码)
	 * @param paging	分页参数，可选，不填则为默认值
	 * @return
	 * @throws XimalayaException
	 */
	public RadioList getRadiosByCity(int cityCode, Paging paging)throws XimalayaException{
		paging = paging == null ? new Paging() : paging;
		HttpParameter[] specificParameters = new HttpParameter[3];
		specificParameters[0] = new HttpParameter("city_code", cityCode);
		specificParameters[1] = new HttpParameter("page", paging.getPage());
		specificParameters[2] = new HttpParameter("count", paging.getCount());
		return Radio.constructRadioList(
			CLIENT.get(String.format("%s/live/get_radios_by_city", BASE_URL), assembleHttpParams(specificParameters)));
	}
	
	/** 
	 * @param url 路径
	 * @return Image
	 * @throws XimalayaException 
	 * 
	 */
	
	public String GetImage(String url) throws XimalayaException{
		Log.i("GetImageString", "GetImageString3");
		return CLIENT.get(url, null).asJSONObject().toString();
	}
}
