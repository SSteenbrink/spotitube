package dea.datasource.dao;

import dea.StubUtility;
import dea.datasource.dao.mappers.IDataMapper;
import dea.services.domain_objects.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.List;

public class CustomerDaoTest {

    CustomerDao sut;
    Connection connectionMock;
    IDataMapper customerMapperMock;

    @Before
    public void setUp() {
        sut = new CustomerDao();
        connectionMock = Mockito.mock(Connection.class);
        customerMapperMock = Mockito.mock(IDataMapper.class);
        sut.setCustomerMapper(customerMapperMock);
        sut.setConnection(connectionMock);
    }

    @Test
    public void findByUsernameReturnsFirstResult() {
        // Arrange
        List<Customer> customers = StubUtility.getCustomerStubs();
        Mockito.doReturn(customers).when(customerMapperMock).findMany(Mockito.any(), Mockito.any());
        Customer expected = customers.get(0);

        // Act
        Customer actual = sut.findByUsername("test");

        // Assert
        Mockito.verify(customerMapperMock).findMany(Mockito.any(), Mockito.any());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateWorks() {
        // Arrange
        Customer customer = StubUtility.getCustomerStubs().get(0);

        // Act
        sut.update(customer);

        // Assert
        Mockito.verify(customerMapperMock).update(customer, connectionMock);
    }
}