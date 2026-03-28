package phattrienungdungvoij2ee.bai5_qlsp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import phattrienungdungvoij2ee.bai5_qlsp.model.Order;
import phattrienungdungvoij2ee.bai5_qlsp.model.OrderDetail;
import phattrienungdungvoij2ee.bai5_qlsp.model.Product;
import phattrienungdungvoij2ee.bai5_qlsp.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductService productService;
	private final CartService cartService;

	public OrderService(OrderRepository orderRepository, ProductService productService, CartService cartService) {
		this.orderRepository = orderRepository;
		this.productService = productService;
		this.cartService = cartService;
	}

	@Transactional
	public Order checkout(HttpSession session) {
		Map<Integer, Integer> cart = cartService.getCart(session);
		if (cart.isEmpty()) {
			throw new IllegalStateException("Giỏ hàng trống");
		}
		Order order = new Order();
		order.setCreatedAt(LocalDateTime.now());
		List<OrderDetail> details = new ArrayList<>();
		long total = 0;
		for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
			Product product = productService.get(entry.getKey());
			if (product == null) {
				continue;
			}
			int qty = entry.getValue();
			long lineTotal = product.getPrice() * qty;
			total += lineTotal;
			OrderDetail detail = new OrderDetail();
			detail.setOrder(order);
			detail.setProductId(product.getId());
			detail.setProductName(product.getName());
			detail.setUnitPrice(product.getPrice());
			detail.setQuantity(qty);
			details.add(detail);
		}
		if (details.isEmpty()) {
			throw new IllegalStateException("Không có sản phẩm hợp lệ trong giỏ hàng");
		}
		order.setTotalAmount(total);
		order.setDetails(details);
		Order saved = orderRepository.save(order);
		cartService.clear(session);
		return saved;
	}
}
