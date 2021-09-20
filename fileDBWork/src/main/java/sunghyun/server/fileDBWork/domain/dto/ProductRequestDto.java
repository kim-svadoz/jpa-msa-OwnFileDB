package sunghyun.server.fileDBWork.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
public class ProductRequestDto {
    private Long id;

    private String name;
}