package sunghyun.server.fileDBWork.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
public class ProductListResponseDto {
    List<ProductResponseDto> list;
}
