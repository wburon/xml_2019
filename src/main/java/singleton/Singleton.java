package singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Singleton {
	
	private String url = "localhost";
	private static String database = "notre_database";
	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	
	private Singleton(){
		if(mongoClient == null)
			mongoClient = new MongoClient(url);
	}
	
	public static MongoClient getInstance(){
		if(mongoClient == null)
			new Singleton();
		return mongoClient;
	}
	
	public static MongoDatabase getDatabase(){
		if(mongoDatabase == null)
			mongoDatabase = getInstance().getDatabase(database);
		return mongoDatabase;
	}
	
}
