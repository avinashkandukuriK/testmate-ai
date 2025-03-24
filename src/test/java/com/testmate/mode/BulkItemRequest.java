package com.testmate.mode;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BulkItemRequest {
    private List<String> itemNumbers;
    private List<String> clubNumbers;
    private String status;
    private String effectiveDate;
    private String outOfStockDate;

    // Getters, Setters, Constructors
}
