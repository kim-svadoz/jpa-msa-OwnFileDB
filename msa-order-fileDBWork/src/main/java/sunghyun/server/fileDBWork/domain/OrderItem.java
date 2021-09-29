package sunghyun.server.fileDBWork.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import sunghyun.server.fileDBWork.domain.dto.OrderItemResponseDto;
import sunghyun.server.fileDBWork.domain.dto.OrderResponseDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
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
