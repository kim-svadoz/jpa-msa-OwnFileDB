package sunghyun.server.fileDBWork.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*
 * 예외처리를 하기 위해서 사용되는 자바 객체
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}
