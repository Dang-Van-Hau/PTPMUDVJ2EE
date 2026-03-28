package phattrienungdungvoij2ee.bai5_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import phattrienungdungvoij2ee.bai5_qlsp.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
