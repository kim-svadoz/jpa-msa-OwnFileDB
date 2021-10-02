package sunghyun.server.fileDBWork.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
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

    /*
     * OrderApplication에서 사용 - 상품의 전체 목록 조회
     */
    @GetMapping("/lists")
    public ResponseEntity<ProductListResponseDto> getProductList() {
        ProductListResponseDto productList = productService.getProductList();
        return new ResponseEntity<ProductListResponseDto>(productList, HttpStatus.OK);
    }

    /*
     * OrderApplication에서 사용 - 클라이언트가 요청한 id에 대한 상품 조회
     */
    @GetMapping("/list")
    public ResponseEntity<ProductListResponseDto> getProductListByIds(@RequestParam("ids") String ids) {
        List<Long> productList = new ArrayList<>(Arrays.asList(ids.split(",")))
                    .stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

        ProductListResponseDto productListByIds = productService.getProductListByIds(productList);
        return new ResponseEntity<ProductListResponseDto>(productListByIds, HttpStatus.OK);
    }
}
