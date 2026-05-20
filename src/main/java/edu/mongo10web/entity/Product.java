package edu.mongo10web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    private String id;
    private String name;
    String manufacturer;
    private Integer cost;
    @DocumentReference
    private Category category;

    public Product(String name, String manufacturer, Integer cost, Category category) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.cost = cost;
        this.category = category;
    }
}
