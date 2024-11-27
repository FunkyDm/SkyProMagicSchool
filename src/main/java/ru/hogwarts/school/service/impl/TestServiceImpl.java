package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.TestService;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public List<Long> getTestSum(){
        long start = System.currentTimeMillis();
        long sum = 0;
        sum = IntStream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        System.out.println();
        return List.of(System.currentTimeMillis() - start, sum);
    }

}
