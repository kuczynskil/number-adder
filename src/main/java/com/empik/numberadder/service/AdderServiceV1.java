package com.empik.numberadder.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdderServiceV1 implements AdderService {

    @Override
    public int add(String numbers) {
        if (numbers.isEmpty()) return 0;
        if (!Character.isDigit(numbers.charAt(numbers.length() - 1))) return -1;
        String delimiters = ",";

        numbers = numbers.replace("\n", ",");
        numbers = numbers.replaceAll("\\s+", "");
        return Arrays.stream(numbers.split(",")).mapToInt(Integer::parseInt).sum();
    }

    public static void main(String[] args) {
        String str = "1,\n";
        System.out.println(new AdderServiceV1().add(str));
    }
}
