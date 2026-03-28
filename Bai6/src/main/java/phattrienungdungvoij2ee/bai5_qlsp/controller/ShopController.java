package phattrienungdungvoij2ee.bai5_qlsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import phattrienungdungvoij2ee.bai5_qlsp.service.CategoryService;
import phattrienungdungvoij2ee.bai5_qlsp.service.ProductService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	private final ProductService productService;
	private final CategoryService categoryService;

	public ShopController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping
	public String shop(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer categoryId,
			@RequestParam(defaultValue = "asc") String sort,
			@RequestParam(defaultValue = "0") int page,
			Model model) {
		Integer filterCategory = (categoryId != null && categoryId > 0) ? categoryId : null;
		model.addAttribute("productPage", productService.findShopPage(keyword, filterCategory, sort, page));
		model.addAttribute("categories", categoryService.getAll());
		model.addAttribute("keyword", keyword != null ? keyword : "");
		model.addAttribute("categoryId", filterCategory != null ? filterCategory : 0);
		model.addAttribute("sort", sort.equalsIgnoreCase("desc") ? "desc" : "asc");
		model.addAttribute("currentPage", page);
		return "shop/shop";
	}
}
