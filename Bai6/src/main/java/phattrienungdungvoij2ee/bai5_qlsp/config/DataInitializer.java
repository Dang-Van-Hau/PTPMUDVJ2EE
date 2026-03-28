package phattrienungdungvoij2ee.bai5_qlsp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import phattrienungdungvoij2ee.bai5_qlsp.model.Category;
import phattrienungdungvoij2ee.bai5_qlsp.model.Product;
import phattrienungdungvoij2ee.bai5_qlsp.repository.CategoryRepository;
import phattrienungdungvoij2ee.bai5_qlsp.repository.ProductRepository;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;

	public DataInitializer(CategoryRepository categoryRepository, ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}

	@Override
	public void run(String... args) {
		if (categoryRepository.count() == 0) {
			categoryRepository.save(new Category(0, "Điện thoại"));
			categoryRepository.save(new Category(0, "Laptop"));
			categoryRepository.save(new Category(0, "Phụ kiện"));
		}
		if (productRepository.count() == 0) {
			Category phone = categoryRepository.findByName("Điện thoại").orElseThrow();
			Category laptop = categoryRepository.findByName("Laptop").orElseThrow();
			Category accessory = categoryRepository.findByName("Phụ kiện").orElseThrow();
			productRepository.save(new Product(0, "iPhone 15", null, 25_000_000L, phone));
			productRepository.save(new Product(0, "Samsung Galaxy S24", null, 22_000_000L, phone));
			productRepository.save(new Product(0, "Xiaomi 14", null, 18_000_000L, phone));
			productRepository.save(new Product(0, "MacBook Air M3", null, 28_000_000L, laptop));
			productRepository.save(new Product(0, "Dell XPS 15", null, 35_000_000L, laptop));
			productRepository.save(new Product(0, "Tai nghe Bluetooth", null, 1_500_000L, accessory));
			productRepository.save(new Product(0, "Ốp lưng điện thoại", null, 200_000L, accessory));
		}
	}
}
