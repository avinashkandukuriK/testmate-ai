package com.testmate.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.testmate.mode.FlattenedItemResponse;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

public class common {
    private void saveResultsToFile(List<FlattenedItemResponse> expected, List<FlattenedItemResponse> actual) {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("expected", expected);
            result.put("actual", actual);

            mapper.writeValue(new File("target/test-output/flattened-comparison.json"), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveResultsToFile(List<MatchResult> matchResults) {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

            long matched = matchResults.stream().filter(m -> m.getMatchStatus().equals("MATCH")).count();
            long total = matchResults.size();

            Map<String, Object> report = new LinkedHashMap<>();
            report.put("summary", Map.of(
                    "total", total,
                    "matched", matched,
                    "mismatched", total - matched
            ));

            List<Map<String, Object>> results = matchResults.stream().map(m -> {
                Map<String, Object> entry = new LinkedHashMap<>();
                entry.putAll(Map.of(
                        "itemNumber", m.getExpected().getItemNumber(),
                        "clubNumber", m.getExpected().getClubNumber(),
                        "status", m.getExpected().getStatus(),
                        "effectiveDate", m.getExpected().getEffectiveDate(),
                        "outOfStockDate", m.getExpected().getOutOfStockDate(),
                        "matchStatus", m.getMatchStatus()
                ));
                return entry;
            }).collect(Collectors.toList());

            report.put("results", results);

            mapper.writeValue(new File("target/test-output/flattened-match-report.json"), report);
            System.out.println(">>> Match report written to flattened-match-report.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
