package sunghyun.server.fileDBWork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sunghyun.server.fileDBWork.domain.entity.Order;
import sunghyun.server.fileDBWork.domain.entity.OrderItem;
import sunghyun.server.fileDBWork.domain.vo.CustomFile;
import sunghyun.server.fileDBWork.exception.OrderNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalLong;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final File f;

    @Override
    public File findFolderById(Long id) {
        String pNum = id + "";
        String path = f.getPath() + "/" + pNum;
        return new File(path);
    }

    @Override
    public CustomFile createNextDir() {
        String[] list = f.list();
        OptionalLong max = Arrays.stream(list).mapToLong(Long::parseLong).max();

        String pNum = max.isPresent() == false ? 1 + "" : (max.getAsLong() + 1) + "";
        String productPath = f.getPath() + "/" + pNum;
        File orderFolder = new File(productPath);
        if (!orderFolder.exists()) {
            orderFolder.mkdir();
        }
        return CustomFile.builder()
                .file(orderFolder)
                .index(Long.parseLong(pNum)).build();
    }

    @Override
    public Order save(Order order, Long id) throws IOException {
        long size = order.getOrderItems().size();

        String path = f.getPath() + "/" + id;

        List<OrderItem> orderItems = order.getOrderItems();

        int idx = 1;
        for (OrderItem item : orderItems) {
            String curPath = path + "/" + idx;
            idx++;
            File itemFolder = new File(curPath);
            itemFolder.mkdir();

            RandomAccessFile raf = new RandomAccessFile(itemFolder+"/product.txt", "rw");
            raf.write((item.getProductId()+"").getBytes());
        }

        return order;
    }

    @Override
    public Order update(Order order, File folder) throws IOException {
        List<OrderItem> orderItems = order.getOrderItems();

        RandomAccessFile raf;
        for (OrderItem item : orderItems) {
            Long requestId = item.getId();
            String productPath = folder + "/" + requestId;
            File product = new File(productPath);
            if (!product.exists()) {
                throw new OrderNotFoundException(String.format("ID[%s] not found", requestId));
            }

            raf = new RandomAccessFile(product+"/product.txt", "rw");
            raf.setLength(0);
            raf.seek(0);
            raf.write((item.getProductId()+"").getBytes());
        }
        return order;
    }
}
