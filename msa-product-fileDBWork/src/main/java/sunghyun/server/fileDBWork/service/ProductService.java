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
import sunghyun.server.fileDBWork.domain.dto.ProductListResponseDto;
import sunghyun.server.fileDBWork.domain.dto.ProductRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductResponseDto;
import sunghyun.server.fileDBWork.exception.ProductNotFoundException;
import sunghyun.server.fileDBWork.repository.ProductRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductResponseDto read(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        byId.orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", id)));

        return ProductResponseDto.of(byId.get(), modelMapper);
    }

    public List<Product> getProductListPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
        Page page = productRepository.findAll(pageable);

        return page.getContent();
    }

    public ProductResponseDto create(ProductCreateRequestDto productCreateRequestDto) {
        Product product = ProductCreateRequestDto.toEntity(productCreateRequestDto, modelMapper);
        productRepository.save(product);

        return ProductResponseDto.of(product, modelMapper);
    }

    public ProductResponseDto update(ProductRequestDto productRequestDto) {
        Optional<Product> updateProduct = productRepository.findById(productRequestDto.getId());
        updateProduct.orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", productRequestDto.getId())));

        Product product = updateProduct.get();
        product.setName(productRequestDto.getName());
        productRepository.save(product);

        return ProductResponseDto.of(product, modelMapper);
    }

    public HttpStatus delete(Long id) {
        Optional<Product> deleteProduct = productRepository.findById(id);
        deleteProduct.orElseThrow(() -> new ProductNotFoundException(String.format("ID[%s] not found", id)));

        productRepository.deleteById(deleteProduct.get().getId());

        return HttpStatus.OK;
    }

    public ProductListResponseDto getProductList() {
        List<Product> productList = productRepository.findAll();

        return ProductListResponseDto.builder()
                .list(productList.stream().map(product ->
                        ProductResponseDto.builder()
                                .id(product.getId())
                                .name(product.getName()).build())
                        .collect(Collectors.toList())).build();
    }

    public ProductListResponseDto getProductListByIds(List<Long> list) {
        List<Product> byProductIds = productRepository.findByProductIds(list);

        return ProductListResponseDto.builder()
                .list(byProductIds.stream().map(product ->
                        ProductResponseDto.builder()
                                .id(product.getId())
                                .name(product.getName()).build())
                        .collect(Collectors.toList())).build();
    }
}
