package com.spire.acqura.rest.service.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import spire.match.entities.SearchFacets.FieldType;

public class Constants {

	public final static String URI = "uri";
	public final static String TOKENID = "tokenId";
	public final static String REALMNAME = "realmName";

	static Map<String, FieldType> customFacets = new LinkedHashMap<String, FieldType>() {
		{
			put("candidateSkillMapBean.items.skill", FieldType.RAW);
			put("locationName", FieldType.RAW);
			put("totalExperienceMnth", FieldType.RAW);
			put("candidateEmployerMapBean.items.employerName", FieldType.RAW);
			put("candidateEducationMapBean.items.instituteName", FieldType.RAW);
			put("sourceName", FieldType.RAW);
			put("sourceType", FieldType.RAW);
			put("educationLevel", FieldType.RAW);
			put("updatedOn", FieldType.DATE);
			put("jobTitle", FieldType.RAW);
		}
	};
	static List<String> customFlds = new ArrayList<String>() {
		{
			add("cqi");
			add("Institute");
			add("crm");
			add("candidateUrl");
		}
	};

	public static List<String> getCustomFlds() {
		return customFlds;
	}

	public static void setCustomFlds(List<String> customFlds) {
		Constants.customFlds = customFlds;
	}

	public static Map<String, FieldType> getCustomFacets() {
		return customFacets;
	}

	public static void setCustomFacets(Map<String, FieldType> customFacets) {
		Constants.customFacets = customFacets;
	}

}
