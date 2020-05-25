package dea.controllers;

import dea.StubUtility;
import dea.controllers.dto.TrackDto;
import dea.controllers.dto.TrackResponseDto;
import dea.services.domain_objects.Customer;
import dea.services.domain_objects.Playlist;
import dea.controllers.dto.PlaylistDto;
import dea.controllers.dto.PlaylistResponseDto;
import dea.services.domain_objects.Song;
import dea.services.domain_objects.Track;
import dea.services.interfaces.ICustomerService;
import dea.services.interfaces.IPlaylistService;
import dea.services.interfaces.ITrackService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.rmi.CORBA.Stub;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.List;

public class PlaylistControllerTest {

    private PlaylistController sut;
    private ICustomerService mockCustomerService;
    private IPlaylistService mockPlaylistService;
    private ITrackService mockTrackService;

    private SecurityContext mockSecurityContext;

    private String stubUsername = "mockusername";
    private String stubPassword = "mockPwd";
    private String stubToken = "TOKEN-MOCK-123";
    private String stubFirstname = "Mock";
    private String stubLastname = "ito";
    private Customer stubUser = new Customer(1L, stubUsername, stubPassword, stubFirstname, stubLastname);

    @Before
    public void setUp() {
        sut = new PlaylistController();
        mockCustomerService = Mockito.mock(ICustomerService.class);
        mockPlaylistService = Mockito.mock(IPlaylistService.class);
        mockTrackService = Mockito.mock(ITrackService.class);
        mockSecurityContext = Mockito.mock(SecurityContext.class);

        sut.setTrackService(mockTrackService);
        sut.setCustomerService(mockCustomerService);
        sut.setPlaylistService(mockPlaylistService);
        sut.setSecurityContext(mockSecurityContext);

        Mockito.when(mockCustomerService.findByUsername(Mockito.any())).thenReturn(stubUser);
        Mockito.when(mockSecurityContext.getUserPrincipal()).thenReturn(() -> stubUser.getUsername());
        Mockito.when(mockTrackService.getByPlaylist(Mockito.anyLong())).thenReturn(StubUtility.getTrackStubs());

        List<Playlist> playlistStubs = StubUtility.getPlaylistStubs();
        Mockito.when(mockPlaylistService.getAll()).thenReturn(playlistStubs);
    }

    @Test
    public void getAllReturnsCorrectPlaylist() {
        // Arrange
        List<PlaylistDto> playlistDtoStubs = StubUtility.getPlaylistDtoStubs();

        PlaylistResponseDto expected = new PlaylistResponseDto();
        expected.setPlaylists(playlistDtoStubs);

        // Act
        PlaylistResponseDto actual = (PlaylistResponseDto) sut.getAll(stubToken).getEntity();

        // Assert
        Mockito.verify(mockPlaylistService).getAll();
        Assert.assertEquals(expected.getPlaylists().size(), actual.getPlaylists().size());
    }

    @Test
    public void deletePlaylistChecksOwnerAndCallsDelete() {
        // Arrange
        List<PlaylistDto> mockPlaylistDtos = StubUtility.getPlaylistDtoStubs();

        PlaylistResponseDto expected = new PlaylistResponseDto();
        expected.setPlaylists(mockPlaylistDtos);

        long mockId = 5L;
        Playlist mockPlaylist = StubUtility.getPlaylistStubs().get(0);
        Mockito.when(mockPlaylistService.find(mockId)).thenReturn(mockPlaylist);

        // Act
        sut.deletePlaylist(mockId, stubToken).getEntity();

        // Assert
        Mockito.verify(mockPlaylistService).find(mockId);
        Mockito.verify(mockPlaylistService).delete(mockPlaylist);
    }

    @Test
    public void addPlaylistChecksOwnerAndCallsDelete() {
        // Arrange
        String newPlaylistName = "New playlist";

        Playlist stubPlaylist = new Playlist(-1L, newPlaylistName, stubUser.getId());
        PlaylistDto playListDto = new PlaylistDto();
        playListDto.setId(-1);
        playListDto.setName(newPlaylistName);
        playListDto.setOwner(false);
        // Act
        sut.addPlaylist(stubToken, playListDto).getEntity();

        // Assert
        Mockito.verify(mockPlaylistService).add(stubPlaylist);
        Mockito.verify(mockPlaylistService).getAll();
    }

