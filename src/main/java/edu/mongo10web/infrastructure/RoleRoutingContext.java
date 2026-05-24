package edu.mongo10web.infrastructure;

public class RoleRoutingContext {
    public enum Role{
        UNAUTHORIZED,
        SUPPLIER,
        STOREKEEPER,
        MANAGER
    }

    private final static ThreadLocal<Role> currentRole = ThreadLocal.withInitial(() -> Role.UNAUTHORIZED);

    public static void setRole(Role role) {
        currentRole.set(role);
    }

    public static Role getRole() {
        return currentRole.get();
    }

    public static void clear() {
        currentRole.remove();
    }
}
