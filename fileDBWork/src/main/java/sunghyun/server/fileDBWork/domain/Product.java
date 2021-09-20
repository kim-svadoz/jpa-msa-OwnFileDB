package sunghyun.server.fileDBWork.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Accessors(chain = true)
@Table(name="Product")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "productId")
    private Long id;

    @Column(name = "productName")
    private String name;
}