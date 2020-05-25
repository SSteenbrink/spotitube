package dea.controllers;

import dea.services.domain_objects.Customer;
import dea.controllers.dto.LoginDto;
import dea.controllers.dto.LoginResponseDto;
import dea.services.interfaces.ICustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

public class CustomerControllerTest {

    private CustomerController sut;
    private ICustomerService mockedCustomerService;

    private String mockUsername = "mockusername";
    private String mockPassword = "mockPwd";
    private String mockToken = "TOKEN-MOCK-123";
    private String mockFirstname = "Mock";
    private String mockLastname = "ito";
    private Customer mockUser = new Customer(1L, mockUsername, mockPassword, mockFirstname, mockLastname);

    @Before
    public void setUp() {
        sut = new CustomerController();
        this.mockedCustomerService = Mockito.mock(ICustomerService.class);

        this.sut.setCustomerService(mockedCustomerService);
    }

    @Test
    public void loginReturnsCorrectTokenAndName() {
        // Arrange
        Mockito.when(mockedCustomerService.loginCustomer(mockUser)).thenReturn(mockToken);
        Mockito.when(mockedCustomerService.findByUsername(mockUsername)).thenReturn(mockUser);

        LoginDto loginDto = new LoginDto();
        loginDto.setUser(mockUsername);
        loginDto.setPassword(mockPassword);

        LoginResponseDto expected = new LoginResponseDto();
        expected.setUser(mockFirstname + " " + mockLastname);
        expected.setToken(mockToken);

        // Act
        LoginResponseDto actual = (LoginResponseDto) sut.login(loginDto).getEntity();

        // Assert
        Mockito.verify(mockedCustomerService).loginCustomer(mockUser);
        Mockito.verify(mockedCustomerService).findByUsername(mockUsername);
        Assert.assertEquals(actual.getUser(), expected.getUser());
        Assert.assertEquals(actual.getToken(), expected.getToken());
    }

    @Test
    public void loginWrongPasswordReturnsUnauthorized() {
        // Arrange
        Mockito.when(mockedCustomerService.loginCustomer(mockUser)).thenReturn(mockToken);
        Mockito.when(mockedCustomerService.findByUsername(mockUsername)).thenReturn(mockUser);

        String wrongPassword = "wrongpass";

        LoginDto loginDto = new LoginDto();
        loginDto.setUser(mockUsername);
        loginDto.setPassword(wrongPassword);

        Response.StatusType expected = Response.Status.UNAUTHORIZED;

        // Act
        Response.StatusType actual = sut.login(loginDto).getStatusInfo();

        // Assert
        Assert.assertEquals(expected, actual);
    }
}