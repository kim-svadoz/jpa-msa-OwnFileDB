package sunghyun.server.fileDBWork.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunghyun.server.fileDBWork.domain.dto.OrderRequestDto;
import sunghyun.server.fileDBWork.domain.dto.OrderResponseDto;
import sunghyun.server.fileDBWork.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderApiController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<String> test() {
        return orderService.testMSACommunication();
    }

    /*
     * 주문 LIST 조회
     */

    /*
     * 주문 생성
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> orderProduct(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    /*
     * 주문 수정
     */


    /*
     * 주문 삭제
     */
}
