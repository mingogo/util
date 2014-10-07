package com.fitch.ids.util;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoConnectionManager {
	
	private static final MongoConnectionManager INSTANCE = new MongoConnectionManager();
	
	private MongoConnectionManager(){
		
	}
	
	public static MongoConnectionManager getInstance(){
		return INSTANCE;
	}
	
	public DBCollection getCollection(){
		DBCollection coll = null;
		try{
			//MongoClientURI uri  = new MongoClientURI("mongodb://user:pass@host:port/db");
			MongoClientURI uri  = new MongoClientURI("mongodb://mongo-ids-usr:ids@mongoids-d01:27017/ids-dev-2");
			MongoClient client = new MongoClient(uri);
			DB db = client.getDB(uri.getDatabase());
			coll = db.getCollection("fds_javaetl_test");
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		return coll;
	}
	
	public DBCollection getCollection(String collection){
		DBCollection coll = null;
		try{
			//MongoClientURI uri  = new MongoClientURI("mongodb://user:pass@host:port/db");
			MongoClientURI uri  = new MongoClientURI("mongodb://mongo-ids-usr:ids@mongoids-d01:27017/ids-dev-2");
			MongoClient client = new MongoClient(uri);
			DB db = client.getDB(uri.getDatabase());
			coll = db.getCollection(collection);
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		return coll;
	}
	
	public DBCollection getCollection(String user, String password, String host, int port, String database, String collection){
		DBCollection coll = null;
		try{
			//MongoClientURI uri  = new MongoClientURI("mongodb://user:pass@host:port/db");
			MongoClientURI uri  = new MongoClientURI("mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database);
			MongoClient client = new MongoClient(uri);
			DB db = client.getDB(uri.getDatabase());
			coll = db.getCollection(collection);
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		return coll;
	}
}
