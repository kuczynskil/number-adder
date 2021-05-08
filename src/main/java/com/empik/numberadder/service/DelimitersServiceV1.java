package com.empik.numberadder.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class DelimitersServiceV1 implements DelimitersService {
    private static final Set<Character> SPECIAL_CHARACTERS = new HashSet<>(Arrays.asList(
            '<', '(', '[', '{', '\\', '^', '-', '=', '$', '!', '|', ']', '}', ')', '?', '*', '+', '.', '>'));

    @Override
    public String extractDelimiters(String numbers) {
        if (numbers.contains("[") && numbers.contains("]")) {
            String delimiters = numbers.substring(3, numbers.lastIndexOf("]"));
            String[] delimitersArr = delimiters.split("]\\[");
            handleSpecialCharacters(delimitersArr);

            return String.join("|", delimitersArr);
        }

        char delimiter = numbers.charAt(2);
        return SPECIAL_CHARACTERS.contains(delimiter) ? "\\" + delimiter : String.valueOf(delimiter);
    }

    @Override
    public void handleSpecialCharacters(String[] delimitersArr) {
        for (int i = 0; i < delimitersArr.length; i++) {
            String[] delimiterChars = delimitersArr[i].split("");

            for (int k = 0; k < delimiterChars.length; k++) {
                String tested = delimiterChars[k];

                if (SPECIAL_CHARACTERS.contains(tested.charAt(0))) {
                    delimiterChars[k] = "\\" + tested;
                }
            }
            delimitersArr[i] = String.join("", delimiterChars);
        }
    }
}
