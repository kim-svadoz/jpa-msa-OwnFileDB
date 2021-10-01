package sunghyun.server.fileDBWork.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sunghyun.server.fileDBWork.domain.Order;
import sunghyun.server.fileDBWork.domain.dto.order.OrderListResponseDto;
import sunghyun.server.fileDBWork.domain.dto.orderitem.OrderItemRequestDto;
import sunghyun.server.fileDBWork.domain.dto.order.OrderRequestDto;
import sunghyun.server.fileDBWork.domain.dto.order.OrderResponseDto;
import sunghyun.server.fileDBWork.domain.dto.product.ProductListResponseDto;
import sunghyun.server.fileDBWork.exception.ProductNotFoundException;
import sunghyun.server.fileDBWork.repository.OrderRepository;
import sunghyun.server.fileDBWork.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private static final String url = "http://127.0.0.1:8088/api/products";

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

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
     * 주문 LIST 조회
     */
    public OrderListResponseDto getOrderList() {
        List<Order> list = orderRepository.findAll();

        return OrderListResponseDto.builder()
                .orderList(list).build();
    }

    /*
     * 주문 생성
     */
    public OrderResponseDto create(OrderRequestDto orderRequestDto) {
        String ids = orderRequestDto.getOrderItemList().stream()
                .map(OrderItemRequestDto::getProductId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));


        ProductListResponseDto productListById = getProductListByIds(ids);
        if (productListById.getList().size() != orderRequestDto.getOrderItemList().size()) {
            throw new ProductNotFoundException();
        }

        Order retOrder = orderRepository.save(orderRequestDto.toEntity());

        return retOrder.of();
    }

    /*
     * 주문 수정
     */
    public OrderResponseDto update(OrderRequestDto orderRequestDto) {
        Optional<Order> findOrder = orderRepository.findById(orderRequestDto.getId());
        findOrder.orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", orderRequestDto.getId())));

        Order order = orderRepository.save(orderRequestDto.toEntity());
        return order.of();
    }

    /*
     * 주문 삭제
     */
    public HttpStatus delete(Long id) {
        Optional<Order> findOrder = orderRepository.findById(id);
        findOrder.orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", id)));

        orderRepository.delete(findOrder.get());
        return HttpStatus.OK;
    }

    /*
     * restTemplate Communication with msa-Product
     */
    public ProductListResponseDto getProductListByIds(String ids) {

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/list")
                .queryParam("ids", ids)
                .build(false);

        ProductListResponseDto forObject = restTemplate.getForObject(uri.toString()
                , ProductListResponseDto.class);
        return forObject;
    }
}
