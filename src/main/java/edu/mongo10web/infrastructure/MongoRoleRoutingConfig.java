package edu.mongo10web.infrastructure;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MongoRoleRoutingConfig {
    @Value("${spring.mongodb.host:localhost:localhost}")
    private String host;

    @Value("${spring.mongodb.port:27017}")
    private int port;

    @Value("${spring.mongodb.database:new_sstu_database}")
    private String database;

    @Value("${spring.mongodb.authentication-database}")
    private String authDb;

    @Value("${spring.mongodb.custom.supplier-login}")
    private String supplierLogin;

    @Value("${spring.mongodb.custom.supplier-password}")
    private String supplierPassword;

    @Value("${spring.mongodb.custom.storekeeper-login}")
    private String storekeeperLogin;

    @Value("${spring.mongodb.custom.storekeeper-password}")
    private String storekeeperPassword;

    @Value("${spring.mongodb.custom.manager-login}")
    private String managerLogin;

    @Value("${spring.mongodb.custom.manager-password}")
    private String managerPassword;


    @Bean
    @Primary
    public MongoDatabaseFactory mongoDatabaseFactory() {
        Map<RoleRoutingContext.Role, MongoDatabaseFactory> factories = new HashMap<>();
        String guestUri = String.format("mongodb://%s:%d/%s", host, port, database);
        factories.put(RoleRoutingContext.Role.UNAUTHORIZED, new SimpleMongoClientDatabaseFactory(guestUri));
        factories.put(RoleRoutingContext.Role.SUPPLIER, new SimpleMongoClientDatabaseFactory(
                MongoClients.create(String.format("mongodb://%s:%s@%s:%d/%s?authSource=admin", supplierLogin, supplierPassword, host, port, database)), database));
        factories.put(RoleRoutingContext.Role.STOREKEEPER, new SimpleMongoClientDatabaseFactory(
                MongoClients.create(String.format("mongodb://%s:%s@%s:%d/%s?authSource=admin", storekeeperLogin, storekeeperPassword, host, port, database)), database));
        factories.put(RoleRoutingContext.Role.MANAGER, new SimpleMongoClientDatabaseFactory(
                MongoClients.create(String.format("mongodb://%s:%s@%s:%d/%s?authSource=admin", managerLogin, managerPassword, host, port, database)), database));

        return new RoleRoutingMongoDatabaseFactory(factories);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory factory){
        return new MongoTemplate(mongoDatabaseFactory());
    }

}
