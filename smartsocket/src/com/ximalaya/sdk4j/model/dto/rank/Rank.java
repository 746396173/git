package com.ximalaya.sdk4j.model.dto.rank;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;

public class Rank extends XimalayaResponse{
	private static final long serialVersionUID = 1L;
	
	private String rankKey;
	private String kind;
	private String rankTitle;
	private Integer rankType;
	private String rankSubTitle;
	private Integer rankPeriod;
	private String rankPeriodType;
	private Integer rankItemNum;
	private Integer rankOrderNum;
	private String coverUrl;
	private Integer categoryId;
	private String rankContentType;
	private Long rankFirstItemId;
	private String rankFirstItemTitle;
	private List<RankItem> indexRankItems;
	
	public Rank() {
	}

	public String getRankKey() {
		return rankKey;
	}
	public void setRankKey(String rankKey) {
		this.rankKey = rankKey;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getRankTitle() {
		return rankTitle;
	}
	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}
	public Integer getRankType() {
		return rankType;
	}
	public void setRankType(Integer rankType) {
		this.rankType = rankType;
	}
	public String getRankSubTitle() {
		return rankSubTitle;
	}
	public void setRankSubTitle(String rankSubTitle) {
		this.rankSubTitle = rankSubTitle;
	}
	public Integer getRankPeriod() {
		return rankPeriod;
	}
	public void setRankPeriod(Integer rankPeriod) {
		this.rankPeriod = rankPeriod;
	}
	public String getRankPeriodType() {
		return rankPeriodType;
	}
	public void setRankPeriodType(String rankPeriodType) {
		this.rankPeriodType = rankPeriodType;
	}
	public Integer getRankItemNum() {
		return rankItemNum;
	}
	public void setRankItemNum(Integer rankItemNum) {
		this.rankItemNum = rankItemNum;
	}
	public Integer getRankOrderNum() {
		return rankOrderNum;
	}
	public void setRankOrderNum(Integer rankOrderNum) {
		this.rankOrderNum = rankOrderNum;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getRankContentType() {
		return rankContentType;
	}
	public void setRankContentType(String rankContentType) {
		this.rankContentType = rankContentType;
	}
	public Long getRankFirstItemId() {
		return rankFirstItemId;
	}
	public void setRankFirstItemId(Long rankFirstItemId) {
		this.rankFirstItemId = rankFirstItemId;
	}
	public String getRankFirstItemTitle() {
		return rankFirstItemTitle;
	}
	public void setRankFirstItemTitle(String rankFirstItemTitle) {
		this.rankFirstItemTitle = rankFirstItemTitle;
	}

	public List<RankItem> getIndexRankItems() {
		return indexRankItems;
	}
	public void setIndexRankItems(List<RankItem> indexRankItems) {
		this.indexRankItems = indexRankItems;
	}
	
	public Rank(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	public Rank(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			rankKey = json.getString("rank_key");
			kind = json.getString("kind");
			rankTitle = json.getString("rank_title");
			rankType = json.getInteger("rank_type");
			rankSubTitle = json.getString("rank_sub_title");
			rankPeriod = json.getInteger("rank_period");
			rankPeriodType = json.getString("rank_period_type");
			rankItemNum = json.getInteger("rank_item_num");
			rankOrderNum = json.getInteger("rank_order_num");
			coverUrl = json.getString("cover_url");
			categoryId = json.getInteger("category_id");
			rankContentType = json.getString("rank_content_type");
			rankFirstItemId = json.getLong("rank_first_item_id");
			rankFirstItemTitle = json.getString("rank_first_item_title");
			indexRankItems = parseRankItems(json.getJSONArray("index_rank_items"));
		}
	}
	
	private List<RankItem> parseRankItems(JSONArray jsonArray) throws XimalayaException {
		List<RankItem> ranks = new ArrayList<RankItem>();
		try {
			for(int i = 0; i < jsonArray.size(); i++) {
				ranks.add(new RankItem(jsonArray.getJSONObject(i)));
			}
		}
		catch(XimalayaException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return ranks;
	}
	
	public static RankList constructRankList(HttpResponse response) throws XimalayaException {
		RankList rankList = new RankList();
		try {
			JSONObject jsonObject = response.asJSONObject();
			JSONArray ranksJsonArray = jsonObject.getJSONArray("ranks");
 	 		List<Rank> ranks = new ArrayList<Rank> ();
 	 		for(int i = 0; i < ranksJsonArray.size(); i++) {
 	 			ranks.add(new Rank(ranksJsonArray.getJSONObject(i)));
 	 		}
 	 		rankList.setRanks(ranks);
		} catch (JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return rankList;
	}
	
	public static List<Rank> constructRanks(HttpResponse response) throws XimalayaException {
		List<Rank> ranks = new ArrayList<Rank> ();
		try {
			JSONArray ranksJsonArray = response.asJSONArray();
			int size = ranksJsonArray.size();
			for(int i = 0; i < size; i++) {
				ranks.add(new Rank(ranksJsonArray.getJSONObject(i)));
			}
		} catch (JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return ranks;
	}
}
