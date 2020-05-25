package dea.services.interfaces;

import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Track;

import java.util.List;

public interface IPlaylistService {

    List<Playlist> getAll();

    Playlist find(long id);

    void delete(Playlist playlist);

    void add(Playlist playlist);

    void edit(Playlist playlist);

    void removeTrackFromPlaylist(Playlist playlist, Track track);

    void addTrackToPlaylist(Playlist playlist, Track track);
}
