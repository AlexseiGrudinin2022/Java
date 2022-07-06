package mongo.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class ConfigDataBase {


    protected final MongoClient mongoClient;
    protected final MongoDatabase database;
    Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );

    public ConfigDataBase(String host, int port, String dataBaseName) {
        mongoClient = new MongoClient(host, port);
        database = mongoClient.getDatabase(dataBaseName);
        mongoLogger.setLevel(Level.SEVERE);
    }

    public void close() {
        mongoClient.close();
    }


    public MongoDatabase getDatabase() {
        return database;
    }



}
