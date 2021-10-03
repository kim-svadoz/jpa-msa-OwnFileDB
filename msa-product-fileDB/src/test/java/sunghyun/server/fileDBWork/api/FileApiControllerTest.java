package sunghyun.server.fileDBWork.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sunghyun.server.fileDBWork.domain.dto.ProductRequestDto;

import java.io.IOException;
import java.io.RandomAccessFile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileApiControllerTest {

    @Test
    public void test() throws IOException {
        //given
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setId((long) 1);
        productRequestDto.setName("book");

        RandomAccessFile raf = new RandomAccessFile("raf_file.dat", "rw");

        raf.writeInt(10);
        raf.writeChar('C');
        raf.writeLong(1010L);
        raf.writeByte(8);

        raf.seek(0);

        //when
        System.out.println(raf.readInt());
        System.out.println(raf.readChar());
        System.out.println(raf.readLong());
        System.out.println(raf.readByte());


        //ClassPathResource resource = new ClassPathResource("file/test.txt");
        //System.out.println(resource.getFilename());
    }
}