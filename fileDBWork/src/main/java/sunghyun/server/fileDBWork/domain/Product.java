package sunghyun.server.fileDBWork.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Accessors(chain = true)
@ApiModel(description = "제품 상세 정보를 위한 도메인 객체")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(notes = "제품 이름을 입력해 주세요.")
    private String name;
}