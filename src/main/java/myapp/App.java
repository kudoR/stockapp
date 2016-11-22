package myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableScheduling
public class App {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class);
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

}