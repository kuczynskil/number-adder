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
}
