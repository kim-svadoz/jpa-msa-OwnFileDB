package sunghyun.server.fileDBWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sunghyun.server.fileDBWork.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
