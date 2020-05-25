package dea.controllers;

import dea.services.domain_objects.Customer;
import dea.services.domain_objects.Playlist;
import dea.services.domain_objects.Track;
import dea.controllers.authentication.Secured;
import dea.controllers.dto.PlaylistDto;
import dea.controllers.dto.PlaylistResponseDto;
import dea.controllers.dto.TrackDto;
import dea.controllers.dto.TrackResponseDto;
import dea.controllers.dto_mappers.PlaylistDtoMapper;
import dea.controllers.dto_mappers.TrackDtoMapper;
import dea.services.interfaces.ICustomerService;
import dea.services.interfaces.IPlaylistService;
import dea.services.interfaces.ITrackService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Secured
@Path("/playlists")
public class PlaylistController {

    @Context
    SecurityContext securityContext;

    private IPlaylistService playlistService;
    private ITrackService trackService;
    private ICustomerService customerService;

    @Inject
    public void setPlaylistService(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(ITrackService trackService) {
        this.trackService = trackService;
    }

    @Inject
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("token") String token) {
        Customer user = customerService.findByUsername(securityContext.getUserPrincipal().getName());

        PlaylistResponseDto responseDto = getAllPlaylistResponseDto(user);

        return Response.status(Response.Status.OK).entity(responseDto).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response deletePlaylist(@PathParam("id") long id, @QueryParam("token") String token) {
        Customer user = customerService.findByUsername(securityContext.getUserPrincipal().getName());
        Playlist playlist = playlistService.find(id);

        if(isOwnerOfPlaylist(user, playlist)) {
            playlistService.delete(playlist);
        } else {
            Response.status(Response.Status.FORBIDDEN).entity("").build();
        }

        PlaylistResponseDto responseDto = getAllPlaylistResponseDto(user);

        return Response.status(Response.Status.OK).entity(responseDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDto playlistDto) {
        if(playlistDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Customer user = customerService.findByUsername(securityContext.getUserPrincipal().getName());

        Playlist newPlaylist = new Playlist(playlistDto.getId(), playlistDto.getName(), user.getId());
        playlistService.add(newPlaylist);

        PlaylistResponseDto responseDto = getAllPlaylistResponseDto(user);

        return Response.status(Response.Status.CREATED).entity(responseDto).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response editPlaylist(@PathParam("id") long id, @QueryParam("token") String token, PlaylistDto playlistDto) {
        if(playlistDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Customer user = customerService.findByUsername(securityContext.getUserPrincipal().getName());
        Playlist editPlaylist = playlistService.find(playlistDto.getId());

        // Check playlist permission
        if(isOwnerOfPlaylist(user, editPlaylist)) {
            Playlist editedPlaylist = new Playlist(playlistDto.getId(), playlistDto.getName(), user.getId());
            playlistService.edit(editedPlaylist);
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        PlaylistResponseDto responseDto = getAllPlaylistResponseDto(user);

        return Response.status(Response.Status.OK).entity(responseDto).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/tracks")
    public Response getTracksByPlaylist(@PathParam("id") long id, @QueryParam("token") String token) {
        List<Track> tracks = trackService.getByPlaylist(id);

        TrackResponseDto responseDto = convertTracksToResponseDto(tracks);

        return Response.status(Response.Status.OK).entity(responseDto).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/tracks")
    public Response addTrackToPlaylist(@PathParam("id") long id, @QueryParam("token") String token, TrackDto trackDto) {
        if(trackDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Customer user = customerService.findByUsername(securityContext.getUserPrincipal().getName());
        Playlist playlist = playlistService.find(id);
        Track newTrack = trackService.find(trackDto.getId());

        newTrack.setAvailableOffline(trackDto.isOfflineAvailable());

        // Check playlist permission, return FORBIDDEN (403) if not the owner
        if(playlist != null && isOwnerOfPlaylist(user, playlist)) {
            playlistService.addTrackToPlaylist(playlist, newTrack);
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<Track> tracks = trackService.getByPlaylist(id);
        TrackResponseDto responseDto = convertTracksToResponseDto(tracks);

        return Response.status(Response.Status.CREATED).entity(responseDto).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") long playlistId, @PathParam("trackId") long trackId, @QueryParam("token") String token) {
        Customer user = customerService.findByUsername(securityContext.getUserPrincipal().getName());
        Playlist playlist = playlistService.find(playlistId);
        Track track = trackService.find(trackId);

        if(playlist == null || track == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(isOwnerOfPlaylist(user, playlist)) {
            playlistService.removeTrackFromPlaylist(playlist, track);
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<Track> tracks = trackService.getByPlaylist(playlistId);
        TrackResponseDto responseDto = convertTracksToResponseDto(tracks);

        return Response.status(Response.Status.OK).entity(responseDto).build();
    }

    private boolean isOwnerOfPlaylist(Customer user, Playlist playlist) {
        return user.getId() == playlist.getOwner();
    }

    private PlaylistResponseDto getAllPlaylistResponseDto(Customer user) {
        List<Playlist> playlists = playlistService.getAll();
        return convertPlaylistsToResponseDto(user, playlists, trackService);
    }

    private static PlaylistResponseDto convertPlaylistsToResponseDto(Customer user, List<Playlist> playlists, ITrackService trackService) {
        List<PlaylistDto> playlistDtos = new ArrayList<>();
        int playlistLength = 0;
        for (Playlist playlist : playlists) {
            PlaylistDto dto = PlaylistDtoMapper.mapToDto(playlist, user);

            List<Track> tracks = trackService.getByPlaylist(playlist.getId());
            List<TrackDto> trackDtos = new ArrayList<>();
            for(Track track : tracks) {
                playlistLength += track.getLength();
                TrackDto trackDto = TrackDtoMapper.mapToDto(track);
                trackDtos.add(trackDto);
            }
            dto.setTracks(trackDtos);
            playlistDtos.add(dto);
        }

        PlaylistResponseDto responseDto = new PlaylistResponseDto();
        responseDto.setPlaylists(playlistDtos);
        responseDto.setLength(playlistLength);

        return responseDto;
    }

    private static TrackResponseDto convertTracksToResponseDto(List<Track> tracks) {
        List<TrackDto> trackDtos = new ArrayList<>();
        for(Track track : tracks) {
            trackDtos.add(TrackDtoMapper.mapToDto(track));
        }
        TrackResponseDto responseDto = new TrackResponseDto();
        responseDto.setTracks(trackDtos);
        return responseDto;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
}
