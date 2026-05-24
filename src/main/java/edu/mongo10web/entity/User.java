package edu.mongo10web.entity;

import edu.mongo10web.infrastructure.RoleRoutingContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;
}
