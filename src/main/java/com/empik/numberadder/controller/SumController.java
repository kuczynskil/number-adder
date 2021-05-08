package com.empik.numberadder.controller;

import com.empik.numberadder.service.SumServiceV1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SumController {

    private final SumServiceV1 sumServiceV1;

    public SumController(SumServiceV1 sumServiceV1) {
        this.sumServiceV1 = sumServiceV1;
    }

    @GetMapping("")
    public ResponseEntity<Object> getSum(
            @ApiParam(value = "Possible input variants:" +
                    "\n1)     1,2,3,5,10" +
                    "\n2)     1,5,9%0A15" +
                    "\n3)     //;1;2;3" +
                    "\n4)     //[;][.][|]1;2.3|5")
            @RequestParam(defaultValue = "") String numbers) throws JsonProcessingException {
        int[] negativeNumbers = sumServiceV1.getNegativeNumbers(numbers);

        if (negativeNumbers.length > 0) return negativeNumbersResponse(negativeNumbers);

        int result;
        int resOccurrence;

        try {
            result = sumServiceV1.add(numbers);
            resOccurrence = sumServiceV1.getResultOccurrence(result);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("invalid input", HttpStatus.OK);
        }

        String json = String.format("{\"result\":%d,\"occurred\":%d}", result, resOccurrence);
        return new ResponseEntity<>(new ObjectMapper().readValue(json, Object.class), HttpStatus.OK);
    }

    private ResponseEntity<Object> negativeNumbersResponse(int[] negativeNumbers) {
        StringBuilder sb = new StringBuilder();
        appendNegativeNums(negativeNumbers, sb);
        return new ResponseEntity<>("negatives not allowed: " + sb.toString(), HttpStatus.OK);
    }

    private void appendNegativeNums(int[] negativeNumbers, StringBuilder sb) {
        sb.append("[");

        for (int i = 0; i < negativeNumbers.length; i++) {
            if (i == negativeNumbers.length - 1) sb.append(negativeNumbers[i]);
            else sb.append(negativeNumbers[i]).append(", ");
        }
        sb.append("]");
    }
}
