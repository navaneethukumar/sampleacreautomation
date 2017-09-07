package com.spire.acqura.rest.service.testplan.candidate;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.acqura.framework.util.SpireCsvUtil;
import com.spire.acqura.rest.service.consumers.CandidateConsumer;
import com.spire.acqura.rest.service.util.BaseTestPlan;
import com.spire.acqura.rest.service.util.ConsumerFactory;
import com.spire.acqura.rest.service.util.candidate.CandBean;
import com.spire.acqura.rest.service.util.candidate.CandidateDataUtil;
import com.spire.acqura.rest.service.util.candidate.ServiceUtil;
import com.spire.base.util.internal.entity.SpireTestObject;

import spire.talent.entity.profileservice.beans.CandidateBean;

public class CandidateTestPlan extends BaseTestPlan {
	String SERVICE_ENDPOINT_URL = null;
	final static String DATA_PROVIDER_NAME = "NEW_CANDIDATE";
	final static String PROFILE_PATH = "./src/test/resources/data/candidate/";
	final static String PROFILE_CSV = "candidate.csv";
	final static String SEARCH_DATA_FILE_PATH = PROFILE_PATH + PROFILE_CSV;
	Object[] profiles = new Object[3];
	Iterator<Object[]> profiledataSearch = null;
	CandidateConsumer newCandidateConsumer = null;

	@DataProvider(name = DATA_PROVIDER_NAME)
	public Iterator<Object[]> getDataFromCsv(Method method) {
		Iterator<Object[]> candidateData = null;
		try {
			String fileName = SEARCH_DATA_FILE_PATH;
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("candBean", CandBean.class);
			candidateData = SpireCsvUtil.getObjectsFromCsv(CandidateTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return candidateData;
	}

	@BeforeTest
	public void setUp() {
		ConsumerFactory consumerFactory = new ConsumerFactory();
		newCandidateConsumer = consumerFactory.getNewCandidateConsumer();
	}

	@Test(dataProvider = DATA_PROVIDER_NAME)
	public void testNewCandidatePersister(SpireTestObject spireTestObject, CandBean request) throws Exception {
		CandidateBean candidateBean = CandidateDataUtil.createCandiateData(request);
		String candId = newCandidateConsumer.createSingleCandidate(candidateBean, false, true);
//		 validation
		ServiceUtil.validateInMongoDB(candidateBean, candId);
		ServiceUtil.validateInES(candidateBean, candId);
	}
}
