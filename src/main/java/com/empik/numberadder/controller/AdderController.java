package com.empik.numberadder.controller;

import com.empik.numberadder.service.AdderServiceV1;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdderController {

    private final AdderServiceV1 adderServiceV1;

    public AdderController(AdderServiceV1 adderServiceV1) {
        this.adderServiceV1 = adderServiceV1;
    }

    @GetMapping("")
    public ResponseEntity<String> getSum(@RequestParam(defaultValue = "") String numbers) {
        System.out.println(numbers);
        int[] negativeNumbers = adderServiceV1.getNegativeNumbers(numbers);

        if (negativeNumbers.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            for (int i = 0; i < negativeNumbers.length; i++) {
                if (i == negativeNumbers.length - 1) sb.append(negativeNumbers[i]);
                else sb.append(negativeNumbers[i]).append(", ");
            }
            sb.append("]");

            return new ResponseEntity<>("negatives not allowed: " + sb.toString(), HttpStatus.OK);
        }

        return new ResponseEntity<>(String.valueOf(adderServiceV1.add(numbers)), HttpStatus.OK);
    }
}
