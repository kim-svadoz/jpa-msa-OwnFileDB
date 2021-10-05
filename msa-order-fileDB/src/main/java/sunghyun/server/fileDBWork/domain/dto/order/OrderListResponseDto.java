package sunghyun.server.fileDBWork.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sunghyun.server.fileDBWork.domain.Order;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderListResponseDto {

    private List<OrderResponseDto> list;
}
