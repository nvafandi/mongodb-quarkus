package org.phantom.qrks.services;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.phantom.qrks.dto.person.CreatePersonRequest;

public interface PersonService {

    Uni<Response> createPerson(CreatePersonRequest request);

}
