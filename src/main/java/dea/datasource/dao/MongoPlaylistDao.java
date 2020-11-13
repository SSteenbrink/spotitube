package dea.datasource.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertOneOptions;
import dea.datasource.dao.interfaces.IPlaylistDao;
import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Track;

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

    public MongoPlaylistDao() {
        super();
        try {
            playlists = getDatabase().getCollection(getCollectionName(), Playlist.class);
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
            return playlists.find(eq("id", id)).projection(fields(exclude("tracks"))).first();
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
            playlists.deleteOne(eq("id", playlist.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public void addTrackToPlaylist(Playlist playlist, Track track) {
        throw new NotSupportedException();
    }

    @Override
    public void removeTrackFromPlaylist(Playlist playlist, Track track) {
        throw new NotSupportedException();
    }
}
