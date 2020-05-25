package dea.controllers;

import dea.StubUtility;
import dea.services.domain_objects.Track;
import dea.controllers.dto.TrackResponseDto;
import dea.services.interfaces.ITrackService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.List;

public class TrackControllerTest {

    private TrackController sut;
    private ITrackService mockedTrackService;
    private List<Track> stubTracks;


    @Before
    public void setUp() throws Exception {
        sut = new TrackController();

        this.mockedTrackService = Mockito.mock(ITrackService.class);

        this.sut.setTrackService(mockedTrackService);

        stubTracks = StubUtility.getTrackStubs();
    }

    @Test
    public void getAllReturnsAllTracksFromService() {
        // Arrange
        Mockito.when(mockedTrackService.getAll()).thenReturn(stubTracks);

        // Act
        Response result = sut.getAll(0, "");

        // Assert
        Mockito.verify(mockedTrackService).getAll();
        Assert.assertEquals(((TrackResponseDto)result.getEntity()).getTracks().size(), stubTracks.size());
    }

    @Test
    public void getAllReturnsTracksForPlayListFromService() {
        // Arrange
        Mockito.when(mockedTrackService.getEligibleForPlaylist(Mockito.anyLong())).thenReturn(stubTracks);

        // Act
        Response result = sut.getAll(1, "");

        // Assert
        Mockito.verify(mockedTrackService).getEligibleForPlaylist(Mockito.anyLong());
        Assert.assertEquals(((TrackResponseDto)result.getEntity()).getTracks().size(), stubTracks.size());
    }
}