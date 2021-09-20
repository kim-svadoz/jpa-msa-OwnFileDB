package sunghyun.server.fileDBWork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sunghyun.server.fileDBWork.domain.Product;
import sunghyun.server.fileDBWork.exception.ProductNotFoundException;
import sunghyun.server.fileDBWork.repository.ProductRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<Product> findById(Long id) {
        Optional<Product> option = productRepository.findById(id);
        return option.map(product -> ResponseEntity
                .status(HttpStatus.OK)
                .body(Product.builder()
                        .id(product.getId())
                        .name(product.getName()).build()))
                .orElseThrow(ProductNotFoundException::new);
    }

    /*
    public ResponseEntity<Product> findByIds(List<Long> ids) {
        List<Optional<Product>> options = productRepository.findById(ids);
        if (options.size() != ids.size()) {
            throw new ProductNotFoundException();
        }
        return ResponseEntity.ok().body(Product.builder())
    }
    */

    public ResponseEntity<Product> create(Product product) {
        Product newProduct = productRepository.save(Product.builder()
                .name(product.getName()).build());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Product.builder()
                        .id(newProduct.getId())
                        .name(newProduct.getName()).build());
    }
}
