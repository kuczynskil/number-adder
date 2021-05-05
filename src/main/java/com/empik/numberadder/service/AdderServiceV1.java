package com.empik.numberadder.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdderServiceV1 implements AdderService {

    @Override
    public int add(String numbers) {
        if (numbers.isEmpty()) return 0;
        numbers = numbers.replaceAll(" ", "");
        return Arrays.stream(numbers.split(",")).mapToInt(Integer::parseInt).sum();
    }
}
