package com.spire.acqura.rest.service.consumers;

import com.spire.acqura.rest.service.util.ConfigCache;
import com.spire.acqura.rest.service.util.GlobalConfigs;
import com.spire.acqura.rest.service.util.Constants;

public class BaseConsumer {

	String baseUri = null;
	String host = null;
	String port = null;

	public BaseConsumer() {
		host = ConfigCache.getConfig(GlobalConfigs.SERVICEHOST.getValue());
		port = ConfigCache.getConfig(GlobalConfigs.SERVICEPORT.getValue());
		baseUri = ConfigCache.getConfig(Constants.URI);
		baseUri = "http://" + host + ":" + port + baseUri;
	}

	public String getBaseUri() {
		return baseUri;
	}

	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
