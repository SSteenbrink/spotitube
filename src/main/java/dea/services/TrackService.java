package dea.services;

import dea.datasource.dao.interfaces.ITrackDao;
import dea.services.domain_objects.Track;
import dea.services.interfaces.ITrackService;

import javax.inject.Inject;
import java.util.List;

public class TrackService implements ITrackService {

    private ITrackDao trackDao;

    @Inject
    public void setTrackDao(ITrackDao trackDao) {
        this.trackDao = trackDao;
    }

    @Override
    public List<Track> getAll() {
        return trackDao.findAll();
    }

    @Override
    public Track find(long id) {
        return trackDao.find(id);
    }

    @Override
    public List<Track> getEligibleForPlaylist(long id) {
        return trackDao.findEligibleForPlaylist(id);
    }

    @Override
    public List<Track> getByPlaylist(long id) {
        return trackDao.findByPlaylist(id);
    }


}
