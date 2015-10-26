package com.ximalaya.sdk4j;

import java.util.ArrayList;
import java.util.List;



import com.ximalaya.sdk4j.http.HttpParameter;
import com.ximalaya.sdk4j.model.Paging;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.track.Track;
import com.ximalaya.sdk4j.model.dto.track.TrackList;
import com.ximalaya.sdk4j.util.StringUtil;

/**
 * 声音相关接口
 * @author will
 *
 */
public class Tracks extends Ximalaya {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6472908914256459025L;
	
	private static final List<Track> EMPTY_TRACKS = new ArrayList<Track> (0);
	
	/**
	 * 根据分类和标签获取热门声音（带分页）
	 * 
	 * @param categoryID 分类ID，必填，如果为0则表示所有分类下热门声音
	 * @param tagName    标签名，可选
	 * @param paging     分页参数，可选，如果为null则为默认分页参数
	 * @return
	 * @throws XimalayaException 
	 */
	public TrackList getHotTrackList(long categoryID, String tagName, Paging paging) throws XimalayaException {
		checkCategoryID(categoryID);
		paging = paging == null ? new Paging(): paging;
		
		HttpParameter[] specificParams = null;
		if(tagName != null && !tagName.isEmpty()) {
			specificParams = new HttpParameter[4];
			specificParams[0] = new HttpParameter("category_id", categoryID);
			specificParams[1] = new HttpParameter("tag_name", tagName);
			specificParams[2] = new HttpParameter("page", paging.getPage());
			specificParams[3] = new HttpParameter("count", paging.getCount());
		}
		else {
			specificParams = new HttpParameter[3];
			specificParams[0] = new HttpParameter("category_id", categoryID);
			specificParams[1] = new HttpParameter("page", paging.getPage());
			specificParams[2] = new HttpParameter("count", paging.getCount());
		}
		return Track.constructTrackList(
				CLIENT.get(String.format("%s/tracks/hot", BASE_URL),
						   assembleHttpParams(specificParams)));
	}
	
	/**
	 * 根据上一次所听声音的id，搜索给定数量的数据。
	 * @param pid     主播ID
	 * @param albumId 专辑ID
	 * @param trackId 声音ID
	 * @param count   需要数据的条数
	 * @return
	 * @throws XimalayaException
	 */
	public TrackList getLastPlayTracks(Long pid, Long albumId, Long trackId,int count) throws XimalayaException{
		HttpParameter[] specificParams = new HttpParameter[4];
			specificParams[0] = new HttpParameter("pid", pid);
			specificParams[1] = new HttpParameter("album_id", albumId);
			specificParams[2] = new HttpParameter("track_id", trackId);
			specificParams[3] = new HttpParameter("count", count);
		return Track.constructTrackList(
				CLIENT.get(String.format("%s/tracks/get_last_play_tracks", BASE_URL),
							assembleHttpParams(specificParams)));
	}
	
	/**
	 * 根据一组声音ID批量获取声音
	 * 
	 * @param trackIDs 一组声音ID，必填
	 * @return
	 * @throws XimalayaException
	 */
	public List<Track> batchGetTracks(long[] trackIDs) throws XimalayaException {
		if(trackIDs == null || trackIDs.length == 0) {
			return EMPTY_TRACKS; 
		}
		
		HttpParameter[] specificParams = new HttpParameter[] { new HttpParameter("ids", StringUtil.join(trackIDs, ",")) };
		return Track.constructTracks(
				CLIENT.get(String.format("%s/tracks/get_batch", BASE_URL),
						   assembleHttpParams(specificParams)));
	}
	
	private void checkCategoryID(long categoryID) {
		if(categoryID < 0) {
			throw new IllegalArgumentException("categoryID should >= 0");
		}
	}

}
