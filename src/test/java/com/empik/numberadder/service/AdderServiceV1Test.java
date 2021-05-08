package com.empik.numberadder.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AdderServiceV1Test {

    private final AdderServiceV1 adderServiceV1 = new AdderServiceV1();

    @Test
    void returns_0_for_empty_String() {
        assertEquals(0, adderServiceV1.add(""));
    }

    @Test
    void returns_sum_of_two_nums_separated_by_comma() {
        assertEquals(2, adderServiceV1.add("1,1"));
        assertEquals(20, adderServiceV1.add("1,19"));
        assertEquals(5, adderServiceV1.add("2,3"));
    }

    @Test
    void returns_sum_of_unknown_amount_of_numbers_separated_by_comma() {
        assertEquals(33, adderServiceV1.add("11,11,11"));
        assertEquals(1, adderServiceV1.add("1"));
        assertEquals(10, adderServiceV1.add("2,1,2,1,4"));
    }

    @Test
    void returns_sum_of_numbers_separated_by_comma_where_input_contains_whitespaces() {
        assertEquals(2, adderServiceV1.add("  1,1  "));
        assertEquals(20, adderServiceV1.add("1   ,   19"));
        assertEquals(5, adderServiceV1.add(" 2 , 3 "));

        assertEquals(33, adderServiceV1.add("  11 ,11,11   "));
        assertEquals(1, adderServiceV1.add(" 1      "));
        assertEquals(10, adderServiceV1.add("2 ,1 ,  2,1,4"));
    }

    @Test
    void returns_sum_of_numbers_separated_by_comma_or_a_new_line() {
        assertEquals(2, adderServiceV1.add("1\n1"));
        assertEquals(5, adderServiceV1.add("1,1\n3"));
        assertEquals(15, adderServiceV1.add("5\n8,2"));
        assertEquals(6, adderServiceV1.add("1\n1\n1\n1\n1\n1"));

    }

    @Test
    void returns_sum_of_numbers_separated_by_delimiter_passed_in_input_String() {
        assertEquals(3, adderServiceV1.add("//;\n1;2"));
        assertEquals(45, adderServiceV1.add("//,23,22"));
        assertEquals(30, adderServiceV1.add("//.\n10.20"));
        assertEquals(7, adderServiceV1.add("//B3B4"));
    }

    @Test
    void returns_sum_of_numbers_but_ommits_numbers_bigger_than_1000() {
        assertEquals(1020, adderServiceV1.add("//.\n1000.20"));
        assertEquals(20, adderServiceV1.add("1001,20"));
        assertEquals(5, adderServiceV1.add("5\n2000"));
        assertEquals(6, adderServiceV1.add("1,2,3,9000"));
    }

    @Test
    void returns_sum_of_numbers_separated_by_delimiter_longer_than_one_char() {
        assertEquals(30, adderServiceV1.add("//[abc]\n10abc20"));
        assertEquals(1020, adderServiceV1.add("//[..]1000..20"));
        assertEquals(75, adderServiceV1.add("//[___]\n50___25___9999"));
        assertEquals(1, adderServiceV1.add("//[;;]1;;2000;;7777"));
    }

    @Test
    void returns_sum_of_numbers_separated_by_more_than_one_custom_delimiter() {
        assertEquals(60, adderServiceV1.add("//[;;][X]\n10;;20X30"));
        assertEquals(11, adderServiceV1.add("//[f][!][.]\n1f5!4.1"));
        assertEquals(33, adderServiceV1.add("//[abc][d][z][s]\n10abc20d1z1s1"));
        assertEquals(55, adderServiceV1.add("//[--][++][_]\n10--20++10--10_5"));
    }
}
