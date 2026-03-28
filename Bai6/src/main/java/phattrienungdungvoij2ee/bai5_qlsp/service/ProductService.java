package phattrienungdungvoij2ee.bai5_qlsp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import phattrienungdungvoij2ee.bai5_qlsp.model.Product;
import phattrienungdungvoij2ee.bai5_qlsp.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAll() {
		return productRepository.findAll();
	}

	public Product get(int id) {
		return productRepository.findById(id).orElse(null);
	}

	public void add(Product product) {
		productRepository.save(product);
	}

	public void update(Product product) {
		productRepository.save(product);
	}

	public void delete(int id) {
		productRepository.deleteById(id);
	}

	public Page<Product> findShopPage(String keyword, Integer categoryId, String sortDirection, int pageIndex) {
		Sort.Direction dir = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(pageIndex, 5, Sort.by(dir, "price"));
		String kw = keyword == null ? "" : keyword.trim();
		boolean hasKeyword = !kw.isEmpty();
		boolean hasCategory = categoryId != null && categoryId > 0;
		if (hasKeyword && hasCategory) {
			return productRepository.findByNameContainingIgnoreCaseAndCategory_Id(kw, categoryId, pageable);
		}
		if (hasKeyword) {
			return productRepository.findByNameContainingIgnoreCase(kw, pageable);
		}
		if (hasCategory) {
			return productRepository.findByCategory_Id(categoryId, pageable);
		}
		return productRepository.findAll(pageable);
	}

	public void updateImage(Product product, MultipartFile imageFile) {
		if (imageFile == null || imageFile.isEmpty()) return;

		String contentType = imageFile.getContentType();
		if (contentType != null && !contentType.startsWith("image/")) {
			throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
		}
		try {
			String projectRoot = System.getProperty("user.dir");
			Path dirImages = Paths.get(projectRoot, "src/main/resources/static/images");
			if (!Files.exists(dirImages)) {
				Files.createDirectories(dirImages);
			}
			String originalFilename = imageFile.getOriginalFilename();
			if (originalFilename != null) {
				originalFilename = originalFilename.replaceAll("\\s+", "_");
			}
			String newFileName = UUID.randomUUID() + "_" + originalFilename;
			Path dest = dirImages.resolve(newFileName);
			Files.copy(imageFile.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
			product.setImage(newFileName);
		} catch (IOException e) {
			throw new RuntimeException("Lỗi khi lưu hình ảnh: " + e.getMessage(), e);
		}
	}
}
