package com.spire.acqura.rest.service.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.testng.ITestContext;

public class ConfigUtil {

	public void loadGenericConfig(ITestContext context) {
		if (MavenConfigUtil.readMavenProps()) {
			System.out.println("Global Configs got set are -->" + ConfigCache.getConfigs());
			return;
		} else {
			Map<String, String> suiteParams = context.getCurrentXmlTest().getSuite().getParameters();
			for (GlobalConfigs key : GlobalConfigs.values()) {
				ConfigCache.addConfig(key.getValue(), suiteParams.get(key.getValue()));
			}
		}
		System.out.println("Local Configs set are -->" + ConfigCache.getConfigs());
	}

	public void loadTestConfigs(ITestContext context) {
		try {
			Map<String, String> testParams = context.getCurrentXmlTest().getLocalParameters();
			if (StringUtils.isNotEmpty(Constants.URI)) {
				ConfigCache.addConfig(Constants.URI, testParams.get(Constants.URI));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
