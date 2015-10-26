package com.ximalaya.sdk4j.model.dto.rank;

import java.util.List;

import com.ximalaya.sdk4j.model.dto.AbstractPageResult;
import com.ximalaya.sdk4j.model.dto.rank.Rank;

public class RankList extends AbstractPageResult{
	
	private List<Rank> ranks;

	public List<Rank> getRanks() {
		return ranks;
	}

	public void setRanks(List<Rank> ranks) {
		this.ranks = ranks;
	}
}
