package com.empik.numberadder.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SumServiceV1Test {

    private final SumServiceV1 sumServiceV1 = new SumServiceV1(new DelimitersServiceV1(),
            new NegativeNumbersServiceV1(new DelimitersServiceV1()));

    @Test
    void returns_0_for_empty_String() {
        assertEquals(0, sumServiceV1.add(""));
    }

    @Test
    void returns_sum_of_two_nums_separated_by_comma() {
        assertEquals(2, sumServiceV1.add("1,1"));
        assertEquals(20, sumServiceV1.add("1,19"));
        assertEquals(5, sumServiceV1.add("2,3"));
    }

    @Test
    void returns_sum_of_unknown_amount_of_numbers_separated_by_comma() {
        assertEquals(33, sumServiceV1.add("11,11,11"));
        assertEquals(1, sumServiceV1.add("1"));
        assertEquals(10, sumServiceV1.add("2,1,2,1,4"));
    }

    @Test
    void returns_sum_of_numbers_separated_by_comma_where_input_contains_whitespaces() {
        assertEquals(2, sumServiceV1.add("  1,1  "));
        assertEquals(20, sumServiceV1.add("1   ,   19"));
        assertEquals(5, sumServiceV1.add(" 2 , 3 "));

        assertEquals(33, sumServiceV1.add("  11 ,11,11   "));
        assertEquals(1, sumServiceV1.add(" 1      "));
        assertEquals(10, sumServiceV1.add("2 ,1 ,  2,1,4"));
    }

    @Test
    void returns_sum_of_numbers_separated_by_comma_or_a_new_line() {
        assertEquals(2, sumServiceV1.add("1\n1"));
        assertEquals(5, sumServiceV1.add("1,1\n3"));
        assertEquals(15, sumServiceV1.add("5\n8,2"));
        assertEquals(6, sumServiceV1.add("1\n1\n1\n1\n1\n1"));

    }

    @Test
    void returns_sum_of_numbers_separated_by_delimiter_passed_in_input_String() {
        assertEquals(3, sumServiceV1.add("//;\n1;2"));
        assertEquals(45, sumServiceV1.add("//,23,22"));
        assertEquals(30, sumServiceV1.add("//.\n10.20"));
        assertEquals(7, sumServiceV1.add("//B3B4"));
    }

    @Test
    void returns_sum_of_numbers_but_ommits_numbers_bigger_than_1000() {
        assertEquals(1020, sumServiceV1.add("//.\n1000.20"));
        assertEquals(20, sumServiceV1.add("1001,20"));
        assertEquals(5, sumServiceV1.add("5\n2000"));
        assertEquals(6, sumServiceV1.add("1,2,3,9000"));
    }

    @Test
    void returns_sum_of_numbers_separated_by_delimiter_longer_than_one_char() {
        assertEquals(30, sumServiceV1.add("//[abc]\n10abc20"));
        assertEquals(1020, sumServiceV1.add("//[..]1000..20"));
        assertEquals(75, sumServiceV1.add("//[___]\n50___25___9999"));
        assertEquals(1, sumServiceV1.add("//[;;]1;;2000;;7777"));
    }

    @Test
    void returns_sum_of_numbers_separated_by_more_than_one_custom_delimiter() {
        assertEquals(60, sumServiceV1.add("//[;;][X]\n10;;20X30"));
        assertEquals(11, sumServiceV1.add("//[f][!][.]\n1f5!4.1"));
        assertEquals(33, sumServiceV1.add("//[abc][d][z][s]\n10abc20d1z1s1"));
        assertEquals(55, sumServiceV1.add("//[--][++][_]\n10--20++10--10_5"));
    }

    @Test
    void returns_negative_numbers_from_input_as_a_String() {
        assertArrayEquals(new int[]{-1, -30}, sumServiceV1.getNegativeNumbers("//[;;]1;;-1;;2;;-30"));
        assertArrayEquals(new int[]{-5, -2, -3}, sumServiceV1.getNegativeNumbers("5,-5,-2,-3,12"));
        assertArrayEquals(new int[]{-3}, sumServiceV1.getNegativeNumbers("//;\n-3"));

        assertArrayEquals(new int[]{-3}, sumServiceV1.getNegativeNumbers("-3,          -0,2"));
        assertArrayEquals(new int[]{-3, -2}, sumServiceV1.getNegativeNumbers("-3,0,-2"));
        assertArrayEquals(new int[]{-3}, sumServiceV1.getNegativeNumbers("-3,0,2,-"));
    }
}
