package phattrienungdungvoij2ee.bai5_qlsp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import phattrienungdungvoij2ee.bai5_qlsp.model.Category;
import phattrienungdungvoij2ee.bai5_qlsp.repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	public Category get(int id) {
		return categoryRepository.findById(id).orElse(null);
	}
}
