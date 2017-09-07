package com.spire.acqura.rest.service.util.candidate;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.acqura.rest.service.util.CustomObjectMapperContextResolver;
import com.spire.acqura.rest.service.util.MongoDBUtil;

import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateEducationMapBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidateProjectMapBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;
import spire.talent.entity.profileservice.beans.EntityAddressMapBean;
import spire.talent.gi.commons.beans.ExtCandidateBean;

public class ServiceUtil {

	private static final Logger _logger = LoggerFactory.getLogger(ServiceUtil.class);
	static Gson gson = new Gson();

	public static void validateInMongoDB(CandidateBean request, String candidateId) {
		Query<ExtCandidateBean> candidatesQuery = null;
		Datastore datastore = MongoDBUtil.getConnection();
		candidatesQuery = datastore.createQuery(ExtCandidateBean.class);
		candidatesQuery.field("id").equal(candidateId.trim());
		ExtCandidateBean extResponse = candidatesQuery.get();

		// requested data
		Collection<CandidateSkillMapBean> requestSkillCollection = request.getCandidateSkillMapBean().getItems();
		Collection<CandidateEducationMapBean> requestEducationCollection = request.getCandidateEducationMapBean()
				.getItems();
		Collection<CandidateEmployerMapBean> requestEmployerCollection = request.getCandidateEmployerMapBean()
				.getItems();
		Collection<CandidateProjectMapBean> requestProjectCollection = request.getCandidateProjectMapBean().getItems();
		Collection<EntityAddressMapBean> requestAddressCollection = request.getEntityAddressBean().getItems();

		// resposne data
		List<spire.talent.gi.commons.beans.CandidateSkillMapBean> responseListOfSkills = extResponse
				.getCandidateSkillMapBean();
		List<spire.talent.gi.commons.beans.CandidateEducationMapBean> responseListOfEducations = extResponse
				.getCandidateEducationMapBean();
		List<spire.talent.gi.commons.beans.CandidateEmployerMapBean> responseListOfEmployers = extResponse
				.getCandidateEmployerMapBean();
		List<spire.talent.gi.commons.beans.CandidateProjectMapBean> responseListOfProjects = extResponse
				.getCandidateProjectMapBean();

		// validation
		if (requestSkillCollection != null && responseListOfSkills != null
				&& requestSkillCollection.size() == responseListOfSkills.size()) {
			System.out.println("Skill validatin done !!");
		} else {
			throw new RuntimeException("Mongo DB validation failed for candidateId -->" + candidateId);
		}
		if (requestEducationCollection != null
				&& requestEducationCollection.size() == responseListOfEducations.size()) {
			System.out.println("Education validatin done !!");
		} else {
			throw new RuntimeException("Mongo DB validation failed for candidateId -->" + candidateId);
		}
		if (requestEmployerCollection != null && requestEmployerCollection.size() == responseListOfEmployers.size()) {
			System.out.println("Employer validatin done !!");
		} else {
			throw new RuntimeException("Mongo DB validation failed for candidateId -->" + candidateId);
		}
		if (requestProjectCollection != null && requestProjectCollection.size() == responseListOfProjects.size()) {
			System.out.println("Project validatin done !!");
		} else {
			throw new RuntimeException("Mongo DB validation failed for candidateId -->" + candidateId);
		}
		if (requestAddressCollection != null && requestAddressCollection.size() == requestAddressCollection.size()) {
			System.out.println("Address validatin done !!");
		} else {
			throw new RuntimeException("Mongo DB validation failed for candidateId -->" + candidateId);
		}

		// String jsonResponse = gson.toJson(extResponse);
		// ObjectMapper objMap = new ObjectMapper();
		// try {
		// CandidateBean response = objMap.readValue(jsonResponse,
		// CandidateBean.class);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// if (response != null) {
		// validateCandidateResponseInMongo(request, response);
		// }
	}

