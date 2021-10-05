package sunghyun.server.fileDBWork.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunghyun.server.fileDBWork.domain.dto.order.OrderListResponseDto;
import sunghyun.server.fileDBWork.domain.dto.order.OrderRequestDto;
import sunghyun.server.fileDBWork.domain.dto.order.OrderResponseDto;
import sunghyun.server.fileDBWork.domain.dto.product.ProductListResponseDto;
import sunghyun.server.fileDBWork.service.OrderService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderApiController {

    private final OrderService orderService;

    /*
     * 상품 LIST 조회
     */
    @GetMapping("/products")
    public ResponseEntity<ProductListResponseDto> getProducts() {
        ProductListResponseDto productListByIds = orderService.getProductList();
        return new ResponseEntity<ProductListResponseDto>(productListByIds, HttpStatus.OK);
    }

    /*
     * 주문 생성
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> orderProduct(@RequestBody OrderRequestDto orderRequestDto) throws IOException {
        OrderResponseDto createOrder = orderService.create(orderRequestDto);
        return new ResponseEntity<OrderResponseDto>(createOrder, HttpStatus.OK);
    }

    /*
     * 주문 수정
     */
    @PutMapping
    public ResponseEntity<OrderResponseDto> changeOrder(@RequestBody OrderRequestDto orderRequestDto) throws IOException {
        OrderResponseDto changeOrder = orderService.update(orderRequestDto);
        return new ResponseEntity<OrderResponseDto>(changeOrder, HttpStatus.OK);
    }

    /*
     * 특정 주문 삭제
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(Long id) throws IOException {
        HttpStatus status = orderService.delete(id);
        return new ResponseEntity<>(status);
    }

    /*
     * 주문 LIST 조회
     */
    @GetMapping
    public ResponseEntity<OrderListResponseDto> getOrderList() throws IOException {
        OrderListResponseDto orderList = orderService.getOrderList();
        return new ResponseEntity<OrderListResponseDto>(orderList, HttpStatus.OK);
    }

}
