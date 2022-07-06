package mongo.config.collections_mongo_db;

import com.mongodb.client.MongoCollection;
import mongo.config.ConfigDataBase;
import mongo.enums.EnumNameAllCollections;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;


public class ConfigDBCollections extends ConfigDataBase {


    private final Map<EnumNameAllCollections, MongoCollection<Document>> allCollectionsMongoDB = new HashMap<>();

    public ConfigDBCollections(String host, int port, String dataBaseName) {
        super(host, port, dataBaseName);
    }


    public void addCollectionsName(EnumNameAllCollections... nameCollection) {

        for (EnumNameAllCollections currentCollection : nameCollection) {
            allCollectionsMongoDB.put(currentCollection, super.getDatabase().getCollection(currentCollection.name()));
        }
    }

    public void addCollectionOne(EnumNameAllCollections nameCollection) {
        allCollectionsMongoDB.put(nameCollection, super.getDatabase().getCollection(nameCollection.name()));
    }

    public MongoCollection<Document> getCollectionForName(EnumNameAllCollections nameCollection) {
        return allCollectionsMongoDB.get(nameCollection);
    }

    public Map<EnumNameAllCollections, MongoCollection<Document>> getAllInitCollections() {
        return allCollectionsMongoDB;
    }

}
