package com.testmate.db.db2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "inventory_item")
@Getter
@Setter
public class InventoryItem {

    @Id
    private String itemNumber;

    private String clubNumber;
    private String status;
    private LocalDate effectiveDate;
    private LocalDate outOfStockDate;
}
