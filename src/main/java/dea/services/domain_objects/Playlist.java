package dea.services.domain_objects;

public class Playlist extends DomainObject {

    private String name;
    private long owner;
    private long[] tracks;

    public Playlist() {}

    public Playlist(final long id, final String name, final long owner) {
        super(id);
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(final long owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Playlist [id=" + getId() + ", nam=" + getName() + ", owner=" + getOwner() + "]";
    }
}
