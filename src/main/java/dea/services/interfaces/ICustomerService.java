package dea.services.interfaces;

import dea.services.domain_objects.Customer;

import javax.ws.rs.NotAuthorizedException;

public interface ICustomerService {
    Customer findByUsername(String username);
    String loginCustomer(Customer customer);
    Customer getAuthenticatedUser(String token) throws NotAuthorizedException;
}
