package com.ximalaya.sdk4j.model.dto.album;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;

import java.util.ArrayList;
import java.util.List;


public class ReletiveAlbum extends Album{
	private static final long serialVersionUID = 1L;
	
	private String recommendSrc;     // 用于统计，推荐来源
    private String recommendTrace;   // 推荐跟踪

    public ReletiveAlbum() {
	}
    
    public String getRecommendSrc() {
        return recommendSrc;
    }

    public void setRecommendSrc(String recommendSrc) {
        this.recommendSrc = recommendSrc;
    }

    public String getRecommendTrace() {
        return recommendTrace;
    }

    public void setRecommendTrace(String recommendTrace) {
        this.recommendTrace = recommendTrace;
    }

    public ReletiveAlbum(JSONObject json) throws XimalayaException {
    	super(json);
        init(json);
    }

    private void init(JSONObject json) throws XimalayaException {
        if (json != null) {
            recommendSrc = json.getString("recommend_src");
            recommendTrace = json.getString("recommend_trace");
        }
    }

    public static ReletiveAlbumList constructReletiveAlbumList(HttpResponse response) throws XimalayaException {
        ReletiveAlbumList reletiveAlbumList = new ReletiveAlbumList();
        JSONObject albumListJsonObject = response.asJSONObject();
        reletiveAlbumList.setTotalPage(albumListJsonObject.getIntValue("total_page"));
        reletiveAlbumList.setTotalCount(albumListJsonObject.getIntValue("total_count"));
        reletiveAlbumList.setCurrentPage(albumListJsonObject.getIntValue("current_page"));
        	
        List<ReletiveAlbum> reletiveAlbums = new ArrayList<ReletiveAlbum>();
        JSONArray albumsJsonArray = albumListJsonObject.getJSONArray("reletive_albums");
        for (int i = 0; i < albumsJsonArray.size(); i++) {
        	reletiveAlbums.add(new ReletiveAlbum(albumsJsonArray.getJSONObject(i)));
        }
       	reletiveAlbumList.setReletiveAlbum(reletiveAlbums);
        return reletiveAlbumList;
    }
}
