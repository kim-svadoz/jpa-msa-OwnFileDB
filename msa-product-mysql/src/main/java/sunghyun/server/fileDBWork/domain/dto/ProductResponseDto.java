package sunghyun.server.fileDBWork.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;
import sunghyun.server.fileDBWork.domain.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;

    private String name;

    public static ProductResponseDto of(Product product, ModelMapper modelMapper) {
        return modelMapper.map(product, ProductResponseDto.class);
    }
}
