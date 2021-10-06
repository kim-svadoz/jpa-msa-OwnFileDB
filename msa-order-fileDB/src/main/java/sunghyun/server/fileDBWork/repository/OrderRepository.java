package sunghyun.server.fileDBWork.repository;

import org.springframework.stereotype.Repository;
import sunghyun.server.fileDBWork.domain.entity.Order;
import sunghyun.server.fileDBWork.domain.vo.CustomFile;

import java.io.File;
import java.io.IOException;

@Repository
public interface OrderRepository {
    File findFolderById(Long id);
    CustomFile createNextDir();
    Order save(Order order, Long id) throws IOException;
    Order update(Order order, File folder) throws IOException;
}