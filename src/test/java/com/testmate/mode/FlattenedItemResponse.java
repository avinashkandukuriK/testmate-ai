package com.testmate.mode;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter @Getter
public class FlattenedItemResponse {
    private String itemNumber;
    private String clubNumber;
    private String status;
    private String effectiveDate;
    private String outOfStockDate;

    public FlattenedItemResponse(String item, String club, String status, String effectiveDate, String outOfStockDate) {
    }

    // equals & hashCode for comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlattenedItemResponse)) return false;
        FlattenedItemResponse that = (FlattenedItemResponse) o;
        return Objects.equals(itemNumber, that.itemNumber)
            && Objects.equals(clubNumber, that.clubNumber)
            && Objects.equals(status, that.status)
            && Objects.equals(effectiveDate, that.effectiveDate)
            && Objects.equals(outOfStockDate, that.outOfStockDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemNumber, clubNumber, status, effectiveDate, outOfStockDate);
    }
}
