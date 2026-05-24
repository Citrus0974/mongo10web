package edu.mongo10web.infrastructure;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//@Component
public class RoleRoutingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            RoleRoutingContext.Role role = (RoleRoutingContext.Role) session.getAttribute("user_role");
            if (role!= null) {
//                RoleRoutingContext.setRole(role);
                return true;
            }
        }
//        RoleRoutingContext.setRole(RoleRoutingContext.Role.UNAUTHORIZED);
        return true;
    }
}
