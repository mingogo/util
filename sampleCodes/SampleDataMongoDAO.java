package com.fitch.ids.mongo.dao;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.fitch.ids.mongo.vo.MongoVO;
import com.fitch.ids.oracle.vo.FdsNormDocument;
import com.fitch.ids.util.MongoConnectionManager;
import com.fitch.ids.util.OracleToMongoTransformer;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class SampleDataMongoDAO extends Thread{

	OracleToMongoTransformer oTom = new OracleToMongoTransformer();
	
	DBCollection coll = null; 
	LinkedBlockingQueue<FdsNormDocument> queue;
	
	public SampleDataMongoDAO(){
		this.coll =  MongoConnectionManager.getInstance().getCollection();
	}
	
	
	public SampleDataMongoDAO(String collection){
		this.coll =  MongoConnectionManager.getInstance().getCollection(collection);
	}
	
	public SampleDataMongoDAO(String collection, LinkedBlockingQueue<FdsNormDocument> q){
		this.coll =  MongoConnectionManager.getInstance().getCollection(collection);
		this.queue = q;
	}
	
	public SampleDataMongoDAO(String user, String password, String host, int port, String database, String collection){
		this.coll =  MongoConnectionManager.getInstance().getCollection(user, password, host, port, database, collection);
	}
	
	public void insertData(List<MongoVO> mongoData) {
		for(MongoVO m : mongoData){
			coll.insert(m);
		}
	}
	
	public BasicDBObject testFind(){
		return (BasicDBObject)coll.findOne();
	}

	public void insertFdsNormDocData(List<FdsNormDocument> fdsNormDocuments) {
		int count = 0;
		for(FdsNormDocument f : fdsNormDocuments){
			MongoVO m = oTom.transformDocument(f);
			coll.insert(m);
			m = null;
			count ++;
			if(10000 == count) {
				System.gc();
				count = 0;
			}
		}
		
	}

	public void insertFullFdsNormDocData(List<FdsNormDocument> fdsNormDocuments) {
		int count = 0;
		for(FdsNormDocument f : fdsNormDocuments){
			MongoVO m = oTom.transformFullDocument(f);
			coll.insert(m);
			m = null;
			count ++;
			if(10000 == count) {
				System.gc();
				count = 0;
			}
		}
	}
	
	public void insertFullFdsNormDocDataInThread() {
		
		while(true){
			try {
				FdsNormDocument f = queue.take();
				MongoVO m = oTom.transformFullDocument(f);
				coll.insert(m);
				System.out.println("Mongo: One document inserted into Mongo..." + System.currentTimeMillis() + " ---------> " + queue.size());
				m = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		this.insertFullFdsNormDocDataInThread();
	}

}
