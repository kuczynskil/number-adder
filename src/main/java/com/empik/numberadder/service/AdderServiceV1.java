package com.empik.numberadder.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdderServiceV1 implements AdderService {

    private final DelimitersServiceV1 delimitersServiceV1;
    private final NegativeNumbersServiceV1 negativeNumbersServiceV1;

    private static final Map<Integer, Integer> resultOccurrence = new HashMap<>();

    public AdderServiceV1(DelimitersServiceV1 delimitersServiceV1, NegativeNumbersServiceV1 negativeNumbersServiceV1) {
        this.delimitersServiceV1 = delimitersServiceV1;
        this.negativeNumbersServiceV1 = negativeNumbersServiceV1;
    }

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
            delimiters = delimitersServiceV1.extractDelimiters(numbers);
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

    public int[] getNegativeNumbers(String numbers) {
        return negativeNumbersServiceV1.getNegativeNumbers(numbers);
    }




    public static void main(String[] args) {
        String str4 = "-400-700-2-99;abc";
//        String str = "//[abc][..]1000..20";
//        String str2 = "//[abc][..]1000..20";
//        String str3 = "//[abc][..]100..20";
//        System.out.println(new AdderServiceV1().add(str));
//        System.out.println(new AdderServiceV1().add(str2));
//        System.out.println(new AdderServiceV1().add(str3));
//        System.out.println(Arrays.toString(new AdderServiceV1(delimitersService_v1).getNegativeNumbers(str4)));
//        resultOccurrence.entrySet().forEach(System.out::println);
    }
}
