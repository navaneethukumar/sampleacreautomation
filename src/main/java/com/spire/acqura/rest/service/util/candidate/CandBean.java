package com.spire.acqura.rest.service.util.candidate;

public class CandBean {

	private boolean socialData;
	private boolean locationGeo;
	private boolean crmData;
	private boolean social;
	private boolean crm;
	private boolean entityAddressBean;

	private String displayId;
	private int skill;
	private int education;
	private int employer;
	private int project;

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public int getEmployer() {
		return employer;
	}

	public void setEmployer(int employer) {
		this.employer = employer;
	}

	public int getProject() {
		return project;
	}

	public void setProject(int project) {
		this.project = project;
	}

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public boolean isSocialData() {
		return socialData;
	}

	public void setSocialData(boolean socialData) {
		this.socialData = socialData;
	}

	public boolean isLocationGeo() {
		return locationGeo;
	}

	public void setLocationGeo(boolean locationGeo) {
		this.locationGeo = locationGeo;
	}

	public boolean isCrmData() {
		return crmData;
	}

	public void setCrmData(boolean crmData) {
		this.crmData = crmData;
	}

	public boolean isSocial() {
		return social;
	}

	public void setSocial(boolean social) {
		this.social = social;
	}

	public boolean isCrm() {
		return crm;
	}

	public void setCrm(boolean crm) {
		this.crm = crm;
	}

	public boolean isEntityAddressBean() {
		return entityAddressBean;
	}

	public void setEntityAddressBean(boolean entityAddressBean) {
		this.entityAddressBean = entityAddressBean;
	}

}
