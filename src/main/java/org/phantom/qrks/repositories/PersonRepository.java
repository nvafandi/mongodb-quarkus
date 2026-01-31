package org.phantom.qrks.repositories;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.phantom.qrks.entities.Person;

@ApplicationScoped
public class PersonRepository implements PanacheMongoRepository<Person> {

    public Person findByEmail(String email) {
        return find("email", email).firstResult();
    }

}
