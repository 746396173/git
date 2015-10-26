package com.ximalaya.sdk4j.model.dto.column;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;
import com.ximalaya.sdk4j.model.dto.album.Album;
import com.ximalaya.sdk4j.model.dto.profile.Editor;
import com.ximalaya.sdk4j.model.dto.track.Track;

public class Column extends XimalayaResponse{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String kind;
	private String columnTitle;
	private String columnSubTitle;
	private String columnIntro;
	private String columnFootNote;
	private String coverUrlSmall;
	private String coverUrlLarge;
	private String logoSmall;
	private Integer columnContentType;
	private Long releasedAt;
	private Boolean isHot;
	private Editor columnEditor;
	private List<?> columnItems;
	
	public Column() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getColumnTitle() {
		return columnTitle;
	}
	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}
	public String getColumnSubTitle() {
		return columnSubTitle;
	}
	public void setColumnSubTitle(String columnSubTitle) {
		this.columnSubTitle = columnSubTitle;
	}
	public String getColumnIntro() {
		return columnIntro;
	}
	public void setColumnIntro(String columnIntro) {
		this.columnIntro = columnIntro;
	}
	public String getColumnFootNote() {
		return columnFootNote;
	}
	public void setColumnFootNote(String columnFootNote) {
		this.columnFootNote = columnFootNote;
	}
	public String getCoverUrlSmall() {
		return coverUrlSmall;
	}
	public void setCoverUrlSmall(String coverUrlSmall) {
		this.coverUrlSmall = coverUrlSmall;
	}
	public String getCoverUrlLarge() {
		return coverUrlLarge;
	}
	public void setCoverUrlLarge(String coverUrlLarge) {
		this.coverUrlLarge = coverUrlLarge;
	}
	public String getLogoSmall() {
		return logoSmall;
	}
	public void setLogoSmall(String logoSmall) {
		this.logoSmall = logoSmall;
	}
	public Integer getColumnContentType() {
		return columnContentType;
	}
	public void setColumnContentType(Integer columnContentType) {
		this.columnContentType = columnContentType;
	}
	public Long getReleasedAt() {
		return releasedAt;
	}
	public void setReleasedAt(Long releasedAt) {
		this.releasedAt = releasedAt;
	}
	public Boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public Editor getColumnEditor() {
		return columnEditor;
	}
	public void setColumnEditor(Editor columnEditor) {
		this.columnEditor = columnEditor;
	}
	public List<?> getColumnItems() {
		return columnItems;
	}
	public void setColumnItems(List<?> columnItems) {
		this.columnItems = columnItems;
	}
	
	public Column(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	public Column(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			id = json.getLong("id");
			kind = json.getString("kind");
			columnTitle = json.getString("column_title");
			columnSubTitle = json.getString("column_sub_title");
			columnIntro  = json.getString("column_intro");
			columnFootNote = json.getString("column_foot_note");
			coverUrlSmall = json.getString("cover_url_small");
			coverUrlLarge = json.getString("cover_url_large");
			logoSmall = json.getString("logo_small");
			columnContentType  = json.getIntValue("column_content_type");
			releasedAt  = json.getLong("released_at");
			isHot = json.getBooleanValue("is_hot");
			columnEditor = json.getObject("column_editor", Editor.class);
			columnItems = parseColumnItems(json.getJSONArray("column_items"), columnContentType);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private List<?> parseColumnItems(JSONArray cloumnJsonArray, int columnContentType) throws XimalayaException {
		if(cloumnJsonArray != null) {
			if(columnContentType == 1) {
				List<Album> columnList = new ArrayList<Album>();
				for(int i = 0; i < cloumnJsonArray.size(); i++) {
					columnList.add(new Album(cloumnJsonArray.getJSONObject(i)));
				}
				return columnList;
			}
			else if(columnContentType == 2) {
				List<Track> trackList = new ArrayList<Track>();
				for(int i = 0; i < cloumnJsonArray.size(); i++) {
					trackList.add(new Track(cloumnJsonArray.getJSONObject(i)));
				}
				return trackList;
			}
			else {
				throw new XimalayaException("can find a matched columnContentType");
			}
		}
		else {
			return new ArrayList();
		}
	}
	
	public static ColumnList constructColumnList(HttpResponse response) throws XimalayaException {
		ColumnList columnList = new ColumnList();
		JSONObject columnListJsonObject = response.asJSONObject();
 		try {
 			columnList.setTotalPage(columnListJsonObject.getIntValue("total_page"));
 	 		columnList.setTotalCount(columnListJsonObject.getIntValue("total_count"));
 	 		columnList.setCurrentPage(columnListJsonObject.getIntValue("current_page"));
 	 			
 	 		List<Column> columns = new ArrayList<Column> ();
 	 		JSONArray albumsJsonArray = columnListJsonObject.getJSONArray("columns");
 	 		for(int i = 0; i < albumsJsonArray.size(); i++) {
 	 			columns.add(new Column(albumsJsonArray.getJSONObject(i)));
 	 		}
 	 		columnList.setColumns(columns);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return columnList;
	}
}
