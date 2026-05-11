package edu.mongo10web;


import edu.mongo10web.entity.Category;
import edu.mongo10web.entity.Product;
import edu.mongo10web.entity.Supply;
import edu.mongo10web.entity.SupplyStatus;
import edu.mongo10web.repository.CategoryRepository;
import edu.mongo10web.repository.ProductRepository;
import edu.mongo10web.repository.SupplyRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
    }

    @Bean
    ApplicationRunner runner(CategoryRepository repository, ProductRepository productRepository, SupplyRepository supplyRepository){
        return args -> {
            repository.deleteAll();
            productRepository.deleteAll();
            supplyRepository.deleteAll();
            Category category = new Category("milk", "milk products", "скоропортящиеся продукты");
            repository.save(category);
            repository.save(new Category("fruit", "fruits and vegetables", "разные фрукты овощи"));
            System.out.println("findAll <- Category");
            repository.findAll().forEach(System.out::println);
            System.out.println();

            Product product = new Product("default milk", "local", 150, category);
            productRepository.save(product);
            System.out.println("findAll <- Product");
            productRepository.findAll().forEach(System.out::println);
            System.out.println();

            Supply supply = new Supply(product, 50, SupplyStatus.CREATED, null, null);
            supplyRepository.save(supply);
            supplyRepository.save(new Supply(product, 45, SupplyStatus.PLACED, LocalDateTime.now(), null));
            System.out.println("findAll <- Supply");
            supplyRepository.findAll().forEach(System.out::println);
            System.out.println();
        };
    }
}