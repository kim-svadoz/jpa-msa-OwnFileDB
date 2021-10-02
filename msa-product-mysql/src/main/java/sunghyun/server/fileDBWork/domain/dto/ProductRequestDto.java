package sunghyun.server.fileDBWork.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import sunghyun.server.fileDBWork.domain.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    private Long id;

    private String name;

    public static Product toEntity(ProductRequestDto requestDto, ModelMapper modelMapper) {
        return modelMapper.map(requestDto, Product.class);
    }
}