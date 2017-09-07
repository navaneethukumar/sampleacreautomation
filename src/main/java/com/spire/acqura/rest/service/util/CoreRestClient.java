package com.spire.acqura.rest.service.util;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.google.gson.Gson;

public class CoreRestClient {

	static Client client = null;
	static Invocation.Builder invocationBuilder = null;
	private final static Logger LOGGER = Logger.getLogger(CoreRestClient.class.getName());

	static {
		if (client == null) {
			client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
			;
			client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
			client.register(CustomObjectMapperContextResolver.class);
			try {
				AuthService.generateNewToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Response get(String url) throws Exception {
		WebTarget target = client.target(url);
		invocationBuilder = target.request();
		invocationBuilder.headers(setHeaders());
		Response response = invocationBuilder.get();
		if (response.getStatus() == 401) {
			AuthService.generateNewToken();
			get(url);
		}
		return parserServiceResponse(null, response);
	}

	public static Response post(String url, Entity<?> entity) throws Exception {
		WebTarget target = client.target(url);
		invocationBuilder = target.request();
		invocationBuilder.headers(setHeaders());
		Response response = invocationBuilder.post(entity);
		if (response.getStatus() == 401) {
			AuthService.generateNewToken();
			post(url, entity);
		}
		return parserServiceResponse(entity, response);

	}

	public static Response put(String url, Entity<?> entity) throws Exception {
		WebTarget target = client.target(url);
		invocationBuilder = target.request();
		invocationBuilder.headers(setHeaders());
		Response response = invocationBuilder.put(entity);
		if (response.getStatus() == 401) {
			AuthService.generateNewToken();
			put(url, entity);
		}
		return parserServiceResponse(entity, response);

	}

	public static Response delete(String url) throws Exception {
		WebTarget target = client.target(url);
		invocationBuilder = target.request();
		invocationBuilder.headers(setHeaders());
		Response response = invocationBuilder.delete();
		if (response.getStatus() == 401) {
			AuthService.generateNewToken();
			delete(url);
		}
		return parserServiceResponse(null, response);

	}

	public static Response parserServiceResponse(Entity<?> entity, Response response) throws Exception {
		switch (response.getStatus()) {
		case 200:
			LOGGER.info("passed-->" + response.getStatus());
			return response;
		case 201:
			LOGGER.info("passed-->" + response.getStatus());
			return response;
		case 400:
			if (entity != null) {
				String json = new Gson().toJson(entity);
				LOGGER.info("Request json is wrongly passed --->" + json);
				return response;
			}
			LOGGER.info("Bad Request-->" + response.getStatus());
			return response;
		default:
			throw new Exception("Service Call failed with status code is -->" + response.getStatus());
		}
	}

	public static MultivaluedHashMap<String, Object> setHeaders() {
		MultivaluedHashMap<String, Object> multivaluedMap = new MultivaluedHashMap<String, Object>();
		multivaluedMap = new MultivaluedHashMap<String, Object>();
		multivaluedMap.add("Authorization", "Bearer " + ConfigCache.getConfig(Constants.TOKENID));
		multivaluedMap.add("realmName", ConfigCache.getConfig(Constants.REALMNAME));
		LOGGER.info("Headers used -->" + multivaluedMap);
		return multivaluedMap;
	}

}
