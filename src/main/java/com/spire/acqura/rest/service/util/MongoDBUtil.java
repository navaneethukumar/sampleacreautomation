package com.spire.acqura.rest.service.util;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongoDBUtil {

	static Datastore datastore = null;
	String dbName = "logica_SCHEMA";
	int port = 27018;

	private MongoDBUtil() {
		String host = ConfigCache.getConfig("userServiceHost");
		MongoClientOptions mongoOptions = MongoClientOptions.builder().socketTimeout(60000).connectTimeout(15000)
				.maxConnectionIdleTime(600000).readPreference(ReadPreference.primaryPreferred()).build();
		MongoClient mongoClient;
		mongoClient = new MongoClient(new ServerAddress("192.168.2.75", port), mongoOptions);
		mongoClient.setWriteConcern(WriteConcern.MAJORITY);
		datastore = new Morphia().createDatastore(mongoClient, dbName);
		datastore.ensureIndexes();
		datastore.ensureCaps();
	}

	public static Datastore getConnection() {
		if (datastore == null) {
			synchronized (MongoDBUtil.class) {
				if (datastore == null) {
					new MongoDBUtil();
				}
			}
		}
		return datastore;
	}

}
