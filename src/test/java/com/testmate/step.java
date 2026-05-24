package com.testmate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testmate.mode.BulkItemRequest;
import com.testmate.mode.FlattenedItemResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class step {

    private List<BulkItemRequest> bulkRequests;
    private List<FlattenedItemResponse> apiResponses;

    @Given("I send a bulk item request with the following data")
    public void sendBulkItemRequest(DataTable dataTable) throws Exception {
        bulkRequests = new ArrayList<>();

        for (Map<String, String> row : dataTable.asMaps()) {
            List<String> itemNumbers = Arrays.stream(row.get("itemNumbers").split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            List<String> clubNumbers = Arrays.stream(row.get("clubNumbers").split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            bulkRequests.add(new BulkItemRequest(
                    itemNumbers,
                    clubNumbers,
                    row.get("status"),
                    row.get("effectiveDate"),
                    row.get("outOfStockDate")
            ));
        }

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(bulkRequests)
                .post("/items/bulk");

        response.then().statusCode(200);

        ObjectMapper mapper = new ObjectMapper();
        apiResponses = mapper.readValue(response.asString(), new TypeReference<>() {});
    }

    @Then("the response should contain the following flattened item data")
    public void validateFlattenedResponse(DataTable expectedTable) {
        List<FlattenedItemResponse> expectedFlattened = flattenExpectedRows(expectedTable);

        for (FlattenedItemResponse expected : expectedFlattened) {
            boolean found = apiResponses.stream().anyMatch(actual -> actual.equals(expected));
            Assertions.assertTrue(
                    found,
                    "Expected item-club combo not found: " + expected.getItemNumber() + "-" + expected.getClubNumber()
            );
        }
    }

    private List<FlattenedItemResponse> flattenExpectedRows(DataTable expectedTable) {
        List<FlattenedItemResponse> expectedFlattened = new ArrayList<>();
        for (Map<String, String> row : expectedTable.asMaps()) {
            List<String> itemNumbers = Arrays.stream(row.get("itemNumbers").split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            List<String> clubNumbers = Arrays.stream(row.get("clubNumbers").split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            for (String item : itemNumbers) {
                for (String club : clubNumbers) {
                    expectedFlattened.add(new FlattenedItemResponse(
                            item,
                            club,
                            row.get("status"),
                            row.get("effectiveDate"),
                            row.get("outOfStockDate")
                    ));
                }
            }
        }
        return expectedFlattened;
    }
}
