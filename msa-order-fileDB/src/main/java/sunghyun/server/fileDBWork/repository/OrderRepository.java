package sunghyun.server.fileDBWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sunghyun.server.fileDBWork.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
