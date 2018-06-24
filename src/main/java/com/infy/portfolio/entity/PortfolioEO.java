package com.infy.portfolio.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PORTFOLIO")
public class PortfolioEO implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROJECT_ID", nullable = false)
	private Long projectId;
	
	@Column(name = "PROJECT_NAME", length = 50, nullable = false)
	private String projectName;
	
	@Column(name = "PROJECT_DESC", length = 100 , nullable = false)
	private String projectDescription;
	
	@Column(name = "PROJECT_SUMMARY", length = 250)
	private String projectSummary;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_REQUESTED")
	private Date dateRequest;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_REQUIRED")
	private Date dateRequired;
	
	@Column(name="PROJECT_ESTIMATES")
	private Integer estimates;
	
	@Column(name="PROJECT_TYPE", length = 50)
	private String projectType;
	
	@Column(name="CRITICAL", length = 1)
	private String critical;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "portfolioEO")
	private Set<ContactsEO> keyContactDetails = new HashSet<ContactsEO>(0);

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectSummary() {
		return projectSummary;
	}

	public void setProjectSummary(String projectSummary) {
		this.projectSummary = projectSummary;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public Date getDateRequired() {
		return dateRequired;
	}

	public void setDateRequired(Date dateRequired) {
		this.dateRequired = dateRequired;
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

	public String getCritical() {
		return critical;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}
	
	public Set<ContactsEO> getKeyContactDetails() {
		return keyContactDetails;
	}

	public void setKeyContactDetails(Set<ContactsEO> keyContactDetails) {
		this.keyContactDetails = keyContactDetails;
	}
	
	@Override
	public String toString() {
		return "PortfolioEO [projectId=" + projectId + ", projectName=" + projectName + ", projectDescription="
				+ projectDescription + ", projectSummary=" + projectSummary + ", dateRequest=" + dateRequest
				+ ", dateRequired=" + dateRequired + ", estimates=" + estimates + ", projectType=" + projectType
				+ ", critical=" + critical + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((critical == null) ? 0 : critical.hashCode());
		result = prime * result + ((dateRequest == null) ? 0 : dateRequest.hashCode());
		result = prime * result + ((dateRequired == null) ? 0 : dateRequired.hashCode());
		result = prime * result + ((estimates == null) ? 0 : estimates.hashCode());
		result = prime * result + ((projectDescription == null) ? 0 : projectDescription.hashCode());
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((projectSummary == null) ? 0 : projectSummary.hashCode());
		result = prime * result + ((projectType == null) ? 0 : projectType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortfolioEO other = (PortfolioEO) obj;
		if (critical == null) {
			if (other.critical != null)
				return false;
		} else if (!critical.equals(other.critical))
			return false;
		if (dateRequest == null) {
			if (other.dateRequest != null)
				return false;
		} else if (!dateRequest.equals(other.dateRequest))
			return false;
		if (dateRequired == null) {
			if (other.dateRequired != null)
				return false;
		} else if (!dateRequired.equals(other.dateRequired))
			return false;
		if (estimates == null) {
			if (other.estimates != null)
				return false;
		} else if (!estimates.equals(other.estimates))
			return false;
		if (projectDescription == null) {
			if (other.projectDescription != null)
				return false;
		} else if (!projectDescription.equals(other.projectDescription))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (projectSummary == null) {
			if (other.projectSummary != null)
				return false;
		} else if (!projectSummary.equals(other.projectSummary))
			return false;
		if (projectType == null) {
			if (other.projectType != null)
				return false;
		} else if (!projectType.equals(other.projectType))
			return false;
		return true;
	}	
	
}
