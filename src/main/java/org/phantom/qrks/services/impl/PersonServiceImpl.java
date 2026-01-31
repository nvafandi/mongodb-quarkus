package org.phantom.qrks.services.impl;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.phantom.qrks.dto.person.BaseResponse;
import org.phantom.qrks.dto.person.CreatePersonRequest;
import org.phantom.qrks.entities.Person;
import org.phantom.qrks.repositories.PersonRepository;
import org.phantom.qrks.services.PersonService;

import java.time.LocalDateTime;

@ApplicationScoped
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Inject
    PersonRepository personRepository;

    @Override
    public Uni<Response> createPerson(CreatePersonRequest request) {
        log.info("createPerson: with email: {}", request.getEmail());
        return Uni.createFrom().item(() -> personRepository.findByEmail(request.getEmail()))
                .onItem().transformToUni(existingPerson -> {
                    log.info("existingPerson: {}",existingPerson);
                    if (existingPerson != null) {
                        log.warn("User with email {} already exists.", request.getEmail());
                        BaseResponse<Object> conflictResponse = BaseResponse.builder()
                                .status(Response.Status.CONFLICT.getStatusCode())
                                .message("User with this email already exists.")
                                .build();

                        return Uni.createFrom().item(Response.status(Response.Status.CONFLICT)
                                .entity(conflictResponse)
                                .build());
                    }

                    LocalDateTime now = LocalDateTime.now();
                    Person person = new Person();
                    person.setName(request.getName());
                    person.setEmail(request.getEmail());
                    person.setCreatedAt(now);
                    person.setUpdatedAt(now);

                    personRepository.persist(person);

                    BaseResponse<Person> successResponse = BaseResponse.<Person>builder()
                            .status(Response.Status.CREATED.getStatusCode())
                            .message("Person created successfully")
                            .data(person)
                            .build();

                    return Uni.createFrom().item(Response.ok().entity(successResponse).build());
                })
                .onFailure().recoverWithItem(failure -> {
                    log.error("Error finding person by email: {}", failure.getMessage(), failure);
                    BaseResponse<Object> errorResponse = BaseResponse.builder()
                            .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message("Internal server error during person creation.")
                            .build();
                    return Response.serverError()
                            .entity(errorResponse)
                            .build();
                });
    }

}
