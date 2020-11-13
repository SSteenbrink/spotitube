package dea.datasource.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dea.datasource.dao.interfaces.ITrackDao;
import dea.services.domain_objects.Song;
import dea.services.domain_objects.Track;
import dea.services.domain_objects.Video;
import org.bson.Document;

import javax.enterprise.inject.Alternative;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

@Alternative
public class MongoTrackDao extends MongoDao implements ITrackDao {

    MongoCollection<Document> tracks;

    public MongoTrackDao() {
        super();
        try {
            tracks = getDatabase().getCollection(getCollectionName());
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
        try {
            List<Document> docs = new ArrayList<>();
            tracks.find().into(docs);

            List<Track> results = new ArrayList<>();
            for(Document doc : docs) {
                results.add(convertBsonDocToTrack(doc));
            }
            return results;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public Track find(long id) {
        try {
            FindIterable<Document> result = tracks.find(eq("_id", id));
            Document doc = result.first();
            if(doc == null) {
                throw new NotFoundException();
            }
            return convertBsonDocToTrack(doc);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public List<Track> findByPlaylist(long id) {
        try {
            List<Document> docs = new ArrayList<>();

            tracks.find(in("playlists", id)).into(docs);

            List<Track> results = new ArrayList<>();
            for(Document doc : docs) {
                results.add(convertBsonDocToTrack(doc));
            }
            return results;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public List<Track> findEligibleForPlaylist(long id) {
        try {
            List<Document> docs = new ArrayList<>();

            tracks.find(nin("playlists", id))
                    .projection(fields(exclude("playlists"))).into(docs);

            List<Track> results = new ArrayList<>();
            for(Document doc : docs) {
                results.add(convertBsonDocToTrack(doc));
            }
            return results;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    /*
    * This is needed because the POJO converter does not support conditional (trackType=SONG/VIDEO...) conversion of objects
    */
    private static Track convertBsonDocToTrack(Document doc) {
        if(doc.get("trackType") == "SONG") {
            return new Song(
                    (long) doc.get("_id"),
                    (String) doc.get("title"),
                    (String) doc.get("performer"),
                    (String) doc.get("url"),
                    (int) doc.get("length"),
                    (boolean) doc.get("availableOffline"),
                    (String) doc.get("album"));
        } else {
            return new Video(
                    (long) doc.get("_id"),
                    (String) doc.get("title"),
                    (String) doc.get("performer"),
                    (String) doc.get("url"),
                    (int) doc.get("length"),
                    (boolean) doc.get("availableOffline"),
                    (String) doc.get("publicationDate"),
                    (String) doc.get("description"));
        }
    }
}
