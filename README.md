# Numbers sum
Simple microservice returning sum of numbers.

## General info
This app returns the sum of numbers that have been passed in a GET parameter.\
Rest API has been documented by Swagger, which is available at http://<host>:<port>/swagger-ui.

## Technologies
* Java - version 11
* Spring Boot - version 2.4.3
* Swagger - version 3.0.0

## Features
Summation algorithm accepts following input variants:
* `"1,2,3,5,10"`
* `"1,5,9%0A15"`
* `"//;1;2;3"`
* `"//[;][.][|]1;2.3|5"`

If input is valid microservice returns a JSON object.\
For example for input "//[;][.][|]1;2.3|5":

```
    {
      "result": 11,
      "occurred": 1
    }
 ```

"Occurred" informs about how many times this result have been returned.\
Numbers over 1000 are not summed.

If input is invalid app returns "invalid input"

The algorithm doesn't take negative numbers.\
If those occur in the input the app returns "negatives not allowed" + the negative numbers.


## Contact
Created by [@kuczynskil](https://www.github.com/kuczynskil) - feel free to contact me!
