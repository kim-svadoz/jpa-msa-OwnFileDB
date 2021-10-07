package sunghyun.server.fileDBWork.domain.entity;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class OrderItem {

    private Long id;

    private Long productId;

    private Order order;

    /*
     * 생성 메서드
     */
    public static OrderItem createOrderItem(Long id) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(id);

        return orderItem;
    }


}
