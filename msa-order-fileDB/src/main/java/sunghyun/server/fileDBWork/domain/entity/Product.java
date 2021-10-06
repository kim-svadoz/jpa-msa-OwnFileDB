package sunghyun.server.fileDBWork.domain.entity;

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
@Builder
@ApiModel(description = "제품 상세 정보를 위한 도메인 객체")
public class Product implements Serializable {

    private Long id;

    private String name;
}