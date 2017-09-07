package com.spire.acqura.rest.service.testplan.gisearch;

import java.util.ArrayList;
import java.util.List;

import spire.match.entities.SearchResult;
import spire.talent.gi.beans.PlatformCandidateExtendedBean;
import spire.talent.gi.rest.core.ErrorEntity;

public class SearchResultExtend {
	private Boolean hasError = false;
	private final List<ErrorEntity> errors;
	private SearchResult<PlatformCandidateExtendedBean> response;

	public SearchResultExtend() {
		this.errors = new ArrayList<>();
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public Boolean getHasError() {
		return hasError;
	}

	public void setResponse(SearchResult<PlatformCandidateExtendedBean> response) {
		this.response = response;
	}

	public SearchResult<PlatformCandidateExtendedBean> getResponse() {
		return response;
	}

	public List<ErrorEntity> getErrors() {
		return errors;
	}

	public String toJson() {
		if (hasError) {
			return errors.toString();
		} else {
			return response.toString();
		}
	}

	@Override
	public String toString() {
		return "GenericResponse [hasError=" + hasError + ", errors=" + errors + ", response=" + response + "]";
	}
}
