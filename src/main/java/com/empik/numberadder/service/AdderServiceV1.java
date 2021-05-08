package com.empik.numberadder.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdderServiceV1 implements AdderService {

    private static final Set<Character> SPECIAL_CHARACTERS = new HashSet<>(Arrays.asList(
            '<', '(', '[', '{', '\\', '^', '-', '=', '$', '!', '|', ']', '}', ')', '?', '*', '+', '.', '>'));

    static Map<Integer, Integer> resultOccurrence = new HashMap<>();

    @Override
    public int add(String numbers) {
        if (numbers.isEmpty()) return 0;

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

        int res = Arrays.stream(nums).sum();

        if (resultOccurrence.containsKey(res)) {
            resultOccurrence.put(res, resultOccurrence.get(res) + 1);
        } else resultOccurrence.put(res, 1);

        return res;
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
            String[] delimitersArr = delimiters.split("]\\[");

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

            return String.join("|", delimitersArr);
        }

        char delimiter = numbers.charAt(2);
        if (SPECIAL_CHARACTERS.contains(delimiter)) {
            return "\\" + delimiter;
        }
        return String.valueOf(delimiter);
    }

    public int[] getNegativeNumbers(String numbers) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numbers.length(); i++) {
            if (numbers.charAt(i) == '-') {
                sb.append(numbers.charAt(i));
                while (i + 1 < numbers.length() && Character.isDigit(numbers.charAt(i + 1))) {
                    sb.append(numbers.charAt(i + 1));
                    i++;
                }
                sb.append(",");
            }
        }

        if (sb.length() == 0) return new int[0];
        return Arrays.stream(sb.toString().split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
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
