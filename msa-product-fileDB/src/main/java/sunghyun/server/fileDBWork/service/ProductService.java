package sunghyun.server.fileDBWork.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sunghyun.server.fileDBWork.domain.Product;
import sunghyun.server.fileDBWork.domain.dto.ProductCreateRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductListResponseDto;
import sunghyun.server.fileDBWork.domain.dto.ProductRequestDto;
import sunghyun.server.fileDBWork.domain.dto.ProductResponseDto;
import sunghyun.server.fileDBWork.domain.vo.CustomFile;
import sunghyun.server.fileDBWork.exception.ProductNotFoundException;
import sunghyun.server.fileDBWork.repository.ProductRepository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final HashMap<Long, Long> keyMap;

    private final File f;
    private final ProductRepository productRepository;
    //private final RandomAccessFile raf;

    /*
     * File ์กฐํ
     */
    public ProductResponseDto read(Long id) throws IOException {
        File folder = productRepository.findFolderById(id);
        if (!folder.exists()) {
            throw new ProductNotFoundException(String.format("ID[%s] not found", id));
        }

        RandomAccessFile raf = new RandomAccessFile(folder+"/name.txt", "r");
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
     * File ์์ฑ
     */
    public ProductResponseDto create(ProductCreateRequestDto requestDto) throws IOException {
        String name = requestDto.getName();

        CustomFile customFile = productRepository.createNextDir();
        RandomAccessFile raf = new RandomAccessFile(customFile.getFile()+"/name.txt", "rw");
        raf.write(name.getBytes());

        return ProductResponseDto.builder()
                .id(customFile.getIndex())
                .name(requestDto.getName()).build();
    }

    /*
     * File ์์?
     */
    public ProductResponseDto update(ProductRequestDto requestDto) throws IOException {
        long id = requestDto.getId();
        String name = requestDto.getName();

        File folder = productRepository.findFolderById(id);
        if (!folder.exists()) {
            throw new ProductNotFoundException(String.format("ID[%s] not found", id));
        }

        RandomAccessFile raf = new RandomAccessFile(folder+"/name.txt", "rw");
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
     * File ์ญ์?
     */
    public HttpStatus delete(Long id) throws IOException {
        File folder = productRepository.findFolderById(id);
        if (!folder.exists()) {
            throw new ProductNotFoundException(String.format("ID[%s] not found", id));
        }

        RandomAccessFile raf = new RandomAccessFile(folder+"/name.txt", "rw");
        if (raf.length() == 0) {
            throw new ProductNotFoundException(String.format("ID[%s] not found", id));
        }

        raf.setLength(0);

        return HttpStatus.OK;
    }

    /*
     * File ์?์ฒด List ์กฐํ
     */
    public ProductListResponseDto getProductList() throws IOException {
        String[] flist = f.list();

        RandomAccessFile raf;
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for (String num : flist) {
            long id = Long.parseLong(num);

            File folder = productRepository.findFolderById(id);
            if (!folder.exists()) {
                throw new ProductNotFoundException(String.format("ID[%s] not found", id));
            }

            raf = new RandomAccessFile(folder+"/name.txt", "r");
            raf.seek(0);

            String name = raf.readLine();
            if (name == null || name.length() == 0) continue;

            productResponseDtos.add(new ProductResponseDto(id, name));
        }
        Collections.sort(productResponseDtos, new Comparator<ProductResponseDto>() {
            @Override
            public int compare(ProductResponseDto o1, ProductResponseDto o2) {
                if (o1.getId() - o2.getId() > 0) {
                    return 1;
                }
                return -1;
            }
        });

        return ProductListResponseDto.builder()
                .list(productResponseDtos).build();
    }

    public ProductListResponseDto getProductListByIds(List<Long> list) throws IOException {
        RandomAccessFile raf;
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for (long id : list) {
            File folder = productRepository.findFolderById(id);
            if (!folder.exists()) {
                throw new ProductNotFoundException(String.format("ID[%s] not found", id));
            }

            raf = new RandomAccessFile(folder+"/name.txt", "r");
            raf.seek(0);

            String name = raf.readLine();
            if (name == null || name.length() == 0) continue;

            productResponseDtos.add(new ProductResponseDto(id, name));
        }
        Collections.sort(productResponseDtos, new Comparator<ProductResponseDto>() {
            @Override
            public int compare(ProductResponseDto o1, ProductResponseDto o2) {
                if (o1.getId() - o2.getId() > 0) {
                    return 1;
                }
                return -1;
            }
        });

        return ProductListResponseDto.builder()
                .list(productResponseDtos).build();
    }

    /*
    TODO:
         ์ต์์์ด BEAN ์์ฑํ?๋ ๋จ์ด์ง๋์ง ํธ์ถํ?๋ ๋จ์ด์ง๋์ง ํ์ธ
         ์ต์์ ์ ํ์ผ ์๋ค๋ ๋ก๊ทธ ์ถ๊ฐ
         FILE ํด๋ ์์ ๋ ์๋ก์ด ํด๋ ๋ง๋ค๋๋ก
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
