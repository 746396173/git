package com.ximalaya.sdk4j;

import java.util.List;

import com.ximalaya.sdk4j.http.HttpParameter;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.tag.Tag;

/**
 * 标签相关接口
 * @author will
 *
 */
public class Tags extends Ximalaya {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3845650700794416855L;
	
	/**
	 * 根据分类获取专辑标签
	 * 
	 * @param categoryID 分类ID，必填
	 * @return
	 * @throws XimalayaException 
	 */
	public List<Tag> getAlbumTags(long categoryID) throws XimalayaException {
		return getTags(categoryID, 0);
	}
	
	/**
	 * 根据分类获取声音标签
	 * 
	 * @param categoryID 分类ID，必填
	 * @return
	 * @throws XimalayaException
	 */
	public List<Tag> getTrackTags(long categoryID) throws XimalayaException {
		return getTags(categoryID, 1);
	}
	
	/**
	 * 根据分类和类型获取标签
	 * 
	 * @param categoryID 分类ID，必填
	 * @param type       类型，必填，0-专辑标签，1-声音标签
	 * @return
	 * @throws XimalayaException
	 */
	public List<Tag> getTags(long categoryID, int type) throws XimalayaException {
		checkCategoryIDAndType(categoryID, type);
		
		HttpParameter[] specificParams = new HttpParameter[2];
		specificParams[0] = new HttpParameter("category_id", categoryID);
		specificParams[1] = new HttpParameter("type", type);
		return Tag.constructTags(
				CLIENT.get(String.format("%s/tags/list", BASE_URL),
						    assembleHttpParams(specificParams)));
	}
	
	private void checkCategoryIDAndType(long categoryID, int type) {
		if(categoryID < 0) {
			throw new IllegalArgumentException("categoryID should >= 0");
		}
		if(type != 0 && type != 1) {
			throw new IllegalArgumentException("type should be 0(album tag) or 1(track tag)");
		}
	}
}
