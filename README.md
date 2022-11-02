# abn-recipe

## Java application which allows users to manage their favourite recipes.

### Technologies:
```
 > Java 11 + Spring (boot, rest, test)
 > H2 memory database
 > Junit 4 and Mockito
 > SpringFox/Swagger (for API Documentation)
```

**NOTE**: I've opted to use SpringFox because it's possible to avoid a lot of Swagger annonations in code, so it's clear.

## How to Run

You can run this project using your IDE or running:
```gradlew bootRun```
in a terminal.

After running the project you can go to ```http://localhost:8080/swagger-ui/#/``` and test the API.

**NOTE**: If you want to update the ingredients, you can send the same list used to create it only removing or adding a new ingredient, so the API will automatically remove or add that ingredient.

The projects contains Unit Tests and Integrations Tests.
