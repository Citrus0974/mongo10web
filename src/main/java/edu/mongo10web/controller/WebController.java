package edu.mongo10web.controller;

import edu.mongo10web.entity.Category;
import edu.mongo10web.entity.Product;
import edu.mongo10web.entity.Supply;
import edu.mongo10web.entity.SupplyStatus;
import edu.mongo10web.service.AnalyticsService;
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
    private final AnalyticsService analyticsService;

    public WebController(ProductService productService, CategoryService categoryService, SupplyService supplyService, AnalyticsService analyticsService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplyService = supplyService;
        this.analyticsService = analyticsService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {

        model.addAttribute("products", productService.getAll());
        model.addAttribute("deliveries", supplyService.getAll());

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth);
//        System.out.println(auth.getAuthorities().stream().map(a -> a.getAuthority()).toList());
//        if (auth != null && auth.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPPLIER"))) {
//            model.addAttribute("categories", Collections.emptyList());
//            System.out.println("inside if");
//        } else{
//            model.addAttribute("categories", categoryService.getAll());
//        }
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("statuses", SupplyStatus.values());
        return "dashboard";
    }

    // --- CRUD ПОСТАВОК (SUPPLY) ---
    @PostMapping("/dashboard/deliveries/create")
    public String createDelivery(@RequestParam String productId, @RequestParam Integer quantity) {
        Product product = productService.getById(productId);
        Supply supply = new Supply(product, quantity, SupplyStatus.CREATED, LocalDateTime.now(), null);
        supplyService.create(supply);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/deliveries/delete/{id}")
    public String deleteDelivery(@PathVariable String id) {
        supplyService.delete(id);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/deliveries/update/{id}")
    public String updateDelivery(@PathVariable String id,
                                 @RequestParam String productId,
                                 @RequestParam Integer quantity,
                                 @RequestParam SupplyStatus status) {
        Product product = productService.getById(productId);
        Supply existing = supplyService.getById(id);
        existing.setProduct(product);
        existing.setQuantity(quantity);
        existing.setStatus(status);
        supplyService.update(id, existing);
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

    @PostMapping("/dashboard/products/update/{id}")
    public String updateProduct(@PathVariable String id,
                                @RequestParam String name,
                                @RequestParam String manufacturer,
                                @RequestParam Integer cost,
                                @RequestParam String categoryId) {
        Category category = categoryService.getById(categoryId);
        Product updated = new Product(name, manufacturer, cost, category);
        productService.update(id, updated);
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

    @PostMapping("/dashboard/categories/update/{id}")
    public String updateCategory(@PathVariable String id,
                                 @RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam String criteries) {
        Category updated = new Category(name, description, criteries);
        categoryService.update(id, updated);
        return "redirect:/dashboard";
    }

    @GetMapping("/analytics")
    public String analytics(Model model) {
        model.addAttribute("expensiveProducts", analyticsService.getExpensiveProducts(100));
        model.addAttribute("urgentSupplies", analyticsService.getUrgentSupplies());
        model.addAttribute("manufacturersCount", analyticsService.getProductsCountByManufacturer());

        model.addAttribute("stockMetrics", analyticsService.getProductStockMetrics());
        model.addAttribute("categoryAvgCost", analyticsService.getAverageCostByCategory());
        model.addAttribute("topManufacturers", analyticsService.getTopManufacturersBySupplyVolume());

        return "analytics";
    }

    @PostMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
