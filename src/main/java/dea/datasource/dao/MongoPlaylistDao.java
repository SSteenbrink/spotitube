package dea.datasource.dao;

import com.mongodb.client.MongoCollection;
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
            playlists = getDatabase().getCollection("playlists", Playlist.class);
        } catch(Exception e) {
            throw new InternalServerErrorException();
        }
    }

    @Override
    public List<Playlist> findAll() {
        try {
            List<Playlist> results = new ArrayList<>();
            playlists.find().projection(fields(exclude("tracks"), excludeId())).into(results);
            return results;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public Playlist find(long id) {
        try {
            return playlists.find(eq("id", id)).projection(fields(exclude("tracks"), excludeId())).first();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void insert(Playlist playlist) {
        try {
            playlists.insertOne(playlist);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void update(Playlist playlist) {
        try {
            playlists.replaceOne(eq("id", playlist.getId()), playlist);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void delete(Playlist playlist) {
        try {
            playlists.deleteOne(eq("id", playlist.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InternalServerErrorException();
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
