package com.phattrienungdungvoiJ2EE.bai4_crud.Controller;

import com.phattrienungdungvoiJ2EE.bai4_crud.Service.ProductService;
import com.phattrienungdungvoiJ2EE.bai4_crud.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }
    
    @GetMapping("/home/search")
    public String search(@RequestParam(value = "query", required = false) String query, Model model) {
        List<Product> allProducts = productService.getAll();
        
        if (query != null && !query.trim().isEmpty()) {
            String searchQuery = query.trim().toLowerCase();
            List<Product> searchResults = allProducts.stream()
                    .filter(p -> 
                        (p.getName() != null && p.getName().toLowerCase().contains(searchQuery)) ||
                        (p.getCategory() != null && p.getCategory().getName() != null && 
                         p.getCategory().getName().toLowerCase().contains(searchQuery))
                    )
                    .collect(Collectors.toList());
            model.addAttribute("listproduct", searchResults);
            model.addAttribute("searchQuery", query);
            model.addAttribute("resultCount", searchResults.size());
        } else {
            model.addAttribute("listproduct", allProducts);
        }
        
        return "product/products";
    }
}
