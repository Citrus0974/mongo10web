package edu.mongo10web.controller;

import edu.mongo10web.entity.Category;
import edu.mongo10web.entity.Product;
import edu.mongo10web.entity.Supply;
import edu.mongo10web.entity.SupplyStatus;
import edu.mongo10web.service.CategoryService;
import edu.mongo10web.service.ProductService;
import edu.mongo10web.service.SupplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {

        model.addAttribute("products", productService.getAll());
        model.addAttribute("deliveries", supplyService.getAll());
        model.addAttribute("categories", categoryService.getAll());

        return "dashboard";
    }

    // --- CRUD ПОСТАВОК (SUPPLY) ---
    @PostMapping("/dashboard/deliveries/create")
    public String createDelivery(@RequestParam String productId, @RequestParam Integer quantity) {
        Product product = productService.getById(productId);
        //arriveDateTime устанавливается как "now", статус по умолчанию - CREATED
        Supply supply = new Supply(product, quantity, SupplyStatus.CREATED, LocalDateTime.now(), null);
        supplyService.create(supply);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/deliveries/delete/{id}")
    public String deleteDelivery(@PathVariable String id) {
        supplyService.delete(id);
        return "redirect:/dashboard";
    }

    // --- CRUD ТОВАРОВ (PRODUCT) ---
    @PostMapping("/dashboard/products/create")
    public String createProduct(@RequestParam String name,
                                @RequestParam String manufacturer,
                                @RequestParam Integer cost,
                                @RequestParam String categoryId) {
        Category category = categoryService.getById(categoryId);
        Product product = new Product(name, manufacturer, cost, category);
        productService.create(product);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/products/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.delete(id);
        return "redirect:/dashboard";
    }

    // --- CRUD КАТЕГОРИЙ (CATEGORY) ---
    @PostMapping("/dashboard/categories/create")
    public String createCategory(@RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam String criteries) {
        Category category = new Category(name, description, criteries);
        categoryService.create(category);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/categories/delete/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryService.delete(id);
        return "redirect:/dashboard";
    }

    @PostMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
