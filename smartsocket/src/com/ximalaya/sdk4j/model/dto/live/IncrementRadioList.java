package com.ximalaya.sdk4j.model.dto.live;

import java.util.List;

import com.ximalaya.sdk4j.model.dto.AbstractPageResult;

public class IncrementRadioList extends AbstractPageResult {

	private List<IncrementRadio> radios;
	
	public IncrementRadioList() {
	}
	
	public List<IncrementRadio> getRadios() {
		return radios;
	}
	public void setRadios(List<IncrementRadio> radios) {
		this.radios = radios;
	}
	
}
