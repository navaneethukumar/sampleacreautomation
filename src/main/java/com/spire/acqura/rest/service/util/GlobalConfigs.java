package com.spire.acqura.rest.service.util;

public enum GlobalConfigs {

	SERVICEHOST("serviceHost"), SERVICEPORT("servicePort"), USERSERVICEHOST("userServiceHost"), USERSERVICEPORT(
			"userServicePort"), USERID("userId"), PASSWORD("password");

	private final String value;

	GlobalConfigs(String config) {
		this.value = config;

	}

	public String getValue() {
		return value;
	}
}
