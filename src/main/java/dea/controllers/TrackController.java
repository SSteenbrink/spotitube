package dea.controllers;

import dea.services.domain_objects.Track;
import dea.controllers.authentication.Secured;
import dea.controllers.dto.TrackDto;
import dea.controllers.dto.TrackResponseDto;
import dea.controllers.dto_mappers.TrackDtoMapper;
import dea.services.interfaces.ITrackService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Secured
@Path("/tracks")
public class TrackController {

    @Context
    SecurityContext securityContext;

    private ITrackService trackService;

    @Inject
    public void setTrackService(ITrackService trackService) {
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("forPlaylist") int forPlaylistId, @QueryParam("token") String token) {
        List<Track> tracks;
        if(forPlaylistId == 0) {
            tracks = trackService.getAll();
        } else {
            tracks = trackService.getEligibleForPlaylist((long) forPlaylistId);
        }

        List<TrackDto> trackDtos = new ArrayList<>();
        for(Track track: tracks) {
            trackDtos.add(TrackDtoMapper.mapToDto(track));
        }
        TrackResponseDto responseDto = new TrackResponseDto();
        responseDto.setTracks(trackDtos);

        return Response.status(200).entity(responseDto).build();
    }
}
