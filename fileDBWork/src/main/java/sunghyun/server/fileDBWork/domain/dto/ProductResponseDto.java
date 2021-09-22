package sunghyun.server.fileDBWork.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import sunghyun.server.fileDBWork.domain.Product;

@Data
//@Accessors(chain = true)
@Builder
public class ProductResponseDto {
    private Long id;

    private String name;
}
