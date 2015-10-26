package com.zowee.kefr;

public class ProviceNameAndCode {

	private String ProviceName;
	private Integer ProviceCode;
	
	public ProviceNameAndCode(String ProviceName, Integer ProviceCode){
		this.ProviceName = ProviceName;
		this.ProviceCode = ProviceCode;
	}

	public String getProviceName() {
		return ProviceName;
	}

	public void setProviceName(String proviceName) {
		ProviceName = proviceName;
	}

	public Integer getProviceCode() {
		return ProviceCode;
	}

	public void setProviceCode(Integer proviceCode) {
		ProviceCode = proviceCode;
	}
	
	
}
