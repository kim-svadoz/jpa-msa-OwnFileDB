package sunghyun.server.fileDBWork.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sunghyun.server.fileDBWork.domain.entity.Order;
import sunghyun.server.fileDBWork.domain.dto.order.OrderListResponseDto;
import sunghyun.server.fileDBWork.domain.dto.orderitem.OrderItemRequestDto;
import sunghyun.server.fileDBWork.domain.dto.order.OrderRequestDto;
import sunghyun.server.fileDBWork.domain.dto.order.OrderResponseDto;
import sunghyun.server.fileDBWork.domain.dto.orderitem.OrderItemResponseDto;
import sunghyun.server.fileDBWork.domain.dto.product.ProductListResponseDto;
import sunghyun.server.fileDBWork.domain.vo.CustomFile;
import sunghyun.server.fileDBWork.exception.OrderNotFoundException;
import sunghyun.server.fileDBWork.repository.OrderRepository;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private static final String url = "http://127.0.0.1:8090/api/products";

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;
    private final File f;

    /*
     * 상품 LIST 조회
     */
    public ProductListResponseDto getProductList() {
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/lists")
                .build(false);

        ProductListResponseDto forObject = restTemplate.getForObject(uri.toString()
                , ProductListResponseDto.class);
        return forObject;
    }

    /*
     * 주문 생성
     */
    public OrderResponseDto create(OrderRequestDto orderRequestDto) throws IOException {
        String ids = orderRequestDto.getOrderItemList().stream()
                .map(OrderItemRequestDto::getProductId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        ProductListResponseDto productListById = getProductListByIds(ids);
        if (productListById.getList().size() != orderRequestDto.getOrderItemList().size()) {
            throw new OrderNotFoundException();
        }

        CustomFile nextDir = orderRepository.createNextDir();

        Order retOrder = orderRepository.save(orderRequestDto.toEntity(), nextDir.getIndex());

        return retOrder.of();
    }

    /*
     * 주문 수정
     */
    public OrderResponseDto update(OrderRequestDto orderRequestDto) throws IOException {
        long id = orderRequestDto.getId();
        File folder = orderRepository.findFolderById(id);
        if (!folder.exists()) {
            throw new OrderNotFoundException(String.format("ID[%s] not found", id));
        }

        Order retOrder = orderRepository.update(orderRequestDto.toEntity(), folder);

        return retOrder.of();
    }

    /*
     * 주문 삭제
     */
    public HttpStatus delete(Long id) throws IOException {
        File folder = orderRepository.findFolderById(id);
        if (!folder.exists()) {
            throw new OrderNotFoundException(String.format("ID[%s] not found", id));
        }

        long idx = 1;
        long size = folder.list().length;
        while (idx <= size) {
            String path = folder + "/" + idx;
            idx += 1;
            RandomAccessFile raf = new RandomAccessFile(path+"/product.txt", "rw");
            if (raf.length() == 0) {
                throw new OrderNotFoundException(String.format("ID[%s] not found", id));
            }

            raf.setLength(0);
        }

        return HttpStatus.OK;
    }


    /*
     * 주문 전체 List 조회
     */
    public OrderListResponseDto getOrderList() throws IOException {
        String[] flist = f.list();

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        for (String num : flist) {
            long id = Long.parseLong(num);

            File folder = orderRepository.findFolderById(id);
            if (!folder.exists()) {
                throw new OrderNotFoundException(String.format("ID[%s] not found", id));
            }

            long idx = 1;
            long size = folder.list().length;
            List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();
            RandomAccessFile raf;
            while (idx <= size) {
                String productNum = folder.getPath() + "/" + idx;
                File product = new File(productNum);
                if (!product.exists()) {
                    throw new OrderNotFoundException(String.format("ID[%s] not found", Integer.parseInt(productNum)));
                }
                raf = new RandomAccessFile(product+"/product.txt", "r");
                raf.seek(0);

                String name = raf.readLine();
                idx += 1;
                if (name == null || name.length() == 0) continue;

                orderItemResponseDtos.add(OrderItemResponseDto.builder().id(idx - 1).productId(Long.parseLong(name)).build());
            }
            if (orderItemResponseDtos.size() == 0) continue;

            Collections.sort(orderItemResponseDtos, new Comparator<OrderItemResponseDto>() {
                @Override
                public int compare(OrderItemResponseDto o1, OrderItemResponseDto o2) {
                    if (o1.getProductId() - o2.getProductId() > 0) {
                        return 1;
                    }
                    return -1;
                }
            });

            orderResponseDtos.add(OrderResponseDto.builder().id(id).orderItemList(orderItemResponseDtos).build());
        }

        return OrderListResponseDto.builder().list(orderResponseDtos).build();
    }

    /*
     * restTemplate Communication with msa-Product
     */
    public ProductListResponseDto getProductListByIds(String ids) {

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/list")
                .queryParam("ids", ids)
                .build(true);

        ProductListResponseDto forObject = restTemplate.getForObject(uri.toString()
                , ProductListResponseDto.class);
        return forObject;
    }
}
