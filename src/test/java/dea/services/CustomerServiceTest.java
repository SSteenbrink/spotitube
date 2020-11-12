package dea.services;

import dea.StubUtility;
import dea.datasource.dao.interfaces.ICustomerDao;
import dea.services.domain_objects.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.UUID;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CustomerService.class})
public class CustomerServiceTest {
    ICustomerDao mockCustomerDao;

    CustomerService sut;

    @Before
    public void setUp() {
        sut = new CustomerService();

        mockCustomerDao = Mockito.mock(ICustomerDao.class);
        sut.setCustomerDao(mockCustomerDao);
    }

    @Test
    public void findByUsername() {
        Customer expected = StubUtility.getCustomerStubs().get(0);

        Mockito.when(mockCustomerDao.findByUsername(expected.getUsername())).thenReturn(expected);
        Customer actual = sut.findByUsername(expected.getUsername());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void loginCustomerTest() {
        Customer customer = StubUtility.getCustomerStubs().get(0);

        final String mockToken = "493410b3-dd0b-4b78-97bf-289f50f6e74f";
        UUID mockUUID = UUID.fromString(mockToken);
        PowerMockito.mockStatic(UUID.class);

        Mockito.when(UUID.randomUUID()).thenReturn(mockUUID);
        String token = sut.loginCustomer(customer);

        Assert.assertEquals(token, mockToken);
        Assert.assertNotNull(token);

        Customer loggedInCustomer = sut.getAuthenticatedUser(token);

        Assert.assertEquals(loggedInCustomer, customer);
    }
}
