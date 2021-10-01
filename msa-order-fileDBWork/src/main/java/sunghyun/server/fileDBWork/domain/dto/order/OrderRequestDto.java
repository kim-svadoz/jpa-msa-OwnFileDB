package sunghyun.server.fileDBWork.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import sunghyun.server.fileDBWork.domain.Order;
import sunghyun.server.fileDBWork.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    private Long id;

    private List<OrderItemRequestDto> orderItemList;

    public Order toEntity() {
        Order order = Order.builder()
                .id(id)
                .build();

        List<OrderItem> orderItems = orderItemList.stream()
                .map(orderItemRequestDto -> OrderItem.builder()
                        .id(orderItemRequestDto.getId())
                        .productId(orderItemRequestDto.getProductId())
                        .order(order).build())
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        return order;
    }
}
