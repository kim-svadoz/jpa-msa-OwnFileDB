package sunghyun.server.fileDBWork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sunghyun.server.fileDBWork.domain.Product;
import sunghyun.server.fileDBWork.domain.dto.ProductRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductResponseDto;
import sunghyun.server.fileDBWork.exception.ProductNotFoundException;
import sunghyun.server.fileDBWork.repository.ProductRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<ProductResponseDto> findById(Long id) {
        Optional<Product> findProduct = productRepository.findById(id);
        return findProduct.map(product -> ResponseEntity
                .status(HttpStatus.OK)
                .body(ProductResponseDto.builder()
                        .id(product.getId())
                        .name(product.getName()).build()))
                .orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", id)));
    }

    public ResponseEntity<List<Product>> getProductListPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
        Page page = productRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(page.getContent());

        /*
        if (productDtoPage.hasContent()) {
            List<Product> productList = productDtoPage.getContent();
            final int start = (int)paging.getOffset();
            final int end = Math.min((start + paging.getPageSize()), productList.size());
            final Page<Product> page = new PageImpl<>(productList.subList(start, end), paging, productList.size());

            return page.getContent().stream().
                    map(product -> ResponseEntity
                            .status(HttpStatus.OK)
                            .body(ProductDto.builder().build())).collect(Collectors.toList());
        }
         */
    }

    public ResponseEntity<ProductResponseDto> create(ProductRequestDto product) {
        Product newProduct = productRepository.save(Product.builder()
                .name(product.getName()).build());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductResponseDto.builder()
                        .id(newProduct.getId())
                        .name(newProduct.getName()).build());
    }


    public ResponseEntity<ProductResponseDto> update(ProductRequestDto request) {
        Optional<Product> updateProduct = productRepository.findById(request.getId());
        return updateProduct.map(product -> {
            product.setName(request.getName());
            productRepository.save(product);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ProductResponseDto.builder()
                        .id(product.getId())
                        .name(product.getName()).build());

        }).orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", request.getId())));
    }

    public ResponseEntity<HttpStatus> delete(Long id) {
        Optional<Product> deleteProduct = productRepository.findById(id);

        deleteProduct.orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", id)));

        productRepository.deleteById(deleteProduct.get().getId());
        return ResponseEntity.ok().build();
    }
}
