package sunghyun.server.fileDBWork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunghyun.server.fileDBWork.domain.Product;
import sunghyun.server.fileDBWork.domain.dto.ProductRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductResponseDto;
import sunghyun.server.fileDBWork.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    /*
     * 전체목록 조회
     */
    @GetMapping
    public ResponseEntity<List<Product>> getProductsByPagination(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        return productService.getProductListPage(pageNo, pageSize);
    }

    /*
     * 제품 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    /*
     * 목록 추가
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto product) {
        return productService.create(product);
    }
}
