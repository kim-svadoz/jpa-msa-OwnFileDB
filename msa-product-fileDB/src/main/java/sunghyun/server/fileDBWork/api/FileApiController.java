package sunghyun.server.fileDBWork.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunghyun.server.fileDBWork.domain.dto.ProductCreateRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductListResponseDto;
import sunghyun.server.fileDBWork.domain.dto.ProductRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductResponseDto;
import sunghyun.server.fileDBWork.service.ProductService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileApiController {

    private final ProductService fileProductService;

    /*
     * File 조회
     */
    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDto> read(Long id) throws IOException {
        ProductResponseDto readDto = fileProductService.read(id);
        return new ResponseEntity<ProductResponseDto>(readDto, HttpStatus.OK);
    }

    /*
     * File 생성
     */
    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductCreateRequestDto requestDto) throws IOException {
        ProductResponseDto createDto = fileProductService.create(requestDto);
        return new ResponseEntity<ProductResponseDto>(createDto, HttpStatus.OK);
    }

    /*
     * File 수정
     */
    @PutMapping
    public ResponseEntity<ProductResponseDto> update(@RequestBody ProductRequestDto requestDto) throws IOException {
        ProductResponseDto updateDto = fileProductService.update(requestDto);
        return new ResponseEntity<ProductResponseDto>(updateDto, HttpStatus.OK);
    }

    /*
     * File 삭제
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(Long id) throws IOException {
        HttpStatus status = fileProductService.delete(id);
        return new ResponseEntity<HttpStatus>(status);
    }

    /*
     * File 전체 목록 조회
     */
    @GetMapping("/lists")
    public ResponseEntity<ProductListResponseDto> getProductList() throws IOException {
        ProductListResponseDto productList = fileProductService.getProductList();
        return new ResponseEntity<ProductListResponseDto>(productList, HttpStatus.OK);
    }

    /*
     * OrderApplication에서 사용 - 클라이언트가 요청한 id에 대한 상품 조회
     */
    @GetMapping("/list")
    public ResponseEntity<ProductListResponseDto> getProductListByIds(@RequestParam("ids") String ids) throws IOException {
        List<Long> productList = new ArrayList<>(Arrays.asList(ids.split(",")))
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        ProductListResponseDto productListByIds = fileProductService.getProductListByIds(productList);
        return new ResponseEntity<ProductListResponseDto>(productListByIds, HttpStatus.OK);
    }
}
