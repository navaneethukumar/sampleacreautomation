package com.spire.acqura.rest.service.testplan.gisearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.acqura.rest.service.util.Constants;

import spire.match.entities.FacetTerm;
import spire.match.entities.SearchFacets.FieldType;
import spire.talent.gi.views.PageInfo;
import spire.talent.gi.views.SearchInputRequest;

public class GISearchTestUtil {

	private final static Logger LOGGER = Logger.getLogger(GISearchTestUtil.class.getName());

	public static void generateSearchRequest(SearchInputRequest searchInput) {

		// String searchInput.getSearchQueryString();

	}

	public static SearchInputRequest convertJsonToSearchInput(String searchJson)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		SearchInputRequest searchInputReq = mapper.readValue(searchJson, SearchInputRequest.class);
		searchInputReq.getSearchInput().setCustomFacets(getCustomFacets());
		searchInputReq.getSearchInput().setCustomFields(getCustomFields());
		PageInfo pageInfo = new PageInfo();
		pageInfo.setLimit(1);
		pageInfo.setOffset(0);
		searchInputReq.setPageInfo(pageInfo);
		LOGGER.info("Search Request-->" + new Gson().toJson(searchInputReq));
		return searchInputReq;

	}

	public static List<FacetTerm> getCustomFacets() {
		List<FacetTerm> listFacetTerm = new ArrayList<FacetTerm>();
		for (Map.Entry<String, FieldType> customFacets : Constants.getCustomFacets().entrySet()) {
			FacetTerm facetTerm = new FacetTerm();
			facetTerm.setTerm(customFacets.getKey());
			facetTerm.setType(customFacets.getValue());
			listFacetTerm.add(facetTerm);
		}

		return listFacetTerm;
	}

	public static List<String> getCustomFields() {
		List<String> listCustomFlds = new ArrayList<String>();
		for (String flds : Constants.getCustomFlds()) {
			listCustomFlds.add(flds);
		}
		return listCustomFlds;
	}

}
