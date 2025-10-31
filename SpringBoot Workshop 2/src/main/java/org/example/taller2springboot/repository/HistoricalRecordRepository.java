// HistoricalRecordRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.HistoricalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalRecordRepository extends JpaRepository<HistoricalRecord, Integer> {}
