package dea.datasource.dao;

import dea.datasource.dao.interfaces.ICustomerDao;
import dea.datasource.dao.mappers.IDataMapper;
import dea.services.domain_objects.Customer;
import dea.datasource.dao.mappers.CustomerMapper;

import java.util.List;

public class CustomerDao extends Dao implements ICustomerDao {
    private IDataMapper customerMapper = new CustomerMapper();

    // Query should return only one result as the username is unique
    public Customer findByUsername(String username) {
        List<Customer> results = (List<Customer>)customerMapper.findMany(new FindCustomerByUsername(username), conn);
        return results.get(0);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.update(customer, conn);
    }

    static class FindCustomerByUsername implements StatementSource {
        String username;

        FindCustomerByUsername(String username) {
            this.username = username;
        }

        public String sql() {
            return "SELECT * FROM customer \n" +
                    "WHERE username = ?";
        }

        public Object[] parameters() {
            Object[] parameters = new Object[1];
            parameters[0] = username;
            return parameters;
        }
    }

    protected void setCustomerMapper(IDataMapper customerMapper) {
        this.customerMapper = customerMapper;
    }
}
