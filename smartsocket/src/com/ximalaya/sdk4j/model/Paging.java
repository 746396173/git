package com.ximalaya.sdk4j.model;

/**
 * 分页参数
 * @author will
 */
public class Paging implements java.io.Serializable {
	private static final long serialVersionUID = -3285857427993796670L;
	
	/*
	 * 分页参数配置
	 */
	public static final int MIN_COUNT = 1;
	public static final int MAX_COUNT = 100;
	public static final int MIN_PAGE = 1;
	public static final int DEFAULT_COUNT = 20;
	public static final int DEFAULT_PAGE = 1;
	
	private static final String SET_COUNT_ERROR_MSG = String.format("count should >= %d and <= %d", MIN_COUNT, MAX_COUNT);
	private static final String SET_PAGE_ERROR_MSG = String.format("page should >= %d", MIN_PAGE);
	
	private int count = DEFAULT_COUNT;   // 每页数目，默认为20，最多返回100条
	private int page = DEFAULT_PAGE;     // 查询第几页，默认为1
   
    public Paging() {
    }

    public Paging(int page) {
        setPage(page);
    }

    public Paging(int page, int count) {
        this(page);
        setCount(count);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page < MIN_PAGE) {
            throw new IllegalArgumentException(SET_PAGE_ERROR_MSG);
        }
        this.page = page;
    }
    
    public Paging page(int page) {
    	setPage(page);
    	return this;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count < MIN_COUNT || count > MAX_COUNT) {
            throw new IllegalArgumentException(SET_COUNT_ERROR_MSG);
        }
        this.count = count;
    }

    public Paging count(int count) {
        setCount(count);
        return this;
    }
}
