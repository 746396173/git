package com.ximalaya.sdk4j.model.dto.live;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will.lin on 2015/9/14.
 */
public class City extends XimalayaResponse {

    /**
     *
     */
    private static final long serialVersionUID = 7557254279857972145L;

    private Long id;                // 省市ID
    private String kind;            // DTO实体类型
    private String cityName;
    private int cityCode;           // 城市code (国家行政规划的城市代码)
    private Long createdAt;         // 更新时间
    private Long updateAt;          // 更新时间

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    public City(){}

    public City(HttpResponse response) throws XimalayaException {
        super(response);
        init(response.asJSONObject());
    }

    public City(JSONObject json) throws XimalayaException {
        super();
        init(json);
    }

    private void init(JSONObject json) throws XimalayaException {
        if(json != null) {
            id = json.getLong("id");
            kind = json.getString("kind");
            cityCode = json.getIntValue("city_code");
            cityName = json.getString("city_name");
            createdAt = json.getLong("created_at");
            updateAt = json.getLong("update_at");
        }
    }


    public static List<City> constructCities(HttpResponse response) throws XimalayaException {
        List<City> citys = new ArrayList<City>();
        JSONArray cityJsonArray = response.asJSONArray();
        try {
            for(int i = 0; i < cityJsonArray.size(); i++) {
                citys.add(new City(cityJsonArray.getJSONObject(i)));
            }
        } catch(JSONException jsone) {
            throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
        }
        return citys;
    }
}
