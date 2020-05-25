package dea.datasource.dao.interfaces;

import dea.services.domain_objects.Track;
import java.util.List;

public interface ITrackDao {
    List<Track> findAll();
    Track find(long id);
    List<Track> findByPlaylist(long id);
    List<Track> findEligibleForPlaylist(long id);
}
