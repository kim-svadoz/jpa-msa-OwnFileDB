package sunghyun.server.fileDBWork.domain.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponseDto {
    List<ProductResponseDto> list;
}
