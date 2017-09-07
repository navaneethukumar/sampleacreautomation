package com.spire.acqura.rest.service.util;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test(alwaysRun = true)
public class BaseTestPlan {

	ConfigUtil configs = new ConfigUtil();

	@BeforeSuite
	public void readingSuiteParams(ITestContext context) {
		configs.loadGenericConfig(context);
	}

	@BeforeTest()
	public void readingTestParams(ITestContext context) {
		configs.loadTestConfigs(context);

	}

}
