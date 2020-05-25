package dea.datasource.dao.interfaces;

import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Track;

import java.util.List;

public interface IPlaylistDao {
    List<Playlist> findAll();
    Playlist find(long id);
    void insert(Playlist playlist);
    void update(Playlist playlist);
    void delete(Playlist playlist);
    void addTrackToPlaylist(Playlist playlist, Track track);
    void removeTrackFromPlaylist(Playlist playlist, Track track);
}
