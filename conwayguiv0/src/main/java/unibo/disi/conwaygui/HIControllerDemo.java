package unibo.disi.conwaygui;

 

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller //ANNOTAZIONE IMPORTANTE
//@RestController //is @Controller + @ResponseBody

public class HIControllerDemo {
@Value("${spring.application.name}")
String appName;

@GetMapping("/")
public String homePage(Model model) {
    model.addAttribute("arg", appName);
    return "welcome";
}


@ExceptionHandler
public ResponseEntity<String> handle(Exception ex) {
        //HttpHeaders responseHeaders = new HttpHeaders();
    return new ResponseEntity<String>(
          "HIControllerDemo ERROR " + ex.getMessage(),
          HttpStatus.CREATED);
    }

}