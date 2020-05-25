package dea.controllers.dto;

import java.util.List;

public class TrackResponseDto {
    private List<TrackDto> tracks;

    public List<TrackDto> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDto> tracks) {
        this.tracks = tracks;
    }
}
