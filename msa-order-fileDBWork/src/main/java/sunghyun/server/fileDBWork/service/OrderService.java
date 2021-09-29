package sunghyun.server.fileDBWork.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sunghyun.server.fileDBWork.domain.Order;
import sunghyun.server.fileDBWork.domain.OrderItem;
import sunghyun.server.fileDBWork.domain.Product;
import sunghyun.server.fileDBWork.domain.dto.*;
import sunghyun.server.fileDBWork.exception.ProductNotFoundException;
import sunghyun.server.fileDBWork.repository.OrderRepository;
import sunghyun.server.fileDBWork.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private static final String url = "http://127.0.0.1:8088/api/products";

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    public ResponseEntity<String> testMSACommunication() {
        String curUrl = url + "/1";
        String forObject = restTemplate.getForObject(curUrl, String.class);
        return ResponseEntity.ok().body(forObject);
    }

    public ResponseEntity<OrderResponseDto> createOrder(OrderRequestDto orderRequestDto) {
        String id = orderRequestDto.getOrderItemList().stream()
                .map(OrderItemRequestDto::getProdcutId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        log.info("id =============================================== "+id);

        ProductListResponseDto productListById = getProductById(id);

        for (ProductResponseDto dto : productListById.getProductResponseDtoList()) {
            System.out.println("dto >>>>>>>>>>>>>>>>>>>>>>>> "+dto);
        }

        if (productListById.getProductResponseDtoList().size() != orderRequestDto.getOrderItemList().size()) {
            throw new ProductNotFoundException();
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        Order order = modelMapper.map(orderRequestDto, Order.class);
        orderRepository.save(order);

        OrderResponseDto result = modelMapper.map(order, OrderResponseDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<List<OrderResponseDto>> getAllOrder() {
        List<OrderResponseDto> list = new ArrayList<>();
        orderRepository.findAll().forEach(order -> {
            list.add(modelMapper.map(order, OrderResponseDto.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    public ResponseEntity<ProductListResponseDto> getProductListPage(int pageNo, int pageSize) {
        String pageUrl = url + String.format("?pageNo=%s&pageSize=%s", pageNo, pageSize);
        ProductListResponseDto forObject = restTemplate.getForObject(pageUrl, ProductListResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(forObject);
    }

    public ResponseEntity<OrderResponseDto> findById(Long id) {
        Optional<Order> findOrder = orderRepository.findById(id);
        return findOrder.map(order -> ResponseEntity
                .status(HttpStatus.OK)
                .body(OrderResponseDto.builder()
                        .id(order.getId()).build()))
                .orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", id)));
    }

    public ProductListResponseDto getProductById(String id) {
        String pageUrl = url + String.format("/%s", id);

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(pageUrl)
                .build(true);

        ProductListResponseDto forObject = restTemplate.getForObject(uri.toString()
                , ProductListResponseDto.class);
        log.info(" getProductResponseDtoList().size() >>>>>>>>>>>>>>>>>>>>>>>>> " + forObject.getProductResponseDtoList().size());
        return forObject;
    }
}
