package dea.services.domain_objects;

public class DomainObject {
    private long id;
    public long getId() {
        return id;
    }

    public void setId(long id) throws AssertionError {
        if(id == 0) {
            throw new IllegalStateException("Id cannot be 0!");
        }
        this.id = id;
    }

    public DomainObject() {}

    public DomainObject(long id) {
        if(id == 0) {
            throw new IllegalStateException("Id cannot be 0!");
        }
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(getId());
    }

     /*
        snippet from https://www.sourcecodeexamples.net/2018/04/data-mapper.html
    */
    @Override
    public boolean equals(final Object inputObject) {

        boolean isEqual = false;

        /* Check if both objects are same */
        if (this == inputObject) {
            isEqual = true;
        } else if (inputObject != null && getClass() == inputObject.getClass()) {

            final DomainObject inputDomainObject = (DomainObject) inputObject;

            /* If track id matched */
            if (this.getId() == inputDomainObject.getId()) {
                isEqual = true;
            }
        }
        return isEqual;
    }
}
