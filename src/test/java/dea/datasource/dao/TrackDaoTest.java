package dea.datasource.dao;

import dea.StubUtility;
import dea.datasource.dao.mappers.IDataMapper;
import dea.services.domain_objects.Customer;
import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.List;

public class TrackDaoTest {

    TrackDao sut;
    Connection connectionMock;
    IDataMapper trackMapperMock;

    @Before
    public void setUp() {
        sut = new TrackDao();
        connectionMock = Mockito.mock(Connection.class);
        trackMapperMock = Mockito.mock(IDataMapper.class);
        sut.setConnection(connectionMock);
        sut.setTrackMapper(trackMapperMock);
    }

    @Test
    public void findAll() {
        // Arrange
        List<Track> expected = StubUtility.getTrackStubs();
        Mockito.doReturn(expected).when(trackMapperMock).findMany(Mockito.any(), Mockito.any());

        // Act
        List<Track> actual = sut.findAll();

        // Assert
        Mockito.verify(trackMapperMock).findMany(Mockito.any(), Mockito.any());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void find() {
        // Arrange
        Track expected = StubUtility.getTrackStubs().get(0);
        Mockito.when(trackMapperMock.find(Mockito.anyLong(), Mockito.any())).thenReturn(expected);

        // Act
        Track actual = sut.find(expected.getId());

        // Assert
        Mockito.verify(trackMapperMock).find(expected.getId(), connectionMock);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByPlaylist() {
        // Arrange
        List<Track> expected = StubUtility.getTrackStubs();
        Mockito.doReturn(expected).when(trackMapperMock).findMany(Mockito.any(), Mockito.any());

        // Act
        List<Track> actual = sut.findByPlaylist(1L);

        // Assert
        Mockito.verify(trackMapperMock).findMany(Mockito.any(), Mockito.any());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findEligibleForPlaylist() {
        // Arrange
        List<Track> expected = StubUtility.getTrackStubs();
        Mockito.doReturn(expected).when(trackMapperMock).findMany(Mockito.any(), Mockito.any());

        // Act
        List<Track> actual = sut.findEligibleForPlaylist(1L);

        // Assert
        Mockito.verify(trackMapperMock).findMany(Mockito.any(), Mockito.any());
        Assert.assertEquals(expected, actual);
    }
}