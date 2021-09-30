package sunghyun.server.fileDBWork.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sunghyun.server.fileDBWork.domain.Product;
import sunghyun.server.fileDBWork.domain.dto.ProductCreateRequestDto;
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

    private final ModelMapper modelMapper;

    public ResponseEntity<ProductResponseDto> findById(Long id) {
        Optional<Product> findProduct = productRepository.findById(id);
        return findProduct.map(product -> ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ProductResponseDto.of(product, modelMapper)))
                .orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", id)));
    }

    public ResponseEntity<List<Product>> getProductListPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
        Page page = productRepository.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(page.getContent());
    }

    public ResponseEntity<ProductResponseDto> create(ProductCreateRequestDto productCreateRequestDto) {
        Product newProduct = productRepository.save(ProductCreateRequestDto.toEntity(productCreateRequestDto, modelMapper));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductResponseDto.of(newProduct, modelMapper));
    }

    public ResponseEntity<ProductResponseDto> update(ProductRequestDto productRequestDto) {
        Optional<Product> updateProduct = productRepository.findById(productRequestDto.getId());
        return updateProduct.map(product -> {
            product.setName(productRequestDto.getName());
            productRepository.save(product);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ProductResponseDto.of(product, modelMapper));

        }).orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", productRequestDto.getId())));
    }

    public ResponseEntity<HttpStatus> delete(Long id) {
        Optional<Product> deleteProduct = productRepository.findById(id);

        deleteProduct.orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", id)));

        productRepository.deleteById(deleteProduct.get().getId());
        return ResponseEntity.ok().build();
    }
}
