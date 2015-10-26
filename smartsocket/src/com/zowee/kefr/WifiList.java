package com.zowee.kefr;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WifiList implements Serializable{

	private Integer id;
    private String text;
    
    public WifiList() {
    }

    public WifiList(Integer id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return text;
		
	}
	

}
