package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TestServiceImplTest {

    private final TestServiceImpl testService = new TestServiceImpl();

    @Test
    void testGetTestSum() {
        List<Long> result = testService.getTestSum();

        long executionTime = result.get(0);
        long sum = result.get(1);

        int expectedSum = IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);

        assertEquals(expectedSum, sum);
        assertTrue(executionTime > 0);
    }
    
}