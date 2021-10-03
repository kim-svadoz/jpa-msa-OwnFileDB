package sunghyun.server.fileDBWork.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomFile {
    File file;
    long index;
}
