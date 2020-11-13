package dea.services.domain_objects;

public abstract class Track extends DomainObject {
    private String performer;
    private String title;
    private String url;
    private int length;
    private boolean availableOffline;

    public Track(){}

    public Track(final long id, final String performer, final String title, final String url, final int length, final boolean availableOffline) {
        super(id);
        this.performer = performer;
        this.title = title;
        this.url = url;
        this.length = length;
        this.availableOffline = availableOffline;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(final String performer) {
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public boolean isAvailableOffline() {
        return availableOffline;
    }

    public void setAvailableOffline(final boolean availableOffline) {
        this.availableOffline = availableOffline;
    }

    @Override
    public String toString() {
        return "Track [id=" + getId() + ", performer=" + getPerformer() + ", title=" + getTitle() + " url=" + getUrl() + "length=" + getLength() + "availableOffline=" + isAvailableOffline() + "]";
    }
}

