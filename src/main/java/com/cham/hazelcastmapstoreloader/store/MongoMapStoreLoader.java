package com.cham.hazelcastmapstoreloader.store;

import com.cham.hazelcastmapstoreloader.domain.Trade;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapLoaderLifecycleSupport;
import com.hazelcast.core.MapStore;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static com.mongodb.client.model.Filters.eq;

public class MongoMapStoreLoader implements MapStore<String, Trade>, MapLoaderLifecycleSupport {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    public void init(HazelcastInstance hazelcastInstance, Properties properties, String mapName) {
        String mongoUrl = (String) properties.get("mongo.url");
        String dbName = (String) properties.get("mongo.db");
        String collectionName = (String) properties.get("mongo.collection");
        this.mongoClient = new MongoClient(new MongoClientURI(mongoUrl));
        this.collection = mongoClient.getDatabase(dbName).getCollection(collectionName);
    }

    public void destroy() {
        mongoClient.close();
    }


    public void store(String key, Trade trade) {
        System.out.println("Inside MongoMapStoreLoader.store");
        Document document = new Document("id", key)
                .append("type", trade.getType())
                .append("date", trade.getDate())
                .append("payLoad",trade.getPayLoad());
        this.collection.insertOne(document);
    }

    public void storeAll(Map<String, Trade> map) {

    }

    public void delete(String s) {

    }

    public void deleteAll(Collection<String> collection) {

    }

    public Trade load(String key) {
        System.out.println("Inside MongoMapStoreLoader.load()" + key);

        Document doc = this.collection.find(eq("type", key)).first();

        String id = (String) doc.get("id");
        String type = (String) doc.get("type");
        Date date = doc.getDate("date");
        String payLoad= (String) doc.get("payLoad");
        return new Trade(id, type, date,payLoad);
    }

    public Map<String, Trade> loadAll(Collection<String> collection) {
        return null;
    }

    public Iterable<String> loadAllKeys() {
        return null;
    }
}
