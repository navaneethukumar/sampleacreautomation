package com.spire.acqura.rest.service.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.spire.acqura.rest.service.util.CoreRestClient;
import com.spire.acqura.rest.service.util.search.SearchResultExtend;

import spire.talent.gi.views.SearchInputRequest;

public class GISearchConsumer extends BaseConsumer {

	public SearchResultExtend searchByAttribute(SearchInputRequest searchInputRequest) throws Exception {
		String END_POINT = baseUri + "_candidates?clientType=ACQURA";
		Entity<SearchInputRequest> entity = Entity.entity(searchInputRequest, MediaType.APPLICATION_JSON);
		System.out.println("END-POINT---------->" + END_POINT);
		Response response = CoreRestClient.post(END_POINT, entity);
		if (response != null) {
			SearchResultExtend searchResponse = response.readEntity(SearchResultExtend.class);
			if (searchResponse != null) {
				return searchResponse;
			} else {
				throw new Exception("searchByAttribute() is failed !!!");
			}
		}
		String request = new Gson().toJson(searchInputRequest);
		throw new Exception("searchByAttribute() is failed for Request -->" + request + "/t" + "End-point-->" + baseUri);

	}
}
