package com.spire.acqura.rest.service.testplan.gisearch;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.acqura.rest.service.consumers.GISearchConsumer;
import com.spire.acqura.rest.service.util.BaseTestPlan;
import com.spire.acqura.rest.service.util.ConsumerFactory;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;

import spire.talent.gi.views.SearchInputRequest;

public class GISearchTestPlan extends BaseTestPlan {
	String SERVICE_ENDPOINT_URL = null;
	final static String DATA_PROVIDER_NAME = "SEARCH";
	final static String PROFILE_PATH = "./src/test/resources/GI/Search/";
	final static String PROFILE_CSV = "SearchData.csv";
	final static String SEARCH_DATA_FILE_PATH = PROFILE_PATH + PROFILE_CSV;
	Object[] profiles = new Object[3];
	Iterator<Object[]> profiledataSearch = null;
	GISearchConsumer giSearchConsumer = null;

	@DataProvider(name = DATA_PROVIDER_NAME)
	public Iterator<Object[]> getDataFromCsv(Method method) {
		Iterator<Object[]> profiledata = null;
		try {
			String fileName = SEARCH_DATA_FILE_PATH;
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("request", GISearchRequest.class);
			profiledata = SpireCsvUtil.getObjectsFromCsv(GISearchTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return profiledata;
	}

	@BeforeTest
	public void setUp() {
		ConsumerFactory consumerFactory = new ConsumerFactory();
		giSearchConsumer = consumerFactory.getGISearchConsumer();
	}

	@Test(dataProvider = DATA_PROVIDER_NAME)
	public void testSearchByAttribute(SpireTestObject spireTestObject, GISearchRequest request) throws Exception {
		String baseRequest = request.getRequest();
		SearchInputRequest searchInputRequest = GISearchTestUtil.convertJsonToSearchInput(baseRequest);
		giSearchConsumer.searchByAttribute(searchInputRequest);
	}

}
