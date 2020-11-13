package dea.datasource.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.ws.rs.InternalServerErrorException;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public abstract class MongoDao {
    protected MongoClient mongoClient;
    protected String collectionName;

    ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");

    public MongoDao() {
        PojoCodecProvider codecProvider = PojoCodecProvider.builder()
                .automatic(true)
                .build();

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(codecProvider));

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(pojoCodecRegistry)
                .build();
        try {
            mongoClient = MongoClients.create(clientSettings);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected abstract String getCollectionName();

    protected MongoDatabase getDatabase() {
        try {
            return mongoClient.getDatabase("spotitube");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * from: https://www.tutorialspoint.com/mongodb/mongodb_autoincrement_sequence.htm
     */
    protected long getNextIdValue() {
        MongoCollection<Document> collection = getDatabase().getCollection("counters");
        try {
            Document counter = collection.findOneAndUpdate(eq("_id", getCollectionName()), new Document("$inc", new Document("sequence_value", 1L)));
            return (long) counter.get("sequence_value") + 1;
        } catch(Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }
}
