package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.TestService;

import java.util.List;
import java.util.stream.Stream;

@RestController
public class TestController {

    TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/get/test/sum")
    public List<Long> getTestSum(){
        return testService.getTestSum();
    }

}
