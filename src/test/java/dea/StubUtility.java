package dea;

import dea.services.domain_objects.*;
import dea.controllers.dto.PlaylistDto;
import dea.controllers.dto.TrackDto;

import java.util.ArrayList;
import java.util.List;

public class StubUtility {

    public static List<Track> getTrackStubs() {
        List<Track> mockTracks = new ArrayList<>();
        Track track1 = new Song(1L, "Performer 1", "Title 1", "", 300, false, "Album 1");
        Track track2 = new Video(2L, "Performer 2", "Title 2", "", 400, true, "", "");
        Track track3 = new Video(3L, "Performer 3", "Title 3", "", 200, false, "", "");
        Track track4 = new Video(4L, "Performer 4", "Title 3", "", 200, false, "", "");
        mockTracks.add(track1);
        mockTracks.add(track2);
        mockTracks.add(track3);
        mockTracks.add(track4);
        return mockTracks;
    }

    public static List<TrackDto> getTrackDtoStubs() {
        List<TrackDto> stubTrackDtos = new ArrayList<>();

        TrackDto trackDto1 = new TrackDto();
        trackDto1.setId(1L);
        trackDto1.setTitle("Track 1");
        trackDto1.setOfflineAvailable(true);
        trackDto1.setDuration(200);

        TrackDto trackDto2 = new TrackDto();
        trackDto2.setId(2L);
        trackDto2.setTitle("Track 1");
        trackDto2.setOfflineAvailable(true);
        trackDto2.setDuration(200);

        TrackDto trackDto3 = new TrackDto();
        trackDto3.setId(3L);
        trackDto3.setTitle("Track 3");
        trackDto3.setOfflineAvailable(true);
        trackDto3.setDuration(200);

        TrackDto trackDto4 = new TrackDto();
        trackDto4.setId(4L);
        trackDto4.setTitle("Track 4");
        trackDto4.setOfflineAvailable(false);
        trackDto4.setDuration(200);

        stubTrackDtos.add(trackDto1);
        stubTrackDtos.add(trackDto2);
        stubTrackDtos.add(trackDto3);
        stubTrackDtos.add(trackDto4);
        return stubTrackDtos;
    }

    public static List<Playlist> getPlaylistStubs() {
        List<Playlist> stubPlaylists = new ArrayList<>();
        Playlist playlist1 = new Playlist(1L, "Playlist 1", getCustomerStubs().get(0).getId());
        Playlist playlist2 = new Playlist(2L, "Playlist 2", getCustomerStubs().get(2).getId());
        Playlist playlist3 = new Playlist(3L, "Playlist 3", getCustomerStubs().get(0).getId());
        stubPlaylists.add(playlist1);
        stubPlaylists.add(playlist2);
        stubPlaylists.add(playlist3);
        return stubPlaylists;
    }

    public static List<PlaylistDto> getPlaylistDtoStubs() {
        List<PlaylistDto> stubPlaylistsDtos = new ArrayList<>();
        PlaylistDto playlist1 = new PlaylistDto();
        playlist1.setId(1L);
        playlist1.setName("Coole muziek");
        playlist1.setOwner(false);
        playlist1.setTracks(getTrackDtoStubs());

        PlaylistDto playlist2 = new PlaylistDto();
        playlist2.setId(2L);
        playlist2.setName("Coolere muziek");
        playlist2.setOwner(false);
        playlist2.setTracks(getTrackDtoStubs());

        PlaylistDto playlist3 = new PlaylistDto();
        playlist3.setId(3L);
        playlist3.setName("Coolste muziek");
        playlist3.setOwner(true);
        playlist3.setTracks(getTrackDtoStubs());

        stubPlaylistsDtos.add(playlist1);
        stubPlaylistsDtos.add(playlist2);
        stubPlaylistsDtos.add(playlist3);
        return stubPlaylistsDtos;
    }

    public static List<Customer> getCustomerStubs() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer(1L , "Customer 1", "pass1", "Name1", "Last1");
        Customer customer2 = new Customer(2L , "Customer 2", "pass2", "Name2", "Last2");
        Customer customer3 = new Customer(3L , "Customer 3", "pass3", "Name3", "Last3");
        Customer customer4 = new Customer(4L , "Customer 4", "pass4", "Name4", "Last4");
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        return customers;
    }
}
