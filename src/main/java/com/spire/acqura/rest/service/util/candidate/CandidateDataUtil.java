package com.spire.acqura.rest.service.util.candidate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.gson.Gson;

import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.profileservice.beans.CRM;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateEducationMapBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidateProjectMapBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;
import spire.talent.entity.profileservice.beans.Certification;
import spire.talent.entity.profileservice.beans.EntityAddressMapBean;
import spire.talent.entity.profileservice.beans.Social;
import spire.talent.entity.profileservice.beans.SocialESBean;
import spire.talent.entity.profileservice.beans.SocialExt;

public class CandidateDataUtil {

	static private final Logger _logger = LoggerFactory.getLogger(CandidateDataUtil.class);
	static String generateUniqueId = "_" + String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits()));

	public static CandidateBean createCandiateData(CandBean candBean) {
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setDisplayId(generateUniqueId);
		candidateBean.setFirstName("auto" + generateUniqueId);
		candidateBean.setLastName("last" + generateUniqueId);
		candidateBean.setCurrentRole("autoRole" + generateUniqueId);
		candidateBean.setCurrentEmployer("autoEmployer" + generateUniqueId);
		candidateBean.setTotalExperienceMnth((short) 60);
		candidateBean.setNoticePeriodDays((short) 30);
		candidateBean.setPrimaryEmailId("pradeep.siluveru@spire2grow.com");
		candidateBean.setPrimaryContactNumber("9611537884");
		candidateBean.setLocationName("Bangalore");
		candidateBean.setSourceName("autoSourceName" + generateUniqueId);
		candidateBean.setSourceType("autoSourceType" + generateUniqueId);
		candidateBean.setCurrentCtc(20);
		candidateBean.setExpectedCtc(25);

		if (candBean.isSocialData()) {
			Social social = new Social();
			// hobbies
			Set<String> setHobbies = new HashSet<String>();
			setHobbies.add("Books Reading");
			setHobbies.add("Playing Cricket");
			social.setHobbies(setHobbies);
			// interests
			Set<String> setInterests = new HashSet<String>();
			setInterests.add("Cooking");
			setInterests.add("Programming");
			social.setInterests(setInterests);
			// socialExt
			SocialExt socialExt = new SocialExt();
			socialExt.setContactInformationAvailable("yes");
			social.setSocialExt(socialExt);
			candidateBean.setSocialData(social);

		}
		if (candBean.isSocial()) {
			SocialESBean socialESBean = new SocialESBean();
			socialESBean.setLinkedinUrl("https://www.linkedin.com/in/" + candidateBean.getDisplayId());
			candidateBean.setSocial(socialESBean);
			List<Certification> listOfCerti = new ArrayList<>();
			Certification certification = new Certification();
			certification.setName(generateUniqueId);
			listOfCerti.add(certification);
			socialESBean.setCertifications(listOfCerti);

		}
		// if (candBean.isLocationGeo()) {
		// candidateBean.setLocationGeo(new GeoPoint(100.0, 200.0));
		//
		// }
		// if (candBean.isCrm()) {
		// CRMESBean crmesBean = new CRMESBean();
		// crmesBean.setEngagementScore("50.0");
		// candidateBean.setCrm(crmesBean);
		// }
		if (candBean.isCrmData()) {
			CRM crm = new CRM();
			crm.setAwarenessScore(10);
			crm.setInterestLevelScore(3);
			crm.setPersonalCommunicationScore(4);
			crm.setBroadcastCommunicationScore(5);
			crm.setCompatibilityScore(5);
			crm.setBenefitAwarenessScore(10);
			crm.setCompanyAwarenessScore(2);
			crm.setPersonalMeetingScore(2);
			crm.setPersonalIMScore(3);
			crm.setPersonalPhoneScore(4);
			candidateBean.setCrmData(crm);

		}
		if (candBean.isEntityAddressBean()) {
			CollectionEntity<EntityAddressMapBean> collectionEntity = new CollectionEntity<>();
			EntityAddressMapBean entityAddressMapBean = new EntityAddressMapBean();
			entityAddressMapBean.setCity("Bangalore");
			entityAddressMapBean.setState("Karnataka");
			entityAddressMapBean.setCountry("INDIA");
			entityAddressMapBean.setPincode("560043");
			collectionEntity.addItem(entityAddressMapBean);
			candidateBean.setEntityAddressBean(collectionEntity);
		}
		if (candBean.getSkill() > 0) {
			CollectionEntity<CandidateSkillMapBean> collectionEntity = new CollectionEntity<CandidateSkillMapBean>();
			for (int i = 0; i < candBean.getSkill(); i++) {
				CandidateSkillMapBean candidateSkillMapBean = new CandidateSkillMapBean();
				candidateSkillMapBean.setSkill("autoSkill" + i + generateUniqueId);
				candidateSkillMapBean.setSkillStrength(40.0);
				candidateSkillMapBean.setExperience(5.0);
				candidateSkillMapBean.setFrequency(1);
				candidateSkillMapBean.setProficiency("test");
				candidateSkillMapBean.setSkillCloudCount(10);
				collectionEntity.addItem(candidateSkillMapBean);
			}
			candidateBean.setCandidateSkillMapBean(collectionEntity);
		}
		if (candBean.getEducation() > 0) {
			CollectionEntity<CandidateEducationMapBean> collectionEntity = new CollectionEntity<CandidateEducationMapBean>();
			for (int i = 0; i < candBean.getEducation(); i++) {
				CandidateEducationMapBean candidateEducationMapBean = new CandidateEducationMapBean();
				candidateEducationMapBean.setDegreeName("autoDegree" + i + generateUniqueId);
				candidateEducationMapBean.setGradeTypeName("test");
				candidateEducationMapBean.setInstituteAddressId("Bangalore");
				candidateEducationMapBean.setIsHighestDegree("B.tech");
				collectionEntity.addItem(candidateEducationMapBean);

			}
			candidateBean.setCandidateEducationMapBean(collectionEntity);

		}
		if (candBean.getEmployer() > 0) {
			CollectionEntity<CandidateEmployerMapBean> collectionEntity = new CollectionEntity<CandidateEmployerMapBean>();
			for (int i = 0; i < candBean.getEducation(); i++) {
				CandidateEmployerMapBean candidateEmployerMapBean = new CandidateEmployerMapBean();
				candidateEmployerMapBean.setEmployerName("autoEmployer" + i + generateUniqueId);
				candidateEmployerMapBean.setDesignationName("software engineer");
				candidateEmployerMapBean.setEmployerAddressId("Bangalore");
				candidateEmployerMapBean.setEmploymentTypeName("Permanent");
				collectionEntity.addItem(candidateEmployerMapBean);
			}
			candidateBean.setCandidateEmployerMapBean(collectionEntity);
		}
		if (candBean.getProject() > 0) {
			CollectionEntity<CandidateProjectMapBean> collectionEntity = new CollectionEntity<CandidateProjectMapBean>();
			for (int i = 0; i < candBean.getEducation(); i++) {
				CandidateProjectMapBean candidateProjectMapBean = new CandidateProjectMapBean();
				candidateProjectMapBean.setEmployerName("autoProject" + i + generateUniqueId);
				candidateProjectMapBean.setClientName("Spire");
				candidateProjectMapBean.setDescription("testing");
				candidateProjectMapBean.setProjectName("Acqura Search");
				collectionEntity.addItem(candidateProjectMapBean);
			}

			candidateBean.setCandidateProjectMapBean(collectionEntity);
		}
		String candJson = new Gson().toJson(candidateBean);
		_logger.info("Request json-->" + candJson);
		System.out.println("Candidate Request json-->" + candJson);
		return candidateBean;

	}

}
