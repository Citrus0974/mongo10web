package edu.mongo10web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    private String criteries;

    public Category(String name, String description, String criteries) {
        this.name = name;
        this.description = description;
        this.criteries = criteries;
    }
}
