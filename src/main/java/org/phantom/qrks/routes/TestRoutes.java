package org.phantom.qrks.routes;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phantom.qrks.dto.person.CreatePersonRequest;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestRoutes {

    @POST
    @Path("hello")
    public Response getById(@Valid CreatePersonRequest request) {

        return Response.ok().entity(request).build();
    }

}
