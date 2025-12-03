// HistoricalRecordRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.HistoricalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalRecordRepository extends JpaRepository<HistoricalRecord, Integer> {}
