package com.ximalaya.sdk4j;

import com.ximalaya.sdk4j.http.HttpParameter;
import com.ximalaya.sdk4j.model.DTOValidateUtil;
import com.ximalaya.sdk4j.model.Paging;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.column.Column;
import com.ximalaya.sdk4j.model.dto.column.ColumnList;

public class Columns extends Ximalaya {
	private static final long serialVersionUID = -8651526987345601726L;

	/**
	 * 获取精品听单列表
	 * @param paging 分页参数，可选，不填则为默认值
	 * @return
	 * @throws XimalayaException
	 */
	public ColumnList getQualityColumnList(Paging paging) throws XimalayaException {
		paging = paging == null ? new Paging(): paging;
		HttpParameter[] specificParams = new HttpParameter[3];
		specificParams[0] = new HttpParameter("page", paging.getPage());
		specificParams[1] = new HttpParameter("count", paging.getCount());
		specificParams[2] = new HttpParameter("device_id", "andorid");
		return Column.constructColumnList(
			CLIENT.get(String.format("%s/column/quality_list", BASE_URL), 
					assembleHttpParams(specificParams)));
	}
	
	/**
	 * 获取精品听单列表
	 * @return	听单ID
	 * @throws XimalayaException
	 */
	public Column getColumnDetail(Integer columnId) throws XimalayaException {
		DTOValidateUtil.validateColumnID(columnId);
		HttpParameter[] specificParams = new HttpParameter[2];
		specificParams[0] = new HttpParameter("id", columnId);
		specificParams[1] = new HttpParameter("device_id", "andorid");
		return new Column(
			CLIENT.get(String.format("%s/column/detail", BASE_URL), 
					assembleHttpParams(specificParams)));
	}
}
