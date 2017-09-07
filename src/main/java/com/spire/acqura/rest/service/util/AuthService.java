package com.spire.acqura.rest.service.util;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.HttpUrlConnectorProvider;

import spire.commons.userservice.bean.LoginRequestBean;
import spire.commons.userservice.bean.LoginResponseBean;

public class AuthService {
	static Client client = null;
	static Invocation.Builder invocationBuilder = null;
	private final static Logger LOGGER = Logger.getLogger(AuthService.class.getName());
	static String URI = "/user-service-web/api/authentication/login?";
	static String URL = "http://" + ConfigCache.getConfig(GlobalConfigs.USERSERVICEHOST.getValue()) + ":"
			+ ConfigCache.getConfig(GlobalConfigs.USERSERVICEPORT.getValue()) + URI;

	static {
		if (client == null) {
			client = ClientBuilder.newClient();
			client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
			client.register(CustomObjectMapperContextResolver.class);
		}
	}

	public static void generateNewToken() throws Exception {

		LOGGER.info("User service URL ->" + URL);
		WebTarget target = client.target(URL);
		invocationBuilder = target.request();
		Entity<LoginRequestBean> entity = Entity.entity(getLoginRequestBean(), MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(entity);
		if (response.getStatus() == 200 || response.getStatus() == 201) {
			LoginResponseBean loginResponseBean = response.readEntity(LoginResponseBean.class);
			ConfigCache.addConfig(Constants.TOKENID, loginResponseBean.getTokenId());
			ConfigCache.addConfig(Constants.REALMNAME, loginResponseBean.getRealmName());
		} else {
			throw new Exception("User service call failed -->" + response.getStatus());
		}
	}

	public static LoginRequestBean getLoginRequestBean() {
		LoginRequestBean loginRequestBean = new LoginRequestBean();
		loginRequestBean.setUserId(ConfigCache.getConfig(GlobalConfigs.USERID.getValue()));
		loginRequestBean.setPassword(ConfigCache.getConfig(GlobalConfigs.PASSWORD.getValue()));
		return loginRequestBean;
	}

}
