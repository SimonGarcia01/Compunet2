package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.AvailableSpaceRequest;
import org.example.finalproject.api.v1.dtos.GeneralProgressResponse;
import org.example.finalproject.api.v1.dtos.HistoricalRecordRequest;
import org.example.finalproject.api.v1.dtos.HistoricalRecordResponse;

import java.util.List;

public interface HistoricalRecordService {

    HistoricalRecordResponse findById(Integer id);
    List<HistoricalRecordResponse> getAllHistoricalRecords();
    void createHistoricalRecord(HistoricalRecordRequest historicalRecordRequest);
    void updateHistoricalRecord(Integer id, HistoricalRecordRequest historicalRecordRequest);
    void deleteHistoricalRecord(Integer id);

}
