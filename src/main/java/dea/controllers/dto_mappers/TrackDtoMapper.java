package dea.controllers.dto_mappers;

import dea.services.domain_objects.Song;
import dea.services.domain_objects.Track;
import dea.services.domain_objects.Video;
import dea.controllers.dto.TrackDto;

public class TrackDtoMapper {
    private TrackDtoMapper() {}

    public static TrackDto mapToDto(Track track) {
        if(track instanceof Video) {
            return mapVideoToDto((Video) track);
        } else {
            return mapSongToDto((Song) track);
        }
    }

    private static TrackDto mapSongToDto(Song track) {
        TrackDto dto = new TrackDto();
        dto.setId(track.getId());
        dto.setDuration(track.getLength());
        dto.setTitle(track.getTitle());
        dto.setPerformer(track.getPerformer());
        dto.setAlbum(track.getAlbum());
        dto.setOfflineAvailable(track.isAvailableOffline());
        return dto;
    }

    private static TrackDto mapVideoToDto(Video track) {
        TrackDto dto = new TrackDto();
        dto.setId(track.getId());
        dto.setDuration(track.getLength());
        dto.setTitle(track.getTitle());
        dto.setPerformer(track.getPerformer());
        dto.setDescription(track.getDescription());
        dto.setPublicationDate(track.getPublicationDate());
        dto.setOfflineAvailable(track.isAvailableOffline());
        return dto;
    }
}
