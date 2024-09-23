package su.tomcat.taskflow.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {

  @GetMapping("/test")
  @ResponseBody
  public ResponseEntity<String> testEndpoint() {
    return ResponseEntity.status(HttpStatus.OK).body("Test is Ok!");
  }
}
