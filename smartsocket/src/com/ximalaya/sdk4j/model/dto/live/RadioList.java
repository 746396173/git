package com.ximalaya.sdk4j.model.dto.live;

import java.io.Serializable;
import java.util.List;

import com.ximalaya.sdk4j.model.dto.AbstractPageResult;

public class RadioList extends AbstractPageResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3894839919178264063L;
	
	private List<Radio> radios;
	
	public RadioList() {
	}
	
	public List<Radio> getRadios() {
		return radios;
	}
	public void setRadios(List<Radio> radios) {
		this.radios = radios;
	}
}
