package com.testmate.db.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SqlInventoryItemRepository extends JpaRepository<SqlInventoryItem, String> {
    List<SqlInventoryItem> findByItemNumberAndClubNumber(String itemNumber, String clubNumber);
}
