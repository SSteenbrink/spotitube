package dea.datasource.dao;

import dea.StubUtility;
import dea.datasource.dao.mappers.IDataMapper;
import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.ServiceUnavailableException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PlaylistDaoTest {

    PlaylistDao sut;
    Connection connectionMock;
    IDataMapper playlistMapperMock;

    @Before
    public void setUp() {
        sut = new PlaylistDao();
        connectionMock = Mockito.mock(Connection.class);
        playlistMapperMock = Mockito.mock(IDataMapper.class);
        sut.setPlaylistMapper(playlistMapperMock);
        sut.setConnection(connectionMock);
    }

    @Test
    public void findAll() {
        // Arrange
        List<Playlist> expected = StubUtility.getPlaylistStubs();

        Mockito.doReturn(expected).when(playlistMapperMock).findMany(Mockito.any(), Mockito.any());

        // Act
        List<Playlist> actual = sut.findAll();

        // Assert
        Mockito.verify(playlistMapperMock).findMany(Mockito.any(), Mockito.any());
        Assert.assertEquals(expected.size(), actual.size());
    }

    @Test
    public void find() {
        // Arrange
        Playlist playlist = StubUtility.getPlaylistStubs().get(0);

        // Act
        sut.find(playlist.getId());

        // Assert
        Mockito.verify(playlistMapperMock).find(playlist.getId(), connectionMock);
    }

    @Test
    public void insert() {
        Playlist playlist = StubUtility.getPlaylistStubs().get(0);

        // Act
        sut.insert(playlist);

        // Assert
        Mockito.verify(playlistMapperMock).insert(playlist, connectionMock);
    }

    @Test
    public void update() {
        // Arrange
        Playlist playlist = StubUtility.getPlaylistStubs().get(0);

        sut.update(playlist);

        // Assert
        Mockito.verify(playlistMapperMock).update(playlist, connectionMock);
    }

    @Test
    public void delete() {
        // Arrange
        Playlist playlist = StubUtility.getPlaylistStubs().get(0);

        // Act
        sut.delete(playlist);

        // Assert
        Mockito.verify(playlistMapperMock).delete(playlist, connectionMock);
    }

    @Test
    public void addTrackToPlaylist() throws SQLException {
        // Arrange
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionMock.prepareStatement(Mockito.any())).thenReturn(preparedStatementMock);

        Playlist playlist = StubUtility.getPlaylistStubs().get(0);
        Track track = StubUtility.getTrackStubs().get(0);

        // Act
        sut.addTrackToPlaylist(playlist, track);

        // Assert
        Mockito.verify(preparedStatementMock).execute();
    }

    @Test(expected = ServiceUnavailableException.class)
    public void addTrackToPlaylistThrowsException() throws SQLException {
        // Arrange
        Mockito.when(connectionMock.prepareStatement(Mockito.any())).thenThrow(new SQLException());

        Playlist playlist = StubUtility.getPlaylistStubs().get(0);
        Track track = StubUtility.getTrackStubs().get(0);

        // Act
        sut.addTrackToPlaylist(playlist, track);
    }

    @Test
    public void removeTrackFromPlaylist() throws SQLException {
        // Arrange
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionMock.prepareStatement(Mockito.any())).thenReturn(preparedStatementMock);

        Playlist playlist = StubUtility.getPlaylistStubs().get(0);
        Track track = StubUtility.getTrackStubs().get(0);

        // Act
        sut.removeTrackFromPlaylist(playlist, track);

        // Assert
        Mockito.verify(preparedStatementMock).execute();
    }

    @Test(expected = ServiceUnavailableException.class)
    public void removeTrackFromPlaylistThrowsException() throws SQLException {
        // Arrange
        Mockito.when(connectionMock.prepareStatement(Mockito.any())).thenThrow(new SQLException());

        Playlist playlist = StubUtility.getPlaylistStubs().get(0);
        Track track = StubUtility.getTrackStubs().get(0);

        // Act
        sut.removeTrackFromPlaylist(playlist, track);
    }
}