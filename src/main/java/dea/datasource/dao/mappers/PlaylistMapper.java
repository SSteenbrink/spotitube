package dea.datasource.dao.mappers;

import dea.services.domain_objects.DomainObject;
import dea.services.domain_objects.Playlist;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistMapper extends DataMapper {

    @Override
    protected String findStatement()
    {
        return "SELECT * FROM playlist WHERE id = ?";
    }

    @Override
    protected String insertStatement() {
        return "INSERT INTO playlist (name, owner)\n" +
                "VALUES (?, ?)";
    }

    @Override
    protected void doInsert(DomainObject subject, PreparedStatement insertStatement) throws SQLException {
        Playlist playlist = (Playlist) subject;
        insertStatement.setString(1, playlist.getName());
        insertStatement.setLong(2, playlist.getOwner());
    }

    @Override
    protected String updateStatement() {
        return "UPDATE playlist p\n" +
                "SET p.name = ?, p.owner = ?\n" +
                "WHERE p.id = ?";
    }

    @Override
    protected void doUpdate(DomainObject subject, PreparedStatement updateStatement) throws SQLException {
        Playlist playlist = (Playlist) subject;
        updateStatement.setString(1, playlist.getName());
        updateStatement.setLong(2, playlist.getOwner());
        updateStatement.setLong(3, playlist.getId());
    }

    @Override
    protected String deleteStatement() {
        return "DELETE FROM playlist WHERE id = ?;";
    }

    @Override
    protected void doDelete(DomainObject subject, PreparedStatement deleteStatement) throws SQLException {
        Playlist playlist = (Playlist) subject;
        deleteStatement.setLong(1, playlist.getId());
    }

    @Override
    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        return new Playlist(
                id,
                rs.getString("name"),
                rs.getLong("owner"));
    }
}
