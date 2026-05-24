package edu.mongo10web.controller;

import edu.mongo10web.entity.Supply;
import edu.mongo10web.infrastructure.RoleRoutingContext;
import edu.mongo10web.service.CategoryService;
import edu.mongo10web.service.ProductService;
import edu.mongo10web.service.SupplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class WebController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplyService supplyService;

    public WebController(ProductService productService, CategoryService categoryService, SupplyService supplyService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplyService = supplyService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        RoleRoutingContext.Role role = (RoleRoutingContext.Role) session.getAttribute("user_role");
        if(role == null || role == RoleRoutingContext.Role.UNAUTHORIZED){
            return "login";
        }

        model.addAttribute("products", productService.getAll());
        model.addAttribute("deliveries", supplyService.getAll());
        model.addAttribute("categories", categoryService.getAll());

        return "dashboard";
    }

    @PostMapping("/dashboard/deliveries/create")
    public String createDelivery(@ModelAttribute Supply supply) {
        supplyService.create(supply);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/deliveries/delete/{id}")
    public String deleteDelivery(@PathVariable String id) {
        supplyService.delete(id);
        return "redirect:/dashboard";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        session.setAttribute("user_role", RoleRoutingContext.Role.SUPPLIER);
        return "redirect:/dashboard";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
