package dea.datasource.dao.mappers;

import dea.services.domain_objects.DomainObject;
import dea.datasource.dao.StatementSource;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class DataMapper implements IDataMapper{
    // Identity Map
    private Map<Long, DomainObject> loadedMap = new HashMap<>();

    // Find
    protected abstract String findStatement();

    public DomainObject find(long id, Connection conn) throws InternalServerErrorException {
        DomainObject result = loadedMap.get(id);
        if (result != null) return result;

        try(PreparedStatement findStatement = conn.prepareStatement(findStatement())) {
            findStatement.setLong(1, id);
            try (ResultSet rs = findStatement.executeQuery()) {
                if(!rs.next()) {
                    throw new NotFoundException();
                }
                result = load(rs);
            }

            return result;
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    public List<? extends DomainObject> findMany(StatementSource source, Connection conn) throws InternalServerErrorException {
        ResultSet rs;
        try(PreparedStatement stmt = conn.prepareStatement(source.sql())) {
            for (int i = 0; i < source.parameters().length; i++) {
                stmt.setObject(i+1, source.parameters()[i]);
            }
            rs = stmt.executeQuery();
            return loadAll(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException();
        }
    }

    // Insert
    protected abstract String insertStatement();

    protected abstract void doInsert(DomainObject subject, PreparedStatement insertStatement) throws SQLException;

    public void insert(DomainObject domainObject, Connection conn) throws InternalServerErrorException {
        try(PreparedStatement insertStatement = conn.prepareStatement(insertStatement())) {
            doInsert(domainObject, insertStatement);
            insertStatement.execute();
            loadedMap.put(domainObject.getId(), domainObject);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    // Update
    protected abstract String updateStatement();

    protected abstract void doUpdate(DomainObject subject, PreparedStatement updateStatement) throws SQLException;

    public void update(DomainObject domainObject, Connection conn) {
        try(PreparedStatement updateStatement = conn.prepareStatement(updateStatement())) {
            doUpdate(domainObject, updateStatement);
            updateStatement.execute();
            loadedMap.put(domainObject.getId(), domainObject);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    // Delete
    protected abstract String deleteStatement();

    protected abstract void doDelete(DomainObject subject, PreparedStatement deleteStatement) throws SQLException;

    public void delete(DomainObject domainObject, Connection conn) throws InternalServerErrorException {
        try(PreparedStatement deleteStatement = conn.prepareStatement(deleteStatement())) {
            doDelete(domainObject, deleteStatement);
            deleteStatement.execute();
            loadedMap.remove(domainObject.getId());
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    // Converting ResultSets to DomainObjects
    protected abstract DomainObject doLoad(long id, ResultSet rs) throws SQLException;

    private DomainObject load(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        if (loadedMap.containsKey(id)) return loadedMap.get(id);
        DomainObject result = doLoad(id, rs);
        loadedMap.put(id, result);
        return result;
    }

    private List<DomainObject> loadAll(ResultSet rs) throws SQLException {
        List<DomainObject> result = new ArrayList<>();
        while (rs.next()) {
            result.add(load(rs));
        }
        return result;
    }



}
