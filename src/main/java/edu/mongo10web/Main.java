package edu.mongo10web;


import edu.mongo10web.entity.*;
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

//    @Bean
    ApplicationRunner runner1(CategoryRepository repository, ProductRepository productRepository, SupplyRepository supplyRepository){
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
            productRepository.save(new Product("newmilk", "kto", 40, category));
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

//    @Bean //Init accounts
    public ApplicationRunner accounts(edu.mongo10web.repository.UserRepository userRepository,
                                             org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        return args -> {

            userRepository.deleteAll();

            userRepository.save(new User(null, "supplier", passwordEncoder.encode("password"), "SUPPLIER"));
            userRepository.save(new User(null, "storekeeper", passwordEncoder.encode("password"), "STOREKEEPER"));
            userRepository.save(new User(null, "manager", passwordEncoder.encode("password"), "MANAGER"));

            System.out.println("Логины: supplier / storekeeper / manager. Пароль у всех: password");
        };
    }

    //@Bean
    ApplicationRunner runner2(CategoryRepository categoryRepository, ProductRepository productRepository, SupplyRepository supplyRepository){
        return args -> {
            //категории
            Category grocery = new Category("grocery", "Бакалея и крупы", "Сухой склад");
            categoryRepository.save(grocery);
            Category confectionery = new Category("confectionery", "Кондитерские изделия", "Обычные условия");
            categoryRepository.save(confectionery);
            Category meat = new Category("meat", "Мясной отдел", "Охлажденные, до +4C");
            categoryRepository.save(meat);

            //товары
            Product rice = new Product("Рис Басмати 1кг", "Мистраль", 180, grocery);
            Product pasta = new Product("Макароны Перья 500г", "Макфа", 85, grocery);

            Product chocolate = new Product("Шоколад Горький", "Бабаевский", 120, confectionery);
            Product cookies = new Product("Печенье Овсяное", "Посиделкино", 110, confectionery);
            Product cake = new Product("Торт Прага", "Бабаевский", 650, confectionery);

            Product beef = new Product("Фарш говяжий 400г", "Мираторг", 290, meat);
            Product chicken = new Product("Филе цыпленка 1кг", "Петелинка", 380, meat);

            productRepository.save(rice);
            productRepository.save(pasta);
            productRepository.save(chocolate);
            productRepository.save(cookies);
            productRepository.save(cake);
            productRepository.save(beef);
            productRepository.save(chicken);

            // поставки
            LocalDateTime now = LocalDateTime.now();

            supplyRepository.save(new Supply(rice, 150, SupplyStatus.PLACED, now, null));
            supplyRepository.save(new Supply(pasta, 80, SupplyStatus.CREATED, now, null));

            supplyRepository.save(new Supply(chocolate, 200, SupplyStatus.PLACED, now, null));
            supplyRepository.save(new Supply(cookies, 120, SupplyStatus.RESERVED, now, null));
            supplyRepository.save(new Supply(cake, 15, SupplyStatus.DISPOSED, now, null));
            supplyRepository.save(new Supply(cake, 5, SupplyStatus.GONE, now, null));

            supplyRepository.save(new Supply(beef, 90, SupplyStatus.PLACED, now, null));
            supplyRepository.save(new Supply(chicken, 110, SupplyStatus.CREATED, now, null));
            supplyRepository.save(new Supply(chicken, 40, SupplyStatus.RESERVED, now, null));
        };
    }

}