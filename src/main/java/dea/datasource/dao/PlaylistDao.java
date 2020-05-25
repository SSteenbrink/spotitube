package dea.datasource.dao;

import dea.datasource.dao.interfaces.IPlaylistDao;
import dea.datasource.dao.mappers.IDataMapper;
import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Track;
import dea.datasource.dao.mappers.PlaylistMapper;

import javax.ws.rs.ServiceUnavailableException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PlaylistDao extends Dao implements IPlaylistDao {

    private IDataMapper playlistMapper = new PlaylistMapper();

    @Override
    public List<Playlist> findAll() {
        return (List<Playlist>) playlistMapper.findMany(new FindAllPlaylists(), conn);
    }

    static class FindAllPlaylists implements StatementSource {
        @Override
        public String sql() {
            return "SELECT * FROM playlist";
        }

        @Override
        public Object[] parameters() {
            return new Object[0];
        }
    }

    @Override
    public Playlist find(long id) {
        return (Playlist)playlistMapper.find(id, conn);
    }

    @Override
    public void insert(Playlist playlist) {
        playlistMapper.insert(playlist, conn);
    }

    @Override
    public void update(Playlist playlist) {
        playlistMapper.update(playlist, conn);
    }

    @Override
    public void delete(Playlist playlist) {
        playlistMapper.delete(playlist, conn);
    }

    @Override
    public void addTrackToPlaylist(Playlist playlist, Track track) {
        String sql = "INSERT INTO playlists_tracks (playlist_id, track_id, availableOffline) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, playlist.getId());
            preparedStatement.setLong(2, track.getId());
            preparedStatement.setBoolean(3, track.isAvailableOffline());
            preparedStatement.execute();
        } catch(SQLException e) {
            throw new ServiceUnavailableException();
        }
    }

    @Override
    public void removeTrackFromPlaylist(Playlist playlist, Track track) {
        String sql = "DELETE FROM playlists_tracks WHERE playlist_id = ? AND track_id = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, playlist.getId());
            preparedStatement.setLong(2, track.getId());
            preparedStatement.execute();
        } catch(SQLException e) {
            throw new ServiceUnavailableException();
        }
    }

    public void setPlaylistMapper(IDataMapper playlistMapper) {
        this.playlistMapper = playlistMapper;
    }
}
