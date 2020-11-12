package dea.datasource.dao;

import com.mongodb.client.MongoCollection;
import dea.services.domain_objects.Playlist;

import javax.ws.rs.InternalServerErrorException;

public class MongoPlaylistDao extends MongoDao {

    MongoCollection<Playlist> customers;

    public MongoPlaylistDao() {
        super();
        try {
            customers = getDatabase().getCollection("playlist", Playlist.class);
        } catch(Exception e) {
            throw new InternalServerErrorException();
        }
    }

}
