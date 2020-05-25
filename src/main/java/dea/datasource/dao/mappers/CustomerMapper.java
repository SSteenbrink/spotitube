package dea.datasource.dao.mappers;

import dea.services.domain_objects.Customer;
import dea.services.domain_objects.DomainObject;

import javax.ws.rs.NotSupportedException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper extends DataMapper {

    @Override
    protected String findStatement() {
        throw new NotSupportedException();
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
        return "UPDATE customer \n" +
                "SET username = ?, firstname = ?, lastName = ?, password = ?\n" +
                "WHERE id = ?";
    }

    @Override
    protected void doUpdate(DomainObject subject, PreparedStatement updateStatement) throws SQLException {
        Customer customer = (Customer) subject;
        updateStatement.setString(1, customer.getUsername());
        updateStatement.setString(2, customer.getFirstname());
        updateStatement.setString(3, customer.getLastname());
        updateStatement.setString(4, customer.getPassword());
        updateStatement.setLong(5, customer.getId());
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
    protected Customer doLoad(long id, ResultSet rs) throws SQLException {
        return new Customer(id,
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastname"));
    }
}
