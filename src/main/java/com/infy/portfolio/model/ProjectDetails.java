package com.infy.portfolio.model;

import javax.validation.constraints.NotNull;

public class ProjectDetails {

	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private String summary;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
