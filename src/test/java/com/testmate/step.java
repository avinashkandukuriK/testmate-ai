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

import java.util.*;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

public class step {

    List<BulkItemRequest> bulkRequests;
    List<FlattenedItemResponse> apiResponses;

    @Given("I send a bulk item request with the following data")
    public void sendBulkItemRequest(DataTable dataTable) throws Exception {
        bulkRequests = new ArrayList<>();

        for (Map<String, String> row : dataTable.asMaps()) {
            List<String> itemNumbers = Arrays.stream(row.get("itemNumbers").split(","))
                    .map(String::trim).collect(Collectors.toList());

            List<String> clubNumbers = Arrays.stream(row.get("clubNumbers").split(","))
                    .map(String::trim).collect(Collectors.toList());

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
        List<FlattenedItemResponse> expectedFlattened = new ArrayList<>();

        for (Map<String, String> row : expectedTable.asMaps()) {
            List<String> itemNumbers = Arrays.stream(row.get("itemNumbers").split(","))
                    .map(String::trim).collect(Collectors.toList());

            List<String> clubNumbers = Arrays.stream(row.get("clubNumbers").split(","))
                    .map(String::trim).collect(Collectors.toList());

            String status = row.get("status");
            String effectiveDate = row.get("effectiveDate");
            String outOfStockDate = row.get("outOfStockDate");

            for (String item : itemNumbers) {
                for (String club : clubNumbers) {
                    expectedFlattened.add(new FlattenedItemResponse(
                            item, club, status, effectiveDate, outOfStockDate
                    ));
                }
            }
        }

        // Now validate each flattened combination
        for (FlattenedItemResponse expected : expectedFlattened) {
            boolean found = apiResponses.stream().anyMatch(actual -> actual.equals(expected));
            Assertions.assertTrue(found, "Expected item-club combo not found: " + expected.getItemNumber() + "-" + expected.getClubNumber());
        }

        for (FlattenedItemResponse expected : expectedFlattened) {
            boolean found = apiResponses.stream()
                    .anyMatch(r ->
                            r.getItemNumber().equals(expected.getItemNumber()) &&
                                    r.getClubNumber().equals(expected.getClubNumber())
                    );
        }

        for (FlattenedItemResponse expected : expectedFlattened) {
            Optional<FlattenedItemResponse> match = apiResponses.stream()
                    .filter(r ->
                            r.getItemNumber().equals(expected.getItemNumber()) &&
                                    r.getClubNumber().equals(expected.getClubNumber())
                    )
                    .findFirst();

            boolean found = match.isPresent();
            Assertions.assertTrue(found, "Expected item-club combo not found: " + expected.getItemNumber() + "-" + expected.getClubNumber());
            match.ifPresent(apiResponses::remove);
            System.out.println(found
                    ? "✅ Match found and removed for: " + expected
                    : "❌ No match found for: " + expected);
        }
    }

    @Then("the response should contain the following flattened item data")
    public void validateFlattenedResponse(DataTable expectedTable) {
        List<BulkItemRequest> expectedGrouped = expectedTable.asMaps().stream().map(row -> {
            return new BulkItemRequest(
                    Arrays.stream(row.get("itemNumbers").split(",")).map(String::trim).toList(),
                    Arrays.stream(row.get("clubNumbers").split(",")).map(String::trim).toList(),
                    row.get("status"),
                    row.get("effectiveDate"),
                    row.get("outOfStockDate")
            );
        }).collect(Collectors.toList());

        List<FlattenedItemResponse> expected = RequestFlattener.flattenBulkList(expectedGrouped);

        List<MatchResult> matchResults = new ArrayList<>();

        for (FlattenedItemResponse expectedCombo : expected) {
            boolean found = apiResponses.stream().anyMatch(r -> r.equals(expectedCombo));
            matchResults.add(new MatchResult(expectedCombo, found));
            Assertions.assertTrue(found, "Expected combo not found: " + expectedCombo.getItemNumber() + " x " + expectedCombo.getClubNumber());
        }

        saveResultsToFile(matchResults);
    }

}
