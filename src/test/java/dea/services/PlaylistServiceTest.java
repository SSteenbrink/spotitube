package dea.services;

import dea.StubUtility;
import dea.datasource.dao.interfaces.IPlaylistDao;
import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Song;
import dea.services.domain_objects.Track;
import dea.services.interfaces.ITrackService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.List;

public class PlaylistServiceTest {

    IPlaylistDao mockPlaylistDao;

    PlaylistService sut;

    @Before
    public void setUp() {
        sut = new PlaylistService();

        mockPlaylistDao = Mockito.mock(IPlaylistDao.class);
        sut.setPlaylistDao(mockPlaylistDao);

    }

    @Test
    public void getAll() {
        Mockito.when(mockPlaylistDao.findAll()).thenReturn(StubUtility.getPlaylistStubs());

        List<Playlist> expected = StubUtility.getPlaylistStubs();
        List<Playlist> actual = sut.getAll();
        Mockito.verify(mockPlaylistDao).findAll();
        Assert.assertEquals(expected.size(), actual.size());
    }

    @Test
    public void find() {
        Playlist expected = StubUtility.getPlaylistStubs().get(1);

        Mockito.when(mockPlaylistDao.find(Mockito.anyLong())).thenReturn(expected);

        Playlist actual = sut.find(2L);

        Mockito.verify(mockPlaylistDao).find(2L);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void add() {
        Playlist toBeAdded = StubUtility.getPlaylistStubs().get(0);

        sut.add(toBeAdded);

        Mockito.verify(mockPlaylistDao).insert(toBeAdded);
    }

    @Test
    public void edit() {
        Playlist toBeEdited = StubUtility.getPlaylistStubs().get(0);
        toBeEdited.setName("new name");

        sut.edit(toBeEdited);

        Mockito.verify(mockPlaylistDao).update(toBeEdited);
    }

    @Test
    public void removeTrackFromPlaylist() {
        Track trackToBeRemoved = StubUtility.getTrackStubs().get(0);
        Playlist playlist = StubUtility.getPlaylistStubs().get(0);

        sut.removeTrackFromPlaylist(playlist, trackToBeRemoved);

        Mockito.verify(mockPlaylistDao).removeTrackFromPlaylist(playlist, trackToBeRemoved);
    }

    @Test
    public void delete() {
        Playlist toBeDeleted = StubUtility.getPlaylistStubs().get(0);

        sut.delete(toBeDeleted);

        Mockito.verify(mockPlaylistDao).delete(toBeDeleted);
    }

    @Test
    public void addTrackToPlaylist() {
        Track trackToBeAdded = StubUtility.getTrackStubs().get(0);
        Playlist playlist = StubUtility.getPlaylistStubs().get(0);

        sut.addTrackToPlaylist(playlist, trackToBeAdded);

        Mockito.verify(mockPlaylistDao).addTrackToPlaylist(playlist, trackToBeAdded);
    }
}
