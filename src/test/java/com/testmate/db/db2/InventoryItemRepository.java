package com.testmate.db.db2;

import com.testmate.db.db2.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {
    List<InventoryItem> findByItemNumberAndClubNumber(String itemNumber, String clubNumber);
}
