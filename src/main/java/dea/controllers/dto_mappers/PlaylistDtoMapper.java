package dea.controllers.dto_mappers;

import dea.services.domain_objects.Customer;
import dea.services.domain_objects.Playlist;
import dea.controllers.dto.PlaylistDto;

public class PlaylistDtoMapper {

    private PlaylistDtoMapper(){}

    public static PlaylistDto mapToDto(Playlist playlist, Customer user) {
        boolean isOwner = false;
        if(user != null) {
            isOwner = user.getId() == playlist.getOwner();
        }
        PlaylistDto dto = new PlaylistDto();
        dto.setId(playlist.getId());
        dto.setName(playlist.getName());
        dto.setOwner(isOwner);
        return dto;
    }
}
