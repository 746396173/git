package com.ximalaya.sdk4j;

import java.util.List;

import com.ximalaya.sdk4j.http.HttpParameter;
import com.ximalaya.sdk4j.model.DTOValidateUtil;
import com.ximalaya.sdk4j.model.Paging;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.album.IncrementAlbum;
import com.ximalaya.sdk4j.model.dto.album.IncrementAlbumList;
import com.ximalaya.sdk4j.model.dto.live.IncrementRadio;
import com.ximalaya.sdk4j.model.dto.live.IncrementRadioList;
import com.ximalaya.sdk4j.model.dto.live.IncrementSchedule;
import com.ximalaya.sdk4j.model.dto.track.IncrementTrack;
import com.ximalaya.sdk4j.model.dto.track.IncrementTrackList;
import com.ximalaya.sdk4j.util.StringUtil;

/**
 * 直播相关接口
 * @author william
 *
 */
public class Increments extends Ximalaya {

	/**
	 * 
	 */
	private static final long serialVersionUID = 523186493386608032L;
	
	/**
	 * 根据分类和标签获取专辑增量（带分页）
	 * 
	 * @param categoryID 分类ID，必填，如果为0则表示所有分类下热门专辑
	 * @param tagName    标签名，可选
	 * @param paging     分页参数，可选，不填则为默认值
	 * @param updateTime 增量更新时间，Unix时间戳毫秒数，update_time最小不能小于距当前时间10分钟
	 * @return
	 * @throws XimalayaException 
	 */
	public IncrementAlbumList getIncrementAlbumList(long categoryID, String tagName, Paging paging, long updateTime) throws XimalayaException {
		DTOValidateUtil.validateCategoryID(categoryID);
		paging = paging == null ? new Paging(): paging;
		
		HttpParameter[] specificParams = null;
		if(!StringUtil.isEmpty(tagName)) {
			specificParams = new HttpParameter[5];
			specificParams[0] = new HttpParameter("category_id", categoryID);
			specificParams[1] = new HttpParameter("tag_name", tagName);
			specificParams[2] = new HttpParameter("page", paging.getPage());
			specificParams[3] = new HttpParameter("count", paging.getCount());
			specificParams[4] = new HttpParameter("update_time", updateTime);
		}
		else {
			specificParams = new HttpParameter[4];
			specificParams[0] = new HttpParameter("category_id", categoryID);
			specificParams[1] = new HttpParameter("page", paging.getPage());
			specificParams[2] = new HttpParameter("count", paging.getCount());
			specificParams[3] = new HttpParameter("update_time", updateTime);
		}
		return IncrementAlbum.constructIncrementAlbumList(
				CLIENT.get(String.format("%s/incr/albums", BASE_URL), 
							assembleHttpParams(specificParams)));
	}
	
	/**
	 * 根据专辑ID获取专辑内声音增量（带分页）
	 * 
	 * @param albumID 专辑ID
	 * @param paging  分页参数，可选，不填则为默认值
	 * @param updateTime 更新时间，Unix时间戳毫秒数，update_time最小不能小于距当前时间10分钟
	 * @return
	 * @throws XimalayaException 
	 */
	public IncrementTrackList getIncrementTrackList(long albumID, Paging paging, long updateTime) throws XimalayaException {
		DTOValidateUtil.validateAlbumID(albumID);
		paging = paging == null ? new Paging(): paging;
		
		HttpParameter[] specificParams = new HttpParameter[4];
		specificParams[0] = new HttpParameter("album_id", albumID);
		specificParams[1] = new HttpParameter("page", paging.getPage());
		specificParams[2] = new HttpParameter("count", paging.getCount());
		specificParams[3] = new HttpParameter("update_time", updateTime);
		return IncrementTrack.constructIncrementTracks(
				CLIENT.get(String.format("%s/incr/tracks", BASE_URL),
						   assembleHttpParams(specificParams)));
	}
	
	/**
	 * 根据电台类型、省份代码
	 * 获取直播电台列表增量
	 * @param radioType 1-国家台，2-省市台，3-网络台
	 * @param provinceCode 省市代码，如果radioType为2则必须指定该参数
	 * @param paging 分页参数，可选，不填则为默认值
	 * @param updateTime 电台更新时间，Unix时间戳毫秒数，update_time最小不能小于距当前时间10分钟
	 * @return
	 * @throws XimalayaException
	 */
	public IncrementRadioList getIncrementRadioList(int radioType, String provinceCode, Paging paging, long updateTime) throws XimalayaException {
		DTOValidateUtil.validateRadioTypeAndProvinceCode(radioType, provinceCode);
		paging = paging == null ? new Paging(): paging;
		
		HttpParameter[] specificParams = null;
		if(!StringUtil.isEmpty(provinceCode)) {
			specificParams = new HttpParameter[5];
			specificParams[0] = new HttpParameter("radio_type", radioType);
			specificParams[1] = new HttpParameter("province_code", provinceCode);
			specificParams[2] = new HttpParameter("page", paging.getPage());
			specificParams[3] = new HttpParameter("count", paging.getCount());
			specificParams[4] = new HttpParameter("update_time", updateTime);
		}
		else {
			specificParams = new HttpParameter[5];
			specificParams[0] = new HttpParameter("radio_type", radioType);
			specificParams[1] = new HttpParameter("page", paging.getPage());
			specificParams[2] = new HttpParameter("count", paging.getCount());
			specificParams[4] = new HttpParameter("update_time", updateTime);
		}
		return IncrementRadio.constructIncrementRadioList(
				CLIENT.get(String.format("%s/incr/radios", BASE_URL),
						    assembleHttpParams(specificParams)));
	}
	
	/**
	 * 根据直播电台ID获取某个直播电台从指定某时刻到现在之间的节目排期增量数据。
	 * @param radioID 直播电台ID
	 * @param weekday 0-星期天，1-星期一，...，6-星期六
	 * @param updateTime 更新时间，Unix时间戳毫秒数，update_time最小不能小于距当前时间10分钟
	 * @return
	 * @throws XimalayaException
	 */
	public List<IncrementSchedule> getIncrementSchedules(long radioID, int weekday,long updateTime) throws XimalayaException {
		DTOValidateUtil.validateRadioID(radioID);
		DTOValidateUtil.validateWeekday(weekday);
		HttpParameter[] specificParameters = new HttpParameter[3];
		specificParameters[0] = new HttpParameter("radio_id", radioID);
		specificParameters[1] = new HttpParameter("weekday", weekday);
		specificParameters[2] = new HttpParameter("update_time", updateTime);
		return IncrementSchedule.constructIncrementSchedules(CLIENT.get(String.format("%s/incr/schedules", BASE_URL),
						    assembleHttpParams(specificParameters)));
	}
}
