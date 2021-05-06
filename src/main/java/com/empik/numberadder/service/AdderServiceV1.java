package com.empik.numberadder.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class AdderServiceV1 implements AdderService {

    private static final Set<Character> SPECIAL_CHARACTERS = new HashSet<>(Arrays.asList(
            '<', '(', '[', '{', '\\', '^', '-', '=', '$', '!', '|', ']', '}', ')', '?', '*', '+', '.', '>'));


    @Override
    public int add(String numbers) {
        if (numbers.isEmpty()) return 0;

        numbers = numbers.trim();
        if (!Character.isDigit(numbers.charAt(numbers.length() - 1))) return -1;

        String delimiters = ",";
        if (numbers.charAt(0) == '/') {
            delimiters = extractDelimiters(numbers);
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

    private static String extractDelimiters(String numbers) {
        if (numbers.contains("[") && numbers.contains("]")) {
            String delimiters = numbers.substring(3, numbers.lastIndexOf("]"));
            String[] delimiterArr = delimiters.split("]\\[");

            for (int i = 0; i < delimiterArr.length; i++) {
                String[] delimiterChars = delimiterArr[i].split("");

                for (int k = 0; k < delimiterChars.length; k++) {
                    String tested = delimiterChars[k];

                    if (SPECIAL_CHARACTERS.contains(tested.charAt(0))) {
                        delimiterChars[k] = "\\" + tested;
                    }
                }
                delimiterArr[i] = String.join("", delimiterChars);
            }

            return String.join("|", delimiterArr);
        }

        char delimiter = numbers.charAt(2);
        if (SPECIAL_CHARACTERS.contains(delimiter)) {
            return  "\\" + delimiter;
        }
        return String.valueOf(delimiter);
    }


    public static void main(String[] args) {
        String str = "//[abc][..]1000..20";
        System.out.println(extractDelimiters(str));
//        System.out.println(new AdderServiceV1().add(str));
    }
}
