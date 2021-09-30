package sunghyun.server.fileDBWork.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunghyun.server.fileDBWork.domain.Product;
import sunghyun.server.fileDBWork.domain.dto.ProductCreateRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductListResponseDto;
import sunghyun.server.fileDBWork.domain.dto.ProductRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductResponseDto;
import sunghyun.server.fileDBWork.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductApiController {

    private final ProductService productService;

    /*
     * 특정 제품 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        ProductResponseDto responseDto = productService.read(id);
        return new ResponseEntity<ProductResponseDto>(responseDto, HttpStatus.OK);
    }

    /*
     * 전체목록 조회
     */
    @GetMapping
    public ResponseEntity<List<Product>> getProductsByPagination(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        List<Product> productListPage = productService.getProductListPage(pageNo, pageSize);
        return new ResponseEntity<List<Product>>(productListPage, HttpStatus.OK);
    }

    /*
     * 제품 추가
     */
    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductCreateRequestDto product) {
        ProductResponseDto responseDto = productService.create(product);
        return new ResponseEntity<ProductResponseDto>(responseDto, HttpStatus.CREATED);
    }

    /*
     * 제품 수정
     */
    @PutMapping
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto product) {
        ProductResponseDto responseDto = productService.update(product);
        return new ResponseEntity<ProductResponseDto>(responseDto, HttpStatus.OK);
    }

    /*
     * 제품 삭제
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        HttpStatus status = productService.delete(id);
        return new ResponseEntity<>(status);
    }
}
