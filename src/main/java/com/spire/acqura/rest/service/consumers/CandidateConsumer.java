package com.spire.acqura.rest.service.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.spire.acqura.rest.service.util.CoreRestClient;

import spire.talent.entity.profileservice.beans.CandidateBean;

public class CandidateConsumer extends BaseConsumer {

	public String createSingleCandidate(CandidateBean candidateBean, boolean triggerEvent, boolean esUpdate)
			throws Exception {
		String END_POINT = getBaseUri() + "?triggerEvent=" + triggerEvent + "&esUpdate=" + esUpdate;
		Entity<CandidateBean> entity = Entity.entity(candidateBean, MediaType.APPLICATION_JSON);
		System.out.println("END-POINT---------->" + END_POINT);
		System.out.println("Request Json --------->" + new Gson().toJson(candidateBean));
		Response response = CoreRestClient.post(END_POINT, entity);
		if (response != null) {
			String candId = response.readEntity(String.class);
			if (candId != null) {
				String id = candId.split(":", 2)[1];
				return id;
			} else {
				String request = new Gson().toJson(candidateBean);
				throw new Exception("createSingleCandidate() is failed for Request -->" + request + "/t"
						+ "End-point-->" + baseUri);
			}
		}
		String request = new Gson().toJson(candidateBean);
		throw new Exception(
				"createSingleCandidate() is failed for Request -->" + request + "/t" + "End-point-->" + baseUri);
	}

	public String createBulkCandidate(List<CandidateBean> candidateBean, boolean triggerEven, boolean esUpdate)
			throws Exception {
		String END_POINT = baseUri + "?triggerEvent=false&esUpdate=true";
		Entity<List<CandidateBean>> entity = Entity.entity(candidateBean, MediaType.APPLICATION_JSON);
		System.out.println("END-POINT---------->" + END_POINT);
		Response response = CoreRestClient.post(END_POINT, entity);
		if (response != null) {
			String candId = response.readEntity(String.class);
			if (candId != null) {
				return candId;
			} else {
				String request = new Gson().toJson(candidateBean);
				throw new Exception("createSingleCandidate() is failed for Request -->" + request + "/t"
						+ "End-point-->" + baseUri);
			}
		}
		String request = new Gson().toJson(candidateBean);
		throw new Exception(
				"createSingleCandidate() is failed for Request -->" + request + "/t" + "End-point-->" + baseUri);
	}

}
