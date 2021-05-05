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
}
