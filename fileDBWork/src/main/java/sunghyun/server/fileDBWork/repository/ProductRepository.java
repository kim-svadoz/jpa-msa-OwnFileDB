package sunghyun.server.fileDBWork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunghyun.server.fileDBWork.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Page<Product> findAll(Pageable pageable);
}
