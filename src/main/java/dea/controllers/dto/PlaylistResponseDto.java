package dea.controllers.dto;

import java.io.Serializable;
import java.util.List;

public class PlaylistResponseDto implements Serializable {
    private List<PlaylistDto> playlists;
    private int length;

    public List<PlaylistDto> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistDto> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
