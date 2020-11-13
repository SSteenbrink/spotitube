package dea.datasource.dao;

import com.mongodb.client.MongoCollection;
import dea.datasource.dao.interfaces.ITrackDao;
import dea.services.domain_objects.Track;

import javax.enterprise.inject.Alternative;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;

@Alternative
public class MongoTracksDao extends MongoDao implements ITrackDao {

    MongoCollection<Track> tracks;

    public MongoTracksDao() {
        super();
        try {
            tracks = getDatabase().getCollection(getCollectionName(), Track.class);
        } catch(Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    protected String getCollectionName() {
        return "tracks";
    }

    @Override
    public List<Track> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Track find(long id) {
        return null;
    }

    @Override
    public List<Track> findByPlaylist(long id) {
        return new ArrayList<>();
    }

    @Override
    public List<Track> findEligibleForPlaylist(long id) {
        return new ArrayList<>();
    }
}
