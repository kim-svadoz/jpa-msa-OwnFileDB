package sunghyun.server.fileDBWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import sunghyun.server.fileDBWork.domain.Product;
import sunghyun.server.fileDBWork.domain.dto.ProductResponseDto;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
