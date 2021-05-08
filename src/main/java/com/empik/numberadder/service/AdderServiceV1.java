package com.empik.numberadder.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdderServiceV1 implements AdderService {

    private static final Set<Character> SPECIAL_CHARACTERS = new HashSet<>(Arrays.asList(
            '<', '(', '[', '{', '\\', '^', '-', '=', '$', '!', '|', ']', '}', ')', '?', '*', '+', '.', '>'));

    private static final Map<Integer, Integer> resultOccurrence = new HashMap<>();

    @Override
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            setResultOccurrence(0);
            return 0;
        }

        numbers = handleInputWithNotNumericLastElement(numbers);
        String[] numbersDelimiters = handleDelimiters(numbers);
        numbers = numbersDelimiters[0];
        String delimiters = numbersDelimiters[1];

        int[] nums = Arrays.stream(numbers.split(delimiters))
                .mapToInt(Integer::parseInt)
                .filter(num -> num <= 1000)
                .toArray();

        int res = Arrays.stream(nums).sum();
        setResultOccurrence(res);

        return res;
    }

    private String[] handleDelimiters(String numbers) {
        String delimiters = ",";

        if (numbers.charAt(0) == '/') {
            delimiters = extractDelimiters(numbers);
            numbers = numbers.substring(firstDigitIndex(numbers));
        } else numbers = numbers.replace("\n", ",");

        numbers = numbers.replaceAll("\\s+", "");

        return new String[]{numbers, delimiters};
    }

    private String handleInputWithNotNumericLastElement(String numbers) {
        numbers = numbers.trim();
        if (!Character.isDigit(numbers.charAt(numbers.length() - 1))) {
            throw new NumberFormatException("Last element is not a number");
        }
        return numbers;
    }

    private void setResultOccurrence(int res) {
        if (resultOccurrence.containsKey(res)) {
            resultOccurrence.put(res, resultOccurrence.get(res) + 1);
        } else resultOccurrence.put(res, 1);
    }

    public int getResultOccurrence(int res) {
        return resultOccurrence.get(res);
    }


    private int firstDigitIndex(String numbers) {
        for (int i = 0; i < numbers.length(); i++) {
            if (Character.isDigit(numbers.charAt(i))) return i;
        }
        return -1;
    }

    private String extractDelimiters(String numbers) {
        if (numbers.contains("[") && numbers.contains("]")) {
            String delimiters = numbers.substring(3, numbers.lastIndexOf("]"));
            String[] delimitersArr = delimiters.split("]\\[");
            handleSpecialCharacters(delimitersArr);

            return String.join("|", delimitersArr);
        }

        char delimiter = numbers.charAt(2);
        return SPECIAL_CHARACTERS.contains(delimiter) ? "\\" + delimiter : String.valueOf(delimiter);
    }

    private void handleSpecialCharacters(String[] delimitersArr) {
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
            String[] delimiters = extractDelimiters(numbers).split("\\|");

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


    public static void main(String[] args) {
        String str4 = "-400-700-2-99;abc";
//        String str = "//[abc][..]1000..20";
//        String str2 = "//[abc][..]1000..20";
//        String str3 = "//[abc][..]100..20";
//        System.out.println(new AdderServiceV1().add(str));
//        System.out.println(new AdderServiceV1().add(str2));
//        System.out.println(new AdderServiceV1().add(str3));
        System.out.println(Arrays.toString(new AdderServiceV1().getNegativeNumbers(str4)));
//        resultOccurrence.entrySet().forEach(System.out::println);
    }
}