	public static void validateInES(CandidateBean candidateBean, String candidateId)
			throws JsonProcessingException, IOException {
		String URL = "http://192.168.2.175:7200/logica/candidate/" + candidateId.trim();
		candidateBean.setId(candidateId);
		Client client = null;
		Invocation.Builder invocationBuilder = null;
		client = ClientBuilder.newClient();
		client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
		client.register(CustomObjectMapperContextResolver.class);
		WebTarget target = client.target(URL);
		invocationBuilder = target.request();
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200) {
			String responseString = response.readEntity(String.class);
			ObjectMapper objMap = new ObjectMapper();
			JsonNode actualObj = objMap.readTree(responseString);
			JsonNode source = actualObj.get("_source");
			CandidateBean esCandidateResponse = objMap.readValue(source.toString(), CandidateBean.class);
			if (esCandidateResponse != null) {
				validateCandidateResponseInEs(candidateBean, esCandidateResponse);
			} else {
				throw new RuntimeException("Response null for candidateId -->" + candidateId);
			}
		} else {
			throw new RuntimeException("ES call failed for candidateId -->" + candidateId);
		}
	}

	public static void validateCandidateResponseInEs(CandidateBean request, CandidateBean response) {
		System.out.println("request json-->" + gson.toJson(request));
		System.out.println("response json-->" + gson.toJson(response));

		/**
		 * skill validation
		 */
		Collection<CandidateSkillMapBean> requestSkillCollection = request.getCandidateSkillMapBean().getItems();
		Collection<CandidateSkillMapBean> responseSkillCollection = response.getCandidateSkillMapBean().getItems();

		validate(requestSkillCollection, responseSkillCollection);
		/**
		 * Education validation
		 */
		Collection<CandidateEducationMapBean> requestEducationCollection = request.getCandidateEducationMapBean()
				.getItems();
		Collection<CandidateEducationMapBean> responseEducationCollection = response.getCandidateEducationMapBean()
				.getItems();
		validate(requestEducationCollection, responseEducationCollection);
		/**
		 * Employer validation
		 */
		Collection<CandidateEmployerMapBean> requestEmployerCollection = request.getCandidateEmployerMapBean()
				.getItems();
		Collection<CandidateEmployerMapBean> responseEmployerCollection = response.getCandidateEmployerMapBean()
				.getItems();
		validate(requestEmployerCollection, responseEmployerCollection);

		/**
		 * Project validation
		 */
		Collection<CandidateProjectMapBean> requestProjectCollection = request.getCandidateProjectMapBean().getItems();
		Collection<CandidateProjectMapBean> responseProjectCollection = response.getCandidateProjectMapBean()
				.getItems();
		validate(requestProjectCollection, responseProjectCollection);
		/**
		 * Address map
		 */
		Collection<EntityAddressMapBean> requestAddressCollection = request.getEntityAddressBean().getItems();
		Collection<EntityAddressMapBean> responseAddressCollection = response.getEntityAddressBean().getItems();
		validate(requestAddressCollection, responseAddressCollection);

	}

	public static void validateCandidateResponseInMongo(CandidateBean request, ExtCandidateBean response) {
		System.out.println("request json-->" + gson.toJson(request));
		System.out.println("response json-->" + gson.toJson(response));
	}

	static <T> boolean validate(Collection<T> request, Collection<T> response) {
		boolean equals = false;
		if (request != null && response != null) {
			equals = request.size() == response.size() && request.containsAll(response)
					&& response.containsAll(request);
		} else if (request == null && response == null) {
			equals = true;
		}
		if (equals == false) {
			throw new RuntimeException("validation failed for candidate Id-->" + request);
		} else {
			_logger.info("validation done for!!" + request.getClass());
		}
		return equals;
	}

	public static void main(String[] args) throws JsonProcessingException, IOException {
		validateInMongoDB(null, "6002:6005:c76504a8130c38a3a428670eb7b1a3af");
	}

}
