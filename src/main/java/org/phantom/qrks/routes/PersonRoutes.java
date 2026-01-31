package org.phantom.qrks.routes;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phantom.qrks.dto.person.CreatePersonRequest;
import org.phantom.qrks.services.PersonService;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonRoutes {

    @Inject
    PersonService personService;

    @POST
    @Path("/create")
    public Uni<Response> createPerson(@Valid CreatePersonRequest request) {
        return personService.createPerson(request);
    }
}
