package dea.datasource.dao;

import dea.datasource.dao.interfaces.ITrackDao;
import dea.datasource.dao.mappers.IDataMapper;
import dea.services.domain_objects.Track;
import dea.datasource.dao.mappers.TrackMapper;
import java.util.List;

public class TrackDao extends Dao implements ITrackDao {

    private IDataMapper trackMapper = new TrackMapper();

    @Override
    public List<Track> findAll() {
        return (List<Track>)trackMapper.findMany(new FindAllTracks(), conn);
    }

    @Override
    public Track find(long id) {
        return (Track) trackMapper.find(id, conn);
    }

    static class FindAllTracks implements StatementSource {

        public String sql() {
            return "SELECT t.*, NULL as availableOffline FROM tracks t";
        }

        public Object[] parameters() {
            return new Object[0];
        }
    }

    @Override
    public List<Track> findByPlaylist(long id) {
        return (List<Track>)trackMapper.findMany(new FindTrackByPlaylist(id), conn);
    }

    static class FindTrackByPlaylist implements StatementSource {
        long playlistId;

        FindTrackByPlaylist(long id) {
            this.playlistId = id;
        }

        public String sql() {
            return "SELECT t.*, pt.availableOffline FROM tracks t\n" +
                    "JOIN playlists_tracks pt ON pt.track_id = t.id\n" +
                    "WHERE pt.playlist_id = ?";
        }

        public Object[] parameters() {
            Object[] parameters = new Object[1];
            parameters[0] = playlistId;
            return parameters;
        }
    }

    @Override
    public List<Track> findEligibleForPlaylist(long id) {
        return (List<Track>)trackMapper.findMany(new FindTrackEligibleForPlaylist(id), conn);
    }

    static class FindTrackEligibleForPlaylist implements StatementSource {
        long playlistId;

        FindTrackEligibleForPlaylist(long id) {
            this.playlistId = id;
        }

        public String sql() {
            return "SELECT t.*, pt.availableOffline FROM tracks t\n" +
                    "LEFT JOIN playlists_tracks pt ON pt.track_id = t.id\n" +
                    "WHERE t.id NOT IN( \n" +
                    "                   SELECT ptt.track_id \n" +
                    "                   FROM playlist p \n" +
                    "                   INNER JOIN playlists_tracks ptt ON p.id = ptt.playlist_id \n" +
                    "                   WHERE p.id = ?)" +
                    "GROUP BY t.id";
        }

        public Object[] parameters() {
            Object[] parameters = new Object[1];
            parameters[0] = playlistId;
            return parameters;
        }
    }

    public void setTrackMapper(IDataMapper trackMapper) {
        this.trackMapper = trackMapper;
    }
}


