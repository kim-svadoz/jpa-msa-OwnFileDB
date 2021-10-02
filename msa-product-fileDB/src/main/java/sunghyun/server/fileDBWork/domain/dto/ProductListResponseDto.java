package sunghyun.server.fileDBWork.domain.dto;

import lombok.*;
import org.modelmapper.ModelMapper;
import sunghyun.server.fileDBWork.domain.Product;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponseDto {
    private List<ProductResponseDto> list;
}
