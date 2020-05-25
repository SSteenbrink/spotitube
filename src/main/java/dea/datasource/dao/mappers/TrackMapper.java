package dea.datasource.dao.mappers;

import dea.services.domain_objects.DomainObject;
import dea.services.domain_objects.Song;
import dea.services.domain_objects.Track;
import dea.services.domain_objects.Video;

import javax.ws.rs.NotSupportedException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackMapper extends DataMapper {

    @Override
    protected String findStatement() {
        return "SELECT t.*, NULL as availableOffline FROM tracks t\n" +
                "WHERE id = ?";
    }

    @Override
    protected String insertStatement() {
        throw new NotSupportedException();
    }

    @Override
    protected void doInsert(DomainObject subject, PreparedStatement insertStatement) throws SQLException {
        throw new NotSupportedException();
    }

    @Override
    protected String updateStatement() {
        throw new NotSupportedException();
    }

    @Override
    protected void doUpdate(DomainObject subject, PreparedStatement insertStatement) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String deleteStatement() {
        throw new NotSupportedException();
    }

    @Override
    protected void doDelete(DomainObject subject, PreparedStatement deleteStatement) throws SQLException {
        throw new NotSupportedException();
    }

    @Override
    protected Track doLoad(long id, ResultSet result) throws SQLException {
        String trackType = result.getString("trackType");
        if(trackType.equals("SONG")) {
            return new Song(
                    id,
                    result.getString("title"),
                    result.getString("performer"),
                    result.getString("url"),
                    result.getInt("length"),
                    result.getBoolean("availableOffline"),
                    result.getString("album")
            );
        } else {
            return new Video(
                    id,
                    result.getString("title"),
                    result.getString("performer"),
                    result.getString("url"),
                    result.getInt("length"),
                    result.getBoolean("availableOffline"),
                    result.getString("publicationDate"),
                    result.getString("description")
            );
        }
    }


}
