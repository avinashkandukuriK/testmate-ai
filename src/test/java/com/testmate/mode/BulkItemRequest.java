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

    public BulkItemRequest() {
    }

    public BulkItemRequest(List<String> itemNumbers,
                           List<String> clubNumbers,
                           String status,
                           String effectiveDate,
                           String outOfStockDate) {
        this.itemNumbers = itemNumbers;
        this.clubNumbers = clubNumbers;
        this.status = status;
        this.effectiveDate = effectiveDate;
        this.outOfStockDate = outOfStockDate;
    }
}
