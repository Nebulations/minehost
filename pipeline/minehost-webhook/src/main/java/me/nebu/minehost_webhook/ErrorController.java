package me.nebu.minehost_webhook;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController implements org.springframework.boot.webmvc.error.ErrorController {

//    @RequestMapping("/error")
//    public ResponseEntity<?> handleError() {
//        return ResponseEntity.status(403).build();
//    }

}
