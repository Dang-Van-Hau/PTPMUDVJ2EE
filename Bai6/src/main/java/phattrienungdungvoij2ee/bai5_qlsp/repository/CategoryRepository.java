package phattrienungdungvoij2ee.bai5_qlsp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import phattrienungdungvoij2ee.bai5_qlsp.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Optional<Category> findByName(String name);
}
