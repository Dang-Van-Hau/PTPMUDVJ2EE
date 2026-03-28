package phattrienungdungvoij2ee.bai5_qlsp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import phattrienungdungvoij2ee.bai5_qlsp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

	Page<Product> findByCategory_Id(int categoryId, Pageable pageable);

	Page<Product> findByNameContainingIgnoreCaseAndCategory_Id(String keyword, int categoryId, Pageable pageable);
}
