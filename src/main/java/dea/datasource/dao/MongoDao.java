package dea.datasource.dao;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDao {
    protected MongoClient mongoClient;

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

    protected MongoDatabase getDatabase() {
        try {
            return mongoClient.getDatabase("spotitube");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
