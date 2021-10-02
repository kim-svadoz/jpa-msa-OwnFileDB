package sunghyun.server.fileDBWork.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sunghyun.server.fileDBWork.config.CollectionConfig;
import sunghyun.server.fileDBWork.domain.dto.ProductCreateRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductListResponseDto;
import sunghyun.server.fileDBWork.domain.dto.ProductRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductResponseDto;
import sunghyun.server.fileDBWork.exception.ProductNotFoundException;
import sunghyun.server.fileDBWork.repository.FileProductRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProductService {
    private final HashMap<Long, Long> keyMap;

    private final File f;
    //private final RandomAccessFile raf;

    /*
     * File 조회
     */
    public ProductResponseDto read(Long id) throws IOException {
        String pNum = id + "";
        String path = f.getPath() + "/" + pNum;
        File findFile = new File(path);
        if (!findFile.exists()) {
            throw new ProductNotFoundException(String.format("ID[%s] not found", id));
        }

        RandomAccessFile raf = new RandomAccessFile(findFile+"/name.txt", "r");
        if (raf.length() == 0) {
            throw new ProductNotFoundException(String.format("ID[%s] not found", id));
        }

        raf.seek(0);
        String pName = raf.readLine();

        return ProductResponseDto.builder()
                .id(id)
                .name(pName).build();
    }

    /*
     * File 생성
     */
    public ProductResponseDto create(ProductCreateRequestDto requestDto) throws IOException {
        String name = requestDto.getName();
        String[] list = f.list();

        String pNum = list.length == 0 ? 1 + "" : (Integer.parseInt(list[list.length - 1]) + 1) + "";
        String productPath = f.getPath() + "/" + pNum;
        File productFolder = new File(productPath);
        if (!productFolder.exists()) {
            productFolder.mkdir();
        }

        RandomAccessFile raf = new RandomAccessFile(productFolder+"/name.txt", "rw");
        raf.write(name.getBytes());

        return ProductResponseDto.builder()
                .id(Long.parseLong(pNum))
                .name(requestDto.getName()).build();
    }

    /*
     * File 수정
     */
    public ProductResponseDto update(ProductRequestDto requestDto) throws IOException {
        long id = requestDto.getId();
        String name = requestDto.getName();
        String pNum = requestDto.getId() + "";
        String path = f.getPath() + "/" + pNum;
        File findFile = new File(path);
        if (!findFile.exists()) {
            throw new ProductNotFoundException(String.format("ID[%s] not found", id));
        }

        RandomAccessFile raf = new RandomAccessFile(findFile+"/name.txt", "rw");
        if (raf.length() == 0) {
            throw new ProductNotFoundException(String.format("ID[%s] not found", id));
        }

        raf.setLength(0);
        raf.seek(0);
        raf.setLength(name.length());
        raf.write(name.getBytes());

        return ProductResponseDto.builder()
                .id(id)
                .name(name).build();
    }

    /*
     * File 삭제
     */
    public HttpStatus delete(Long id) throws IOException {
        String pNum = id + "";
        String path = f.getPath() + "/" + pNum;
        File findFile = new File(path);
        if (!findFile.exists()) {
            throw new ProductNotFoundException(String.format("ID[%s] not found", id));
        }

        RandomAccessFile raf = new RandomAccessFile(findFile+"/name.txt", "rw");
        raf.setLength(0);

        return HttpStatus.OK;
    }

    /*
     * File 전체 List 조회
     */
    public ProductListResponseDto getProductList() throws IOException {
        String[] flist = f.list();

        RandomAccessFile raf;
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for (String num : flist) {
            String pNum = num + "";
            String path = f.getPath() + "/" + pNum;
            File findFile = new File(path);

            raf = new RandomAccessFile(findFile+"/name.txt", "r");
            raf.seek(0);
            long id = Long.parseLong(num);
            String name = raf.readLine();
            if (name == null || name.length() == 0) continue;

            productResponseDtos.add(new ProductResponseDto(id, name));
        }

        return ProductListResponseDto.builder()
                .list(productResponseDtos).build();
    }


    /*
    TODO:
         익셉션이 BEAN 생성할때 떨어지는지 호출할때 떨어지는지 확인
         익셉션 시 파일 없다는 로그 추가
         FILE 폴더 없을 때 새로운 폴더 만들도록
    public ProductResponseDto create(ProductRequestDto requestDto) throws IOException {
        write2File(requestDto);

        keyMap.forEach((aLong, aLong2) -> System.out.println(aLong + " : " + aLong2+ "total size >>>>>>>>>>>>>>>>>>>>>> "+keyMap.size()));

        return ProductResponseDto.builder().name("temp").build();
    }
    public void write2File(ProductRequestDto requestDto) {
        List<ProductRequestDto> list = new ArrayList<>();
        list.add(requestDto);

        try {
            for (int i = 0; i < list.size(); i++) {
                long idx = keyMap.size() + 1;

                raf.write((idx + "").getBytes());
                keyMap.put(idx, raf.length());
                raf.write((list.get(i).getName()).getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

}
