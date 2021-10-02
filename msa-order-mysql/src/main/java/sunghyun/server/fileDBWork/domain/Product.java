package sunghyun.server.fileDBWork.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ApiModel(description = "제품 상세 정보를 위한 도메인 객체")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ApiModelProperty(notes = "제품 이름을 입력해 주세요.")
    @Column(name = "product_name")
    private String name;
}