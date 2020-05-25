package dea.services;

import dea.StubUtility;
import dea.datasource.dao.interfaces.ITrackDao;
import dea.services.domain_objects.Song;
import dea.services.domain_objects.Track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class TrackServiceTest {

    ITrackDao mockTrackDao;

    TrackService sut;

    @Before
    public void setUp() throws Exception {
        sut = new TrackService();

        mockTrackDao = Mockito.mock(ITrackDao.class);
        sut.setTrackDao(mockTrackDao);
    }

    @Test
    public void getAll() {
        Mockito.when(mockTrackDao.findAll()).thenReturn(StubUtility.getTrackStubs());

        List<Track> expected = StubUtility.getTrackStubs();
        List<Track> actual = sut.getAll();
        Mockito.verify(mockTrackDao).findAll();
        Assert.assertEquals(expected.size(), actual.size());
    }

    @Test
    public void getEligibleForPlaylist() {
        Mockito.when(mockTrackDao.findEligibleForPlaylist(Mockito.anyLong())).thenReturn(StubUtility.getTrackStubs());

        List<Track> expected = StubUtility.getTrackStubs();
        List<Track> actual = sut.getEligibleForPlaylist(1L);
        Mockito.verify(mockTrackDao).findEligibleForPlaylist(Mockito.anyLong());
        Assert.assertEquals(expected.size(), actual.size());
    }

    @Test
    public void getByPlaylist() {
        Mockito.when(mockTrackDao.findByPlaylist(Mockito.anyLong())).thenReturn(StubUtility.getTrackStubs());

        List<Track> expected = StubUtility.getTrackStubs();
        List<Track> actual = sut.getByPlaylist(1L);
        Mockito.verify(mockTrackDao).findByPlaylist(Mockito.anyLong());
        Assert.assertEquals(expected.size(), actual.size());
    }

    @Test
    public void find() {
        Track expected = new Song(1L, "Performer", "Title", "http://example.com", 100, true, "Album");

        Mockito.when(mockTrackDao.find(Mockito.anyLong())).thenReturn(expected);

        Track actual = sut.find(1L);

        Mockito.verify(mockTrackDao).find(1L);
        Assert.assertEquals(expected, actual);
    }
}