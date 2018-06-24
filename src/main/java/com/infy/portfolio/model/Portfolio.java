package com.infy.portfolio.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class Portfolio {

	private Long projectId;
	@NotNull
	private ProjectDetails projectDetails;
	private Date dateRequested;
	private Date dateRequired;
	@NotNull
	private List<KeyContacts> contacts;
	private Integer estimates;
	private String projectType;
	private boolean isCritical;
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public ProjectDetails getProjectDetails() {
		return projectDetails;
	}
	public void setProjectDetails(ProjectDetails projectDetails) {
		this.projectDetails = projectDetails;
	}
	public Date getDateRequested() {
		return dateRequested;
	}
	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}
	public Date getDateRequired() {
		return dateRequired;
	}
	public void setDateRequired(Date dateRequired) {
		this.dateRequired = dateRequired;
	}
	public List<KeyContacts> getContacts() {
		return contacts;
	}
	public void setContacts(List<KeyContacts> contacts) {
		this.contacts = contacts;
	}
	public Integer getEstimates() {
		return estimates;
	}
	public void setEstimates(Integer estimates) {
		this.estimates = estimates;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public boolean isCritical() {
		return isCritical;
	}
	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}
	
	
	
}
