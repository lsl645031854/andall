package com.andall.sally.supply.utils;

import java.util.List;

public class Convert {
	private String value;
	private String label;
	private List<Children> children;
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setChildren(List<Children> children) {
		this.children = children;
	}
	
	public List<Children> getChildren() {
		return this.children;
	}
}

class Children {
	private String value;
	private String label;
	private List<Children> children;
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setChildren(List<Children> children) {
		this.children = children;
	}
	
	public List<Children> getChildren() {
		return this.children;
	}
}
