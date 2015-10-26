package com.ximalaya.sdk4j.model.dto;

public abstract class AbstractPageResult {
	
	protected Integer totalPage;    // 鎬婚〉鏁�
	protected Integer totalCount;   // 澹伴煶鎬绘暟
	protected Integer currentPage;  // 褰撳墠椤�
	
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
}
