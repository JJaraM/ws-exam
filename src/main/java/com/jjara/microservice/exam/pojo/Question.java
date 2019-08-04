package com.jjara.microservice.exam.pojo;

import java.util.List;

public class Question {

	private String text;
	private List<Option> options;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	
	
	
	
}
