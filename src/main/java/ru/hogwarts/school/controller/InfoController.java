package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port}")
    String serverPort;

    @GetMapping("/get/port")
    public ResponseEntity<String> getCurrentRunningApplicationPort(){
        return ResponseEntity.ok(serverPort);
    }

}
