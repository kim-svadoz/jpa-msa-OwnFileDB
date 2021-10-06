package sunghyun.server.fileDBWork.domain.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sunghyun.server.fileDBWork.domain.dto.orderitem.OrderItemResponseDto;
import sunghyun.server.fileDBWork.domain.dto.order.OrderResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "주문 상세 정보를 위한 도메인 객체")
public class Order {

    private Long id;

    /*
     * 한번 주문 시 여러 상품을 주문할 수 있다.
     * 주문(Order)과 주문상품(OrderItem)은 일대다 관계이다.
     */
    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderResponseDto of() {
        return OrderResponseDto.builder()
                .id(this.id)
                .orderItemList(this.orderItems.stream()
                    .map(orderItem -> OrderItemResponseDto.builder()
                        .id(orderItem.getId())
                        .productId(orderItem.getProductId()).build())
                    .collect(Collectors.toList())).build();
    }
}
