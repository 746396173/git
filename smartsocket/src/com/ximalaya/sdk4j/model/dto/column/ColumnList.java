package com.ximalaya.sdk4j.model.dto.column;

import java.util.List;

import com.ximalaya.sdk4j.model.dto.AbstractPageResult;

public class ColumnList extends AbstractPageResult {

	private List<Column> columns;	// 听单列表
	
	public ColumnList() {
	}

	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
