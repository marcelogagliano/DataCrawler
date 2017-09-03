/**
 * 
 */
package net.protheos.crawler.dao;

import static com.mongodb.client.model.Filters.eq;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author marce
 *
 */
public final class MongoProxy{
	private static final int port = 27017;
	private static final String host = "localhost"; 
	private static final String standardDB = "crawlerdb";
	private static final String standardCollection = "collection";
	private static MongoClient mongo = new MongoClient(host , port);		
	private static MongoDatabase db = mongo.getDatabase(standardDB);
	private static MongoCollection<Document> collection = db.getCollection(standardCollection);

	/**
	 * 
	 */
	private MongoProxy() {
		// TODO Auto-generated constructor stub
	}

	private void destroy() {
		this.getMongo().close();
	}
	
	public static MongoClient getMongo() {
		return mongo;
	}
	
	
	public static MongoDatabase getDB() {
		return db;
	}

	
	public static MongoDatabase getDB(String database) {
		return mongo.getDatabase(database);
	}


	public static void setDB(MongoDatabase db) {
		MongoProxy.db = db;
		
	}

	public static void setDB(String db) {
		MongoProxy.db = mongo.getDatabase(db);
		
	}
	

	public static MongoCollection<Document> getCollection() {
		return collection;
	}


	public static void setCollection(MongoCollection<Document> collection) {
		MongoProxy.setCollection(collection);
	}

	public static long getCollectionSize() {
		return (getCollection().count());
	}

	public long getCollectionSize(String database, String collection) {
		return (getDB(database).getCollection(collection).count());
	}

	public static void insert(Document data){

		getCollection().insertOne(data);
		
	}

	public static void insert(List<Document> dataList){
		getCollection().insertMany(dataList);
	}

	
	public static void insert(String database, String collection, Document data){

		getDB().getCollection(collection).insertOne(data);

	}

	public static void insert(String database, String collection, List<Document> dataList){

		getDB().getCollection(collection).insertMany(dataList);
	
	}

	
	
	public Document get(String database, String collection, Document data){
		
		return null;
		
	}

	public Document getFirst(String database, String collection){
		
		return this.getDB(database).getCollection(collection).find().first();
		
	}
	
	public Document getFirst(String collection){
		
		return this.getDB().getCollection(collection).find().first();
		
	}
	
	public Document getDocument(String collection, String key){
		
		return null;
	}
	
	public List<Document> getAllDocuments(String collection){
		List<Document> dataList = new ArrayList<Document>();
				
		for (Document data : this.getDB().getCollection(collection).find()) {
            //System.out.println(cur.toJson());
			dataList.add(data);
        }
		return dataList;
	}
	
	public void update(String database, String collection, Document data){
		
	}

	public void deleteFirst(String database, String collection){
        getDB(database).getCollection(collection).deleteOne(this.getDB().getCollection(collection).find().first());
	}
	
	public void deleteFirst(String collection){
        getDB().getCollection(collection).deleteOne(this.getDB().getCollection(collection).find().first());
	}
	
	public void delete(String database, String collection, Document data){
        getDB(database).getCollection(collection).deleteOne(data);
	}
	
	public void dropCollection(String database, String collection){
		mongo.getDatabase(database).getCollection(collection).drop();
	}
	
	public void dropDatabase(String database){
		mongo.dropDatabase(database);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(MongoProxy.getMongo().getDatabaseNames());
		System.out.println(MongoProxy.getDB().getName());
		System.out.println(MongoProxy.getCollection());
		System.out.println(MongoProxy.getCollectionSize());

		
		Document entry = new Document("doc1", "ExpDB")
				.append("text", "loren ipsun...")
				.append("seed", "http://wikipedia.com")
				.append("links", "http://uol.com.br")
				.append("date", Instant.now().toString());


		MongoProxy.insert(entry);
		System.out.println(MongoProxy.getCollectionSize());
		
		List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }
		
        MongoProxy.insert(documents);
        System.out.println(MongoProxy.getCollectionSize());
		
	}

}
