package com.ximalaya.sdk4j;

import java.util.List;

import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.dto.category.Category;
import com.ximalaya.sdk4j.model.dto.category.RecommendCategory;

/**
 * 喜马拉雅内容分类
 * @author will
 *
 */
public class Categories extends Ximalaya {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5338362069555289938L;
	
	/**
	 * 获取喜马拉雅分类
	 * 
	 * @return
	 * @throws XimalayaException
	 */
	public List<Category> getCategories() throws XimalayaException {
		return Category.constructCategories(
				CLIENT.get(String.format("%s/categories/list", BASE_URL), assembleHttpParams()));
	}

	/**
	 * 获取喜马拉雅运营人员在首页推荐的专辑分类
	 * @return
	 * @throws XimalayaException
	 */
	public List<RecommendCategory> getRecommendCategories() throws XimalayaException {
		return RecommendCategory.constructRecommendCategories(
				CLIENT.get(String.format("%s/categories/human_recommend", BASE_URL), assembleHttpParams()));
	}
}
