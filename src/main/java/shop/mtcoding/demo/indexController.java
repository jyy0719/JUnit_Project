package shop.mtcoding.demo;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
public class indexController {

    @GetMapping(value = "/index")
    public ResponseEntity<?> index() {
        log.info("인포");
        log.debug("디버그");
        log.warn("경고");
        log.error("에러");

        return new ResponseEntity<>(HttpStatus.CREATED);
        // return new ResponseEntity<>(HttpStatus.OK);
    }

}