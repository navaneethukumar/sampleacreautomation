package com.spire.acqura.rest.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MavenConfigUtil {

	public static boolean readMavenProps() {
		Properties mavenProps = new Properties();
		String path = "maven.properties";
		InputStream input = MavenConfigUtil.class.getClassLoader().getResourceAsStream(path);
		if (input == null) {
			return false;
		}
		try {
			mavenProps.load(input);
			for (GlobalConfigs key : GlobalConfigs.values()) {
				String value = mavenProps.getProperty(key.getValue());
				String configPattern = "\\$\\{[a-zA-Z_\\-0-9.]+\\}";
				Pattern p = Pattern.compile(configPattern);
				Matcher m = p.matcher(value);
				if (m.matches()) {
					return false;
				} else {
					ConfigCache.addConfig(key.getValue(), value);
				}
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return false;

	}

}
