package dea.datasource.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import dea.datasource.dao.interfaces.IPlaylistDao;
import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Track;
import org.bson.Document;

import javax.enterprise.inject.Alternative;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotSupportedException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

@Alternative
public class MongoPlaylistDao extends MongoDao implements IPlaylistDao {

    MongoCollection<Playlist> playlists;
    MongoCollection<Document> tracks;

    public MongoPlaylistDao() {
        super();
        try {
            playlists = getDatabase().getCollection(getCollectionName(), Playlist.class);
            tracks = getDatabase().getCollection("tracks");
        } catch(Exception e) {
            throw new InternalServerErrorException();
        }
    }

    @Override
    protected String getCollectionName() {
        return "playlists";
    }

    @Override
    public List<Playlist> findAll() {
        try {
            List<Playlist> results = new ArrayList<>();
            playlists.find().projection(fields(exclude("tracks"))).into(results);
            return results;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public Playlist find(long id) {
        try {
            return playlists.find(eq("_id", id)).projection(fields(exclude("tracks"))).first();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public void insert(Playlist playlist) {
        try {
            System.out.println(playlist.toString());
            playlist.setId(getNextIdValue());
            playlists.insertOne(playlist);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public void update(Playlist playlist) {
        try {
            playlists.replaceOne(eq("id", playlist.getId()), playlist);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public void delete(Playlist playlist) {
        try {
            UpdateOptions options = new UpdateOptions();
            options = options.bypassDocumentValidation(true);

            tracks.updateMany(new Document(), new Document("$pull", new Document("playlists", playlist.getId())), options);

            playlists.deleteOne(eq("_id", playlist.getId()));
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public void addTrackToPlaylist(Playlist playlist, Track track) {
        try {
            UpdateOptions options = new UpdateOptions();
            options = options.bypassDocumentValidation(true);

            playlists.updateOne(eq("_id", playlist.getId()), new Document("$push", new Document("tracks", track.getId())), options);


            tracks.updateOne(eq("_id", track.getId()), new Document("$push", new Document("playlists", playlist.getId())), options);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public void removeTrackFromPlaylist(Playlist playlist, Track track) {
        try {
            UpdateOptions options = new UpdateOptions();
            options = options.bypassDocumentValidation(true);

            playlists.updateOne(eq("_id", playlist.getId()), new Document("$pull", new Document("tracks", track.getId())), options);

            MongoCollection<Document> tracks = getDatabase().getCollection("tracks");
            tracks.updateOne(eq("_id", track.getId()), new Document("$pull", new Document("playlists", playlist.getId())), options);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }
}
