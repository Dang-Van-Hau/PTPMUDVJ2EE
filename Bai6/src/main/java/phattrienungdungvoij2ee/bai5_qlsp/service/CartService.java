package phattrienungdungvoij2ee.bai5_qlsp.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

	public static final String SESSION_KEY = "cartProductQuantities";

	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getCart(HttpSession session) {
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(SESSION_KEY);
		if (cart == null) {
			cart = new LinkedHashMap<>();
			session.setAttribute(SESSION_KEY, cart);
		}
		return cart;
	}

	public void add(HttpSession session, int productId, int quantity) {
		int q = quantity < 1 ? 1 : quantity;
		Map<Integer, Integer> cart = getCart(session);
		cart.merge(productId, q, Integer::sum);
	}

	public void setQuantity(HttpSession session, int productId, int quantity) {
		Map<Integer, Integer> cart = getCart(session);
		if (quantity <= 0) {
			cart.remove(productId);
		} else {
			cart.put(productId, quantity);
		}
	}

	public void clear(HttpSession session) {
		session.removeAttribute(SESSION_KEY);
	}
}