    @Test
    public void testAddNullPlaylistReturnsBadRequest() {
        // Arrange
        Response.StatusType expected = Response.Status.BAD_REQUEST;

        // Act
        Response.StatusType actual = sut.addPlaylist(stubToken, null).getStatusInfo();

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void editPlaylistChecksOwnerAndCallsEdit() {
        // Arrange
        Playlist stubPlaylist = new Playlist(1L, "Zachte muziek", stubUser.getId());

        Mockito.when(mockPlaylistService.find(1L)).thenReturn(stubPlaylist);

        PlaylistDto playListDto = new PlaylistDto();
        playListDto.setId(1L);
        playListDto.setName("");
        playListDto.setOwner(false);

        // Act
        sut.editPlaylist(1L, stubToken, playListDto).getEntity();

        // Assert
        Mockito.verify(mockPlaylistService).edit(stubPlaylist);
        Mockito.verify(mockPlaylistService).getAll();
    }

    @Test
    public void TestEditNullPlaylistReturnsBadRequest() {
        // Arrange
        Response.StatusType expected = Response.Status.BAD_REQUEST;

        // Act
        Response.StatusType actual = sut.editPlaylist(1L, stubToken, null).getStatusInfo();

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTracksByPlaylist() {
        // Arrange
        List<Track> trackStubs = StubUtility.getTrackStubs();

        Mockito.when(mockTrackService.getByPlaylist(Mockito.anyLong())).thenReturn(trackStubs);
        TrackResponseDto expected = new TrackResponseDto();
        expected.setTracks(StubUtility.getTrackDtoStubs());

        // Act
        TrackResponseDto actual = (TrackResponseDto) sut.getTracksByPlaylist(1L, stubToken).getEntity();

        // Assert
        Mockito.verify(mockTrackService).getByPlaylist(1L);
        Assert.assertEquals(expected.getTracks().size(), actual.getTracks().size());
    }

    @Test
    public void testAddTrackToPlaylist() {
        // Arrange
        Playlist stubPlaylist = new Playlist(1L, "Zachte muziek", stubUser.getId());
        Track stubtrack = StubUtility.getTrackStubs().get(0);
        Mockito.when(mockPlaylistService.find(1L)).thenReturn(stubPlaylist);
        Mockito.when(mockTrackService.find(1L)).thenReturn(stubtrack);

        TrackDto trackDto = StubUtility.getTrackDtoStubs().get(0);
        trackDto.setId(1L);

        PlaylistResponseDto expected = new PlaylistResponseDto();
        expected.setPlaylists(StubUtility.getPlaylistDtoStubs());

        // Act
        sut.addTrackToPlaylist(1L, stubToken, trackDto);

        // Assert
        Mockito.verify(mockPlaylistService).addTrackToPlaylist(Mockito.any(), Mockito.any());
    }

    @Test
    public void TestAddNullTrackToPlaylistReturnsBadRequest() {
        // Arrange
        Response.StatusType expected = Response.Status.BAD_REQUEST;

        // Act
        Response.StatusType actual = sut.addTrackToPlaylist(1L, stubToken, null).getStatusInfo();

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteTrackFromPlaylist() {
        // Arrange
        Playlist stubPlaylist = new Playlist(1L, "Zachte muziek", stubUser.getId());
        Track stubtrack = StubUtility.getTrackStubs().get(0);
        Mockito.when(mockPlaylistService.find(1L)).thenReturn(stubPlaylist);
        Mockito.when(mockTrackService.find(1L)).thenReturn(stubtrack);

        TrackDto trackDto = StubUtility.getTrackDtoStubs().get(0);
        trackDto.setId(1L);

        PlaylistResponseDto expected = new PlaylistResponseDto();
        expected.setPlaylists(StubUtility.getPlaylistDtoStubs());

        // Act
        sut.deleteTrackFromPlaylist(stubPlaylist.getId(), stubtrack.getId(), stubToken);

        // Assert
        Mockito.verify(mockPlaylistService).removeTrackFromPlaylist(Mockito.any(), Mockito.any());
    }

    @Test
    public void TestDeleteNullTrackFromPlaylistReturnsBadRequest() {
        // Arrange
        Mockito.when(mockPlaylistService.find(1L)).thenReturn(null);
        Mockito.when(mockTrackService.find(1L)).thenReturn(null);

        Response.StatusType expected = Response.Status.BAD_REQUEST;

        // Act
        Response.StatusType actual = sut.deleteTrackFromPlaylist(1L, 1L, stubToken).getStatusInfo();

        // Assert
        Assert.assertEquals(expected, actual);
    }
}