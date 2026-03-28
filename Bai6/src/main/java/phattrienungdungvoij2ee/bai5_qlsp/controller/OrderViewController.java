package phattrienungdungvoij2ee.bai5_qlsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderViewController {

	@GetMapping("/success")
	public String orderSuccess(Model model) {
		if (model.getAttribute("orderId") == null) {
			return "redirect:/shop";
		}
		return "order/success";
	}
}
