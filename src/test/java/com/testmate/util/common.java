package com.testmate.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.testmate.mode.FlattenedItemResponse;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class common {

    protected void saveResultsToFile(List<FlattenedItemResponse> expected, List<FlattenedItemResponse> actual) {
        try {
            File output = new File("target/test-output/flattened-comparison.json");
            File parent = output.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("expected", expected);
            result.put("actual", actual);

            mapper.writeValue(output, result);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write flattened comparison report", e);
        }
    }
}
