package org.phantom.qrks.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@MongoEntity(collection = "persons")
@Data
public class Person extends PanacheMongoEntity {

    @NotBlank(message = "Name is required")
    public String name;

    @NotBlank(message = "Email is required")
    public String email;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;

}
