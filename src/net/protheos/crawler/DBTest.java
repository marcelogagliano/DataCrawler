/**
 * 
 */
package net.protheos.crawler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @author marce
 *
 */
public class DBTest {

	private static MongoClient mongo;

	/**
	 * 
	 */
	public DBTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mongo = new MongoClient( "localhost" , 27017 );		
		MongoDatabase dbCrawler = mongo.getDatabase("crawlerdb");
		
		MongoCollection<Document> table = dbCrawler.getCollection("test");

		System.out.println(table.count());

		dbCrawler.drop();
        
		System.out.println(table.count());
		
		Document entry = new Document("doc1", "ExpDB")
							.append("text", "loren ipsun...")
							.append("seed", "http://wikipedia.com")
							.append("links", "http://uol.com.br")
							.append("date", Instant.now().toString());
		
		table.insertOne(entry);

		System.out.println(table.count());

		// get it (since it's the only one in there since we dropped the rest earlier on)
        Document myDoc = table.find().first();
        System.out.println(myDoc.toJson());
		
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }
        
        table.insertMany(documents);
        System.out.println("total # of documents after inserting 100 small ones (should be 101) " + table.count());
        
        for (Document cur : table.find()) {
            System.out.println(cur.toJson());
        }
        
	}

}
