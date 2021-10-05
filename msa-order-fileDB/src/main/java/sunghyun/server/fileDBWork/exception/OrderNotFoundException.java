package sunghyun.server.fileDBWork.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
