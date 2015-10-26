package com.ximalaya.sdk4j;

import com.ximalaya.sdk4j.http.HttpParameter;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.DTOValidateUtil;
import com.ximalaya.sdk4j.model.Paging;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.album.*;
import com.ximalaya.sdk4j.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 专辑相关接口
 *
 * @author will
 */
public class Albums extends Ximalaya {
    /**
     *
     */
    private static final long serialVersionUID = -8651526987345601726L;

    private static final List<Album> EMPTY_ALBUMS = new ArrayList<Album>(0);
    private static final List<UpdatedAlbum> EMPTY_UPDATEDALBUMS = new ArrayList<UpdatedAlbum>(0);

    /**
     * 根据分类和标签获取热门专辑（带分页）
     *
     * @param categoryID 分类ID，必填，如果为0则表示所有分类下热门专辑
     * @param tagName    标签名，可选
     * @param paging     分页参数，可选，不填则为默认值
     * @return
     * @throws XimalayaException
     */
    public AlbumList getHotAlbumList(long categoryID, String tagName, Paging paging) throws XimalayaException {
        DTOValidateUtil.validateCategoryID(categoryID);
        paging = paging == null ? new Paging() : paging;

        HttpParameter[] specificParams = null;
        if (!StringUtil.isEmpty(tagName)) {
            specificParams = new HttpParameter[4];
            specificParams[0] = new HttpParameter("category_id", categoryID);
            specificParams[1] = new HttpParameter("tag_name", tagName);
            specificParams[2] = new HttpParameter("page", paging.getPage());
            specificParams[3] = new HttpParameter("count", paging.getCount());
        } else {
            specificParams = new HttpParameter[3];
            specificParams[0] = new HttpParameter("category_id", categoryID);
            specificParams[1] = new HttpParameter("page", paging.getPage());
            specificParams[2] = new HttpParameter("count", paging.getCount());
        }
        return Album.constructAlbumList(
                CLIENT.get(String.format("%s/albums/hot", BASE_URL),
                        assembleHttpParams(specificParams)));
    }

    /**
     * 获取所有人工推荐分类下的热门专辑，每个分类下返回的专辑个数固定。
     * @param count 每个人工推荐分类下返回专辑的数量
     * @return
     * @throws XimalayaException
     */
    public List<HumanRecommendAlbumList> getHumanRecommendAlbumList(int count) throws XimalayaException {
    	if(count <= 0) {
    		return new ArrayList<HumanRecommendAlbumList>(0);
    	}
        HttpParameter[] specificParams = new HttpParameter[2];
        specificParams[0] = new HttpParameter("page", 1);
        specificParams[1] = new HttpParameter("count", count);
        return Album.constructHumanRecommendAlbumList(
                CLIENT.get(String.format("%s/albums/human_recommend", BASE_URL),
                        assembleHttpParams(specificParams)));
    }
    
    /**
     * 获取下载听模块热门下载推荐专辑
     * @param calcDimension 计算维度，现支持经典（0），最火（1），最新（2），播放最多（3）
     * @param paging  		分页参数，可选，不填则为默认值
     * @return
     * @throws XimalayaException
     */
    public AlbumList getRecommendDownloadAlbumList(int calcDimension, Paging paging) throws XimalayaException {
    	DTOValidateUtil.validateCalcDimension(calcDimension);
    	paging = paging == null ? new Paging() : paging;
    	
        HttpParameter[] specificParams = new HttpParameter[3];
        specificParams[0] = new HttpParameter("page", paging.getPage());
        specificParams[1] = new HttpParameter("count", paging.getCount());
        specificParams[2] = new HttpParameter("calc_dimension", calcDimension);
        return Album.constructAlbumList(
                CLIENT.get(String.format("%s/albums/recommend_download", BASE_URL),
                        assembleHttpParams(specificParams)));
    }

    /**
     * 根据一组ID批量获取专辑
     *
     * @param albumIDs 一组专辑ID，必填
     * @return
     * @throws XimalayaException
     */
    public List<Album> batchGetAlbums(long[] albumIDs) throws XimalayaException {
        if (albumIDs == null || albumIDs.length == 0) {
            return EMPTY_ALBUMS;
        }

        HttpParameter[] specificParams = new HttpParameter[]{new HttpParameter("ids", StringUtil.join(albumIDs, ","))};
        return Album.constructAlbums(
                CLIENT.get(String.format("%s/albums/get_batch", BASE_URL),
                        assembleHttpParams(specificParams)));
    }

    /**
     * 根据分类和标签获取指定分类下所有带版权的专辑（带分页）
     *
     * @param categoryID 分类ID，必填，如果为0则表示所有分类下热门专辑
     * @param paging     分页参数，可选，不填则为默认值
     * @return
     * @throws XimalayaException
     */
    public AlbumList getAllCopyrightAlbumList(long categoryID, Paging paging) throws XimalayaException {
        DTOValidateUtil.validateCategoryID(categoryID);
        paging = paging == null ? new Paging() : paging;

        HttpParameter[] specificParams = null;
        specificParams = new HttpParameter[3];
        specificParams[0] = new HttpParameter("category_id", categoryID);
        specificParams[1] = new HttpParameter("page", paging.getPage());
        specificParams[2] = new HttpParameter("count", paging.getCount());
        return Album.constructAlbumList(
                CLIENT.get(String.format("%s/albums/get_all", BASE_URL),
                        assembleHttpParams(specificParams)));
    }

