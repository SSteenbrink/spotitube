package dea.datasource.dao;


import com.mongodb.client.MongoCollection;
import dea.datasource.dao.interfaces.ICustomerDao;
import javax.enterprise.inject.Alternative;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotSupportedException;

import dea.services.domain_objects.Customer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

@Alternative
public class MongoCustomerDao extends MongoDao implements ICustomerDao {

    MongoCollection<Customer> customers;

    public MongoCustomerDao() {
        super();
        try {
            customers = getDatabase().getCollection(getCollectionName(), Customer.class);
        } catch(Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    protected String getCollectionName() {
        return "customers";
    }

    @Override
    public Customer findByUsername(String username) {
        try {
            return customers.find(eq("username", username)).first();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getCause());
        }
    }

    @Override
    public void update(Customer customer) {
        throw new NotSupportedException();
    }
}
