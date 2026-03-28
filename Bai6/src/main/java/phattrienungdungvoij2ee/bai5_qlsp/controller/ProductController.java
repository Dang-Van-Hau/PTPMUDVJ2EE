package phattrienungdungvoij2ee.bai5_qlsp.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import phattrienungdungvoij2ee.bai5_qlsp.model.Product;
import phattrienungdungvoij2ee.bai5_qlsp.service.CategoryService;
import phattrienungdungvoij2ee.bai5_qlsp.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;

	public ProductController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping
	public String index(Model model) {
		model.addAttribute("listproduct", productService.getAll());
		return "product/products";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("categories", categoryService.getAll());
		return "product/create";
	}

	@PostMapping("/create")
	public String create(
			@Valid Product newProduct,
			BindingResult result,
			@RequestParam("category.id") int categoryId,
			@RequestParam("imageProduct") MultipartFile imageProduct,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("product", newProduct);
			model.addAttribute("categories", categoryService.getAll());
			return "product/create";
		}
		productService.updateImage(newProduct, imageProduct);
		newProduct.setCategory(categoryService.get(categoryId));
		productService.add(newProduct);
		return "redirect:/products";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Product product = productService.get(id);
		if (product == null) {
			return "error/404";
		}
		model.addAttribute("product", product);
		model.addAttribute("categories", categoryService.getAll());
		return "product/edit";
	}

	@PostMapping("/edit")
	public String edit(
			@Valid Product editProduct,
			BindingResult result,
			@RequestParam("category.id") int categoryId,
			@RequestParam("imageProduct") MultipartFile imageProduct,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("product", editProduct);
			model.addAttribute("categories", categoryService.getAll());
			return "product/edit";
		}
		if (imageProduct != null && !imageProduct.isEmpty()) {
			productService.updateImage(editProduct, imageProduct);
		} else {
			Product existing = productService.get(editProduct.getId());
			if (existing != null) {
				editProduct.setImage(existing.getImage());
			}
		}
		editProduct.setCategory(categoryService.get(categoryId));
		productService.update(editProduct);
		return "redirect:/products";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		productService.delete(id);
		return "redirect:/products";
	}
}
