package com.spire.acqura.rest.service.util;

import java.util.HashMap;
import java.util.Map;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

public class ConfigCache {
	private static final Logger _logger = LoggerFactory.getLogger(ConfigCache.class);

	/**
	 * Cache of configurations
	 */
	public static Map<String, String> configs = new HashMap<String, String>();

	public static Map<String, String> getConfigs() {
		return configs;
	}

	public static void setConfigs(Map<String, String> configs) {
		ConfigCache.configs = configs;
	}

	public static boolean addConfig(String key, String value) {

		if (key == null) {
			_logger.error(Key.MESSAGE, "Key in the config cache cannot be null");
			return false;
		}

		try {
			configs.put(key, value);
			return true;
		} catch (Exception ex) {
			_logger.error(Key.EXCEPTION, ex.getClass().getName(), Key.MESSAGE, ex.getMessage(), Key.EXCEPTION,
					ex.getCause());
		}

		return false;
	}

	public static String getConfig(String key) {

		try {
			return configs.get(key);
		} catch (Exception ex) {
			_logger.error(Key.EXCEPTION, ex.getClass().getName(), Key.MESSAGE, ex.getMessage(), Key.EXCEPTION,
					ex.getCause());
		}

		return null;
	}
}
