// HistoricalRecordRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.HistoricalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalRecordRepository extends JpaRepository<HistoricalRecord, Integer> {}