    /**
     * 根据分类和标签获取指定分类下所有带版权的专辑（带分页）
     *
     * @param categoryID 分类ID，必填，如果为0则表示所有分类下热门专辑
     * @param paging     分页参数，可选，不填则为默认值
     * @param tagName    标签名，可选
     * @return
     * @throws XimalayaException
     */
    public AlbumList getAllCopyrightAlbumList(long categoryID, String tagName, Paging paging) throws XimalayaException {
        DTOValidateUtil.validateCategoryID(categoryID);
        paging = paging == null ? new Paging() : paging;

        HttpParameter[] specificParams = null;
        if (!StringUtil.isEmpty(tagName)) {
            specificParams = new HttpParameter[4];
            specificParams[0] = new HttpParameter("category_id", categoryID);
            specificParams[1] = new HttpParameter("tag_name", tagName);
            specificParams[2] = new HttpParameter("page", paging.getPage());
            specificParams[3] = new HttpParameter("count", paging.getCount());
        } else {
            specificParams = new HttpParameter[3];
            specificParams[0] = new HttpParameter("category_id", categoryID);
            specificParams[1] = new HttpParameter("page", paging.getPage());
            specificParams[2] = new HttpParameter("count", paging.getCount());
        }
        return Album.constructAlbumList(
                CLIENT.get(String.format("%s/albums/get_all", BASE_URL),
                        assembleHttpParams(specificParams)));
    }
    
    /**
     * 根据专辑ID获取专辑内声音（即浏览专辑）
     *
     * @param albumID 专辑ID
     * @param paging
     * @return
     * @throws XimalayaException
     */
    public AlbumTracks browseAlbumTracks(long albumID, Paging paging) throws XimalayaException {
        DTOValidateUtil.validateAlbumID(albumID);
        paging = paging == null ? new Paging() : paging;

        HttpParameter[] specificParams = new HttpParameter[3];
        specificParams[0] = new HttpParameter("album_id", albumID);
        specificParams[1] = new HttpParameter("page", paging.getPage());
        specificParams[2] = new HttpParameter("count", paging.getCount());
        return Album.constructAlbumTracks(
                CLIENT.get(String.format("%s/albums/browse", BASE_URL),
                        assembleHttpParams(specificParams)));
    }

    /**
     * 根据专辑ID列表获取批量专辑更新提醒信息列表
     *
     * @param albumIDs 专辑ID
     * @return
     * @throws XimalayaException
     */
    public List<UpdatedAlbum> getUpdatedAlbums(long[] albumIDs) throws XimalayaException {
        if (albumIDs == null || albumIDs.length == 0) {
            return EMPTY_UPDATEDALBUMS;
        }

        HttpParameter[] specificParams = new HttpParameter[]{new HttpParameter("ids", StringUtil.join(albumIDs, ","))};
        return UpdatedAlbum.constructUpdatedAlbums(
                CLIENT.get(String.format("%s/albums/get_update_batch", BASE_URL),
                        assembleHttpParams(specificParams)));
    }

    /**
     * 获取某个专辑的相关推荐专辑
     * @param id 专辑ID
     * @return
     * @throws XimalayaException
     */
    public ReletiveAlbumList getReletiveAlbums(Long id) throws XimalayaException {
    	if (id == null || id <= 0) {
            return new ReletiveAlbumList();
        }
    	HttpParameter[] specificParams = new HttpParameter[]{new HttpParameter("albumId", id)};
        HttpResponse response = CLIENT.get(String.format("%s/albums/relative_album", BASE_URL),
             assembleHttpParams(specificParams));
        return ReletiveAlbum.constructReletiveAlbumList(response);
    }


    /**
     * 某分类下的热门专辑聚合接口
     * @param categoryId	分类id
     * @param tagCount		取该分类下前几个标签，默认为4（包括最火）
     * @param albumCount	获取每个标签下的专辑数量，默认为3
     * @return
     * @throws XimalayaException
     */
    public List<AlbumList> getHotAggregation(Long categoryId, Integer tagCount, Integer albumCount) throws XimalayaException {
       DTOValidateUtil.validateCategoryID(categoryId);
       HttpParameter[] specificParams = new HttpParameter[]{
           new HttpParameter("category_id", categoryId),
           new HttpParameter("tag_count", tagCount),
           new HttpParameter("album_count", albumCount)};
       HttpResponse response = CLIENT.get(String.format("%s/albums/hot_aggregation", BASE_URL),
           assembleHttpParams(specificParams));
       return Album.constructAlbumListList(response);

    }
}
