package dea.services;

import dea.datasource.dao.interfaces.IPlaylistDao;
import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Track;
import dea.services.interfaces.IPlaylistService;
import dea.services.interfaces.ITrackService;

import javax.inject.Inject;
import java.util.List;

public class PlaylistService implements IPlaylistService {

    private IPlaylistDao playlistDao;

    @Inject
    public void setPlaylistDao(IPlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    public List<Playlist> getAll() {
        return playlistDao.findAll();
    }

    public Playlist find(long id) {
        return playlistDao.find(id);
    }

    public void add(Playlist playlist) {
        playlistDao.insert(playlist);
    }

    public void edit(Playlist playlist) {
        playlistDao.update(playlist);
    }

    public void removeTrackFromPlaylist(Playlist playlist, Track track) {
        playlistDao.removeTrackFromPlaylist(playlist, track);
    }

    public void delete(Playlist playlist) {
        playlistDao.delete(playlist);
    }

    public void addTrackToPlaylist(Playlist playlist, Track track) {
        playlistDao.addTrackToPlaylist(playlist, track);
    }


}
