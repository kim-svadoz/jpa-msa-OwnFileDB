package sunghyun.server.fileDBWork.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    // GET
    // /hello (endpoint)
    // 이전방식으로는 @RequestMapping(method = RequestMethod.GET, path="/hello") 을 사용할 수 가 있다.
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    // alt + enter
    @GetMapping("/hello-bean")
    public HelloBean helloBean() {
        return new HelloBean("Hello!!");
    }

    // {name}과 String xx가 다른 값이라면 (value = "name")을 맵핑해야한다.
    // 여기서 영어 name은 가능하지만, 한글은 404 Error가 발생한다?
    // why ->
    @GetMapping("/hello-bean/path-variable/{name}")
    public HelloBean helloBean(@PathVariable String name) {
        return new HelloBean(String.format("Hello World, %s", name));
    }
}
