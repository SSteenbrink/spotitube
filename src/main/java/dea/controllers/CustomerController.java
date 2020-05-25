package dea.controllers;

import dea.services.domain_objects.Customer;
import dea.controllers.dto.LoginResponseDto;
import dea.controllers.dto.LoginDto;
import dea.services.interfaces.ICustomerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class CustomerController {

    private ICustomerService customerService;

    @Inject
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDto loginDto) {
        if(loginDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Customer customer = customerService.findByUsername(loginDto.getUser());

        String token;
        if (authenticate(loginDto, customer)) {
            token = customerService.loginCustomer(customer);
            LoginResponseDto responseDto = new LoginResponseDto();
            responseDto.setUser(customer.getFirstname() + " " + customer.getLastname());
            responseDto.setToken(token);
            return Response.status(Response.Status.OK).entity(responseDto).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("Incorrect username / password!").build();
    }

    private boolean authenticate(LoginDto loginDto, Customer customer) {
        return customer.getPassword() != null && loginDto.getPassword().equals(customer.getPassword());
    }
}
