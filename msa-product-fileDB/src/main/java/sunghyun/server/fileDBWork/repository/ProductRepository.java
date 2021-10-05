package sunghyun.server.fileDBWork.repository;

import org.springframework.stereotype.Repository;
import sunghyun.server.fileDBWork.domain.Product;
import sunghyun.server.fileDBWork.domain.vo.CustomFile;

import java.io.File;
import java.util.List;

@Repository
public interface ProductRepository {
    File findFolderById(Long id);
    CustomFile createNextDir();
}
