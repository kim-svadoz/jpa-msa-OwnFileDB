package sunghyun.server.fileDBWork.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sunghyun.server.fileDBWork.domain.vo.CustomFile;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.OptionalLong;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
    private final File f;

    @Override
    public File findFolderById(Long id) {
        String pNum = id + "";
        String path = f.getPath() + "/" + pNum;
        return new File(path);
    }

    @Override
    public CustomFile save() {
        String[] list = f.list();
        OptionalLong max = Arrays.stream(list).mapToLong(Long::parseLong).max();

        String pNum = max.isPresent() == false ? 1 + "" : (max.getAsLong() + 1) + "";
        String productPath = f.getPath() + "/" + pNum;
        File productFolder = new File(productPath);
        if (!productFolder.exists()) {
            productFolder.mkdir();
        }
        return CustomFile.builder()
                .file(productFolder)
                .index(Long.parseLong(pNum)).build();
    }
}
