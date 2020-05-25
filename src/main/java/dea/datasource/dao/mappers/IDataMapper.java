package dea.datasource.dao.mappers;

import dea.datasource.dao.StatementSource;
import dea.services.domain_objects.DomainObject;

import java.sql.Connection;
import java.util.List;

public interface IDataMapper {
    DomainObject find(long id, Connection conn);
    List<? extends DomainObject> findMany(StatementSource source, Connection conn);
    void insert(DomainObject domainObject, Connection conn);
    void update(DomainObject domainObject, Connection conn);
    void delete(DomainObject domainObject, Connection conn);
}
