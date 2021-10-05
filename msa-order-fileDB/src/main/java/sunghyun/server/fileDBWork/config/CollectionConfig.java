package sunghyun.server.fileDBWork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class CollectionConfig {

    @Bean
    public HashMap<Long, Long> keyMap() {
        return new HashMap<Long, Long>();
    }
}
