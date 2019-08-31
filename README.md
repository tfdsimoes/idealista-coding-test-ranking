# Challenge management of quality of ads (idealista)

This code is the response to the challenge of idealista Reto: Servicio para gestión de calidad de los anuncios that is present in their [github](https://github.com/idealista/coding-test-ranking).

## Technologies
* Java 1.8
* Apache Maven 3.6.0

## To run
### Using maven
```bash
$ mvn spring-boot:run
```

## Tests
There is unitary tests for the controllers and integration tests.
To run them just do:
```bash
$ mvn tets
```

## Endpoints
There is 3 endpoints implemented one without authorization and the other two with basic auth.

 * No authorization required
    * GET localhost:8080/ads/public-listing (Will retrieve ads with score over 40)
 
 * Basic auth required (username: quality, password: quality)
     * GET localhost:8080/ads/quality-listing  (Will retrieve ads with score under 40)
     * PUT localhost:8080/ads/calculate-score  (Will calculate the scores of ads)

To help you test there is a [postman](https://www.getpostman.com/) collection to help you to test each one

