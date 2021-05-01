package com.example.alfred;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class TestController {

    @PostMapping("/v1/user/{userId}/data/submit")
    public ResponseEntity<String> postData(@RequestBody DataDTO requestBody,
                                           @PathVariable("userId") String userId, @RequestHeader("Content-Type") String head) throws Exception {
        return new ResponseEntity<String>("Done", HttpStatus.CREATED);
    }

//    @GetMapping("/v1/user/{userId}/data")
//    public ResponseEntity<TestModel> getData(@PathVariable("userId") String userId, @RequestHeader("header") String head) throws Exception {
//        return new ResponseEntity<TestModel>(tst.getData(userId), HttpStatus.OK);
//    }
}