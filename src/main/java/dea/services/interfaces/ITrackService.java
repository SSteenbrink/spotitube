package dea.services.interfaces;

import dea.services.domain_objects.Track;
import java.util.List;

public interface ITrackService {

    List<Track> getAll();

    Track find(long id);

    List<Track> getEligibleForPlaylist(long id);

    List<Track> getByPlaylist(long id);
}
