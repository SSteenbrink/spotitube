package dea.datasource.dao.interfaces;

import dea.services.domain_objects.Customer;

public interface ICustomerDao {
    Customer findByUsername(String username);
    void update(Customer customer);
}
