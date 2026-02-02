package com.phattrienungdungvoiJ2EE.bai4_crud.Controller;

import com.phattrienungdungvoiJ2EE.bai4_crud.Service.CategoryService;
import com.phattrienungdungvoiJ2EE.bai4_crud.Service.ProductService;
import com.phattrienungdungvoiJ2EE.bai4_crud.model.Category;
import com.phattrienungdungvoiJ2EE.bai4_crud.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public String Index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }
    
    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }
    
    @PostMapping("/create")
    public String Create(@Valid Product newProduct, 
                        BindingResult result,
                        @RequestParam(value = "category.id", required = false) Integer categoryId,
                        @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        
        // Kiểm tra category
        if (categoryId == null || categoryId <= 0) {
            model.addAttribute("error", "Vui lòng chọn danh mục sản phẩm!");
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        
        Category category = categoryService.get(categoryId);
        if (category == null) {
            model.addAttribute("error", "Danh mục không tồn tại!");
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        newProduct.setCategory(category);
        
        // Xử lý ảnh
        if (imageProduct != null && !imageProduct.isEmpty()) {
            try {
                productService.updateImage(newProduct, imageProduct);
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                model.addAttribute("product", newProduct);
                model.addAttribute("categories", categoryService.getAll());
                return "product/create";
            }
        }
        
        productService.add(newProduct);
        return "redirect:/products";
    }
    
    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "error/404"; // Trang lỗi tùy chỉnh
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }
    
    @PostMapping("/edit")
    public String Edit(@Valid Product editProduct,
                      BindingResult result,
                      @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
                      @RequestParam(value = "category.id", required = false) Integer categoryId,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
        
        // Lấy sản phẩm hiện tại để giữ category và image nếu không có thay đổi
        Product existingProduct = productService.get(editProduct.getId());
        if (existingProduct == null) {
            return "error/404";
        }
        
        // Xử lý category
        if (categoryId != null && categoryId > 0) {
            Category category = categoryService.get(categoryId);
            if (category != null) {
                editProduct.setCategory(category);
            } else {
                model.addAttribute("error", "Danh mục không tồn tại!");
                model.addAttribute("product", editProduct);
                model.addAttribute("categories", categoryService.getAll());
                return "product/edit";
            }
        } else {
            // Giữ category cũ nếu không có category mới
            editProduct.setCategory(existingProduct.getCategory());
        }
        
        // Giữ image cũ nếu không có image mới
        if (imageProduct == null || imageProduct.isEmpty()) {
            editProduct.setImage(existingProduct.getImage());
        }
        
        // Cập nhật ảnh nếu có
        if (imageProduct != null && !imageProduct.isEmpty()) {
            try {
                productService.updateImage(editProduct, imageProduct);
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                model.addAttribute("product", editProduct);
                model.addAttribute("categories", categoryService.getAll());
                return "product/edit";
            }
        }
        
        // Cập nhật sản phẩm
        productService.update(editProduct);
        return "redirect:/products";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
