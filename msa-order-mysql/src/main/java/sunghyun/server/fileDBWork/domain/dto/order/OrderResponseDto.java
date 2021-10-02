package sunghyun.server.fileDBWork.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sunghyun.server.fileDBWork.domain.dto.orderitem.OrderItemResponseDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;

    private List<OrderItemResponseDto> orderItemList;
}
