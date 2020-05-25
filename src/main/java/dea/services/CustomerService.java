package dea.services;

import dea.datasource.dao.interfaces.ICustomerDao;
import dea.services.domain_objects.Customer;
import dea.services.interfaces.ICustomerService;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomerService implements ICustomerService {

    private ICustomerDao customerDao;

    @Inject
    public void setCustomerDao(ICustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    private static Map<String, Customer> authenticatedUsers = new HashMap<>();

    @Override
    public Customer findByUsername(String username) {
        return customerDao.findByUsername(username);
    }

    @Override
    public String loginCustomer(final Customer customer) {
        String token = UUID.randomUUID().toString();
        authenticatedUsers.put(token, customer);
        return token;
    }

    public Customer getAuthenticatedUser(String token) {
        Customer authenticatedCustomer = authenticatedUsers.get(token);
        if(authenticatedCustomer == null) {
            throw new NotAuthorizedException("User is not authenticated");
        }
        return authenticatedCustomer;
    }
}
