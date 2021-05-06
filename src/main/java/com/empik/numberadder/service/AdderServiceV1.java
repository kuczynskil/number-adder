package com.empik.numberadder.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdderServiceV1 implements AdderService {

    private static final char[] SPECIAL_CHARACTERS =
            {'<', '(', '[', '{', '\\', '^', '-', '=', '$', '!', '|', ']', '}', ')', '?', '*', '+', '.', '>'};

    @Override
    public int add(String numbers) {
        if (numbers.isEmpty()) return 0;

        numbers = numbers.trim();
        if (!Character.isDigit(numbers.charAt(numbers.length() - 1))) return -1;

        String delimiters = ",";
        if (numbers.charAt(0) == '/') {
            delimiters = extractDelimiter(numbers);
            numbers = numbers.substring(firstDigitIndex(numbers));
        } else numbers = numbers.replace("\n", ",");

        numbers = numbers.replaceAll("\\s+", "");
        int[] nums = Arrays.stream(numbers.split(delimiters))
                .mapToInt(Integer::parseInt)
                .filter(num -> num <= 1000)
                .toArray();

        return Arrays.stream(nums).sum();
    }


    private static int firstDigitIndex(String numbers) {
        for (int i = 0; i < numbers.length(); i++) {
            if (Character.isDigit(numbers.charAt(i))) return i;
        }
        return -1;
    }

    private static String extractDelimiter(String numbers) {
        char delimiter = numbers.charAt(2);
        for (char c : SPECIAL_CHARACTERS) {
            if (delimiter == c) {
                return "\\" + numbers.charAt(2);
            }
        }
        return String.valueOf(delimiter);
    }


    public static void main(String[] args) {
        String str = "//.1.1";
        System.out.println(extractDelimiter(str));
        System.out.println(new AdderServiceV1().add(str));
    }
}
