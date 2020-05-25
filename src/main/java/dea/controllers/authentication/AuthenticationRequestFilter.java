package dea.controllers.authentication;

import dea.services.domain_objects.Customer;
import dea.services.interfaces.ICustomerService;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.security.Principal;

/*
inspiration: stackoverflow user: cassiomolin
url: https://stackoverflow.com/a/26778123/10391156
 */

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationRequestFilter implements ContainerRequestFilter {

    @Inject
    private ICustomerService customerService;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        MultivaluedMap<String, String> queryParameters = requestContext.getUriInfo().getQueryParameters();

        // Get the Authorization header from the request
        String token = "";
        if(queryParameters.containsKey("token")) {
            token = queryParameters.get("token").toString();
            token = token.replace("[", "");
            token = token.replace("]", "");
        } else {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
        }
        try {
            Customer customer = customerService.getAuthenticatedUser(token);

            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    return customer::getUsername;
                }

                @Override
                public boolean isUserInRole(String s) {
                    return true;
                }

                @Override
                public boolean isSecure() {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return BASIC_AUTH;
                }
            });
        } catch (NotAuthorizedException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .build());
        }
    }
}
