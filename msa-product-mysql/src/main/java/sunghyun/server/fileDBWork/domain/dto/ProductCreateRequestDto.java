package sunghyun.server.fileDBWork.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import sunghyun.server.fileDBWork.domain.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequestDto {
    private String name;

    public static Product toEntity(ProductCreateRequestDto requestDto, ModelMapper modelMapper) {
        return modelMapper.map(requestDto, Product.class);
    }
}