package com.ximalaya.sdk4j;

import java.util.List;

import com.ximalaya.sdk4j.http.HttpParameter;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.AllList;
import com.ximalaya.sdk4j.model.Paging;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.album.Album;
import com.ximalaya.sdk4j.model.dto.album.AlbumList;
import com.ximalaya.sdk4j.model.dto.live.Radio;
import com.ximalaya.sdk4j.model.dto.live.RadioList;
import com.ximalaya.sdk4j.model.dto.search.HotWord;
import com.ximalaya.sdk4j.model.dto.search.SuggestWordList;
import com.ximalaya.sdk4j.model.dto.track.Track;
import com.ximalaya.sdk4j.model.dto.track.TrackList;

/**
 * 搜索，包括专辑搜索和声音搜索
 * @author will
 *
 */
public class Searches extends Ximalaya {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2133121637009516256L;
	
	/**
	 * 搜索专辑
	 * 
	 * @param q 搜索词，必填
	 * @param categoryID 分类ID，必填，如果为0则表示所有分类下的专辑
	 * @param paging 分页参数，可选，不填则为默认值
	 * @return
	 * @throws XimalayaException
	 */
	public AlbumList searchAlbumList(String q, long categoryID,  Paging paging) throws XimalayaException {
		checkSearchParam(q, categoryID);
		paging = paging == null ? new Paging(): paging;
		return Album.constructAlbumList(
				CLIENT.get(String.format("%s/search/albums", BASE_URL),
						   assembleHttpParams(constructSpecificParamsForSearch(q, categoryID, paging))));
	}
	
	/**
	 * 搜索声音
	 * 
	 * @param q  搜索词，必填
	 * @param categoryID 分类ID，必填，如果为0则表示所有分类下的声音
	 * @param paging 分页参数，可选，不填则为默认值
	 * @return
	 * @throws XimalayaException
	 */
	public TrackList searchTrackList(String q, long categoryID, Paging paging) throws XimalayaException {
		checkSearchParam(q, categoryID);
		paging = paging == null ? new Paging(): paging;
		return Track.constructTrackList(
				CLIENT.get(String.format("%s/search/tracks", BASE_URL),
						assembleHttpParams(constructSpecificParamsForSearch(q, categoryID, paging))));
	}
	
	/**
	 * 获取最近top的热搜词
	 * 
	 * @param top 热搜词，必填
	 * @return
	 * @throws XimalayaException
	 */
	public List<HotWord> searchHotWords(int top) throws XimalayaException {
		checkSearchTopParam(top);
		return HotWord.constructHotWords(
				CLIENT.get(String.format("%s/search/hot_words", BASE_URL),
						assembleHttpParams(new HttpParameter[]{new HttpParameter("top", top)})));
	}
	
	/**
	 * 获取某个关键词的联想词
	 * 
	 * @param q 关键词，必填
	 * @return
	 * @throws XimalayaException
	 */
	public SuggestWordList searchSuggestWords(String keyWord) throws XimalayaException{
		checkSearchKeyWordParam(keyWord);
		return new SuggestWordList(CLIENT.get(String.format("%s/search/suggest_words", BASE_URL),
				assembleHttpParams(new HttpParameter[]{new HttpParameter("q", keyWord)})));
	}

	/**
	 * 按照关键词搜索直播。
	 * @param q
	 * @param pageSize
	 * @param page
	 * @return
	 * @throws XimalayaException
	 */
	public RadioList searchRadios(String q, int pageSize, int page) throws XimalayaException {
		HttpResponse response= CLIENT.get(
				String.format("%s/search/radios",BASE_URL),
				assembleHttpParams(
					new HttpParameter[]{
					new HttpParameter("q", q),
					new HttpParameter("count", pageSize),
					new HttpParameter("page", page)}
				)
		);
		return Radio.constructRadioList(response);
	}

	/**
	 * 获取指定数量直播，声音，专辑的内容。
	 * @param q
	 * @param pageSize
	 * @param page
	 * @return
	 * @throws XimalayaException
	 */
	public AllList searchAll(String q, int pageSize, int page) throws XimalayaException {
		HttpResponse response= CLIENT.get(
			String.format("%s/search/all",BASE_URL),
			assembleHttpParams(
				new HttpParameter[]{
					new HttpParameter("q", q),
					new HttpParameter("count", pageSize),
					new HttpParameter("page", page)}
			)
		);
		return new AllList(response);
	}

	private void checkSearchKeyWordParam(String keyWord) {
		if(keyWord == null || keyWord.isEmpty()) {
			throw new IllegalArgumentException("keyWord should not empty");
		}
	}
	
	private void checkSearchTopParam(int top) {
		if(top < 1 || top > 20) {
			throw new IllegalArgumentException("top should >= 1 and <= 20");
		}
	}
	
	private void checkSearchParam(String q, long categoryID) {
		if(q == null || q.isEmpty()) {
			throw new IllegalArgumentException("q should not empty");
		}
		if(categoryID < 0) {
			throw new IllegalArgumentException("category_id should >= 0");
		}
	}
	
	private HttpParameter[] constructSpecificParamsForSearch(String q, long categoryID,  Paging paging) {
		HttpParameter[] specificParams = new HttpParameter[4];
		specificParams[0] = new HttpParameter("q", q);
		specificParams[1] = new HttpParameter("category_id", categoryID);
		specificParams[2] = new HttpParameter("page", paging.getPage());
		specificParams[3] = new HttpParameter("count", paging.getCount());
		return specificParams;
	}
}
