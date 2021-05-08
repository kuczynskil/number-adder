package com.empik.numberadder.service;

public interface DelimitersService {
    String extractDelimiters(String numbers);
    void handleSpecialCharacters(String[] delimitersArr);
}
