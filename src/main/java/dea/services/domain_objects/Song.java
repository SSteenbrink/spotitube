package dea.services.domain_objects;

public class Song extends Track {

    private String album;

    public Song(){}

    public Song(long id, String performer, String title, String url, int length, boolean availableOffline, String album) {
        super(id, performer, title, url, length, availableOffline);
        this.album = album;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
