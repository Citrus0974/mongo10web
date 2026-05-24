package edu.mongo10web.infrastructure;

import com.mongodb.ClientSessionOptions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.RouteMatcher;

import java.util.Map;

public class RoleRoutingMongoDatabaseFactory implements MongoDatabaseFactory {

    private final Map<String, MongoDatabaseFactory> factories;
    private final PersistenceExceptionTranslator exceptionTranslator = new MongoExceptionTranslator();

    public RoleRoutingMongoDatabaseFactory(Map<String, MongoDatabaseFactory> factories) {
        this.factories = factories;
    }

    private MongoDatabaseFactory getVisibleFactory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null ||!auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return factories.get("GUEST");
        }

        String role = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNAUTHORIZED")
                .replace("ROLE_", "");

        return factories.getOrDefault(role, factories.get("GUEST"));
    }

    @Override
    public MongoDatabase getMongoDatabase() throws DataAccessException {
        return getVisibleFactory().getMongoDatabase();
    }

    @Override
    public MongoDatabase getMongoDatabase(String dbName) throws DataAccessException {
        return getVisibleFactory().getMongoDatabase(dbName);
    }

    @Override
    public PersistenceExceptionTranslator getExceptionTranslator() {
        return this.exceptionTranslator;
    }

    @Override
    public ClientSession getSession(ClientSessionOptions options) {
        return getVisibleFactory().getSession(options);
    }

    @Override
    public MongoDatabaseFactory withSession(ClientSession session) {
        return getVisibleFactory().withSession(session);
    }
}
