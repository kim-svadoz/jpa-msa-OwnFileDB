package sunghyun.server.fileDBWork.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sunghyun.server.fileDBWork.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @Test
    public void getProductList_테스트() throws Exception {
        //given
        String pIds = "123";
        List<Long> list = new ArrayList<>(Arrays.asList(pIds.split("")))
                    .stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

        //when


        //then
        assertEquals(list.size(), 3);
    }
}