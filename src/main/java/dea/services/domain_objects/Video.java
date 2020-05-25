package dea.services.domain_objects;

public class Video extends Track {

    private String publicationDate;
    private String description;

    public Video(long id, String performer, String title, String url, int length, boolean availableOffline, String publicationDate, String description) {
        super(id, performer, title, url, length, availableOffline);
        this.publicationDate = publicationDate;
        this.description = description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
