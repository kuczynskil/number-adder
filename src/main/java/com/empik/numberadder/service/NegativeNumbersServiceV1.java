package com.empik.numberadder.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NegativeNumbersServiceV1 {
    private final DelimitersServiceV1 delimitersServiceV1;

    public NegativeNumbersServiceV1(DelimitersServiceV1 delimitersServiceV1) {
        this.delimitersServiceV1 = delimitersServiceV1;
    }

    public int[] getNegativeNumbers(String numbers) {
        if (numbers.isEmpty()) return new int[0];
        numbers = handleCustomDelimitersWithMinusSign(numbers);

        StringBuilder sb = new StringBuilder();
        appendNegativeNums(numbers, sb);

        if (sb.length() == 0) return new int[0];
        return Arrays.stream(sb.toString().split(","))
                .mapToInt(Integer::parseInt)
                .filter(x -> x < 0)
                .toArray();
    }

    private String handleCustomDelimitersWithMinusSign(String numbers) {
        if (numbers.charAt(0) == '/') {
            String[] delimiters = delimitersServiceV1.extractDelimiters(numbers).split("\\|");

            for (String delimiter : delimiters) {
                if (delimiter.contains("-")) {
                    numbers = numbers.replace(delimiter.replace("\\", ""), ",");
                }
            }
        }
        return numbers;
    }

    private void appendNegativeNums(String numbers, StringBuilder sb) {
        for (int i = 0; i < numbers.length() - 1; i++) {
            if (numbers.charAt(i) == '-' && Character.isDigit(numbers.charAt(i + 1))) {
                sb.append(numbers.charAt(i));
                while (i + 1 < numbers.length() && Character.isDigit(numbers.charAt(i + 1))) {
                    sb.append(numbers.charAt(i + 1));
                    i++;
                }
                sb.append(",");
            }
        }
    }
}
