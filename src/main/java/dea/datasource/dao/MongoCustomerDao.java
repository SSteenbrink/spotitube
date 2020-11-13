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
            customers = getDatabase().getCollection("customers", Customer.class);
        } catch(Exception e) {
            throw new InternalServerErrorException();
        }
    }

    @Override
    public Customer findByUsername(String username) {
        System.out.println(customers.toString());
        try {
            return customers.find(eq("username", username)).projection(excludeId()).first();
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void update(Customer customer) {
        throw new NotSupportedException();
    }
}
