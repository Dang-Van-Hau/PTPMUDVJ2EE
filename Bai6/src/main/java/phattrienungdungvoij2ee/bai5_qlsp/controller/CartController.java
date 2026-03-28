package phattrienungdungvoij2ee.bai5_qlsp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import phattrienungdungvoij2ee.bai5_qlsp.dto.CartLineView;
import phattrienungdungvoij2ee.bai5_qlsp.model.Order;
import phattrienungdungvoij2ee.bai5_qlsp.model.Product;
import phattrienungdungvoij2ee.bai5_qlsp.service.CartService;
import phattrienungdungvoij2ee.bai5_qlsp.service.OrderService;
import phattrienungdungvoij2ee.bai5_qlsp.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;
	private final ProductService productService;
	private final OrderService orderService;

	public CartController(CartService cartService, ProductService productService, OrderService orderService) {
		this.cartService = cartService;
		this.productService = productService;
		this.orderService = orderService;
	}

	@GetMapping
	public String viewCart(HttpSession session, Model model) {
		Map<Integer, Integer> cart = cartService.getCart(session);
		List<CartLineView> lines = new ArrayList<>();
		long grandTotal = 0;
		for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
			Product product = productService.get(entry.getKey());
			if (product == null) {
				continue;
			}
			int qty = entry.getValue();
			long lineTotal = product.getPrice() * qty;
			grandTotal += lineTotal;
			lines.add(new CartLineView(product, qty, lineTotal));
		}
		model.addAttribute("lines", lines);
		model.addAttribute("grandTotal", grandTotal);
		return "cart/cart";
	}

	@PostMapping("/add")
	public String addToCart(
			@RequestParam int productId,
			@RequestParam(defaultValue = "1") int quantity,
			HttpSession session,
			@RequestParam(required = false) String redirect) {
		cartService.add(session, productId, quantity);
		if (redirect != null && !redirect.isBlank()) {
			return "redirect:" + redirect;
		}
		return "redirect:/shop";
	}

	@PostMapping("/update")
	public String updateQuantity(
			@RequestParam int productId,
			@RequestParam int quantity,
			HttpSession session) {
		cartService.setQuantity(session, productId, quantity);
		return "redirect:/cart";
	}

	@PostMapping("/checkout")
	public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
		try {
			Order order = orderService.checkout(session);
			redirectAttributes.addFlashAttribute("orderId", order.getId());
			redirectAttributes.addFlashAttribute("orderTotal", order.getTotalAmount());
			return "redirect:/order/success";
		} catch (IllegalStateException ex) {
			redirectAttributes.addFlashAttribute("checkoutError", ex.getMessage());
			return "redirect:/cart";
		}
	}
}
