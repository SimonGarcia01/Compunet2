package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.HistoricalRecordRequest;
import org.example.finalproject.api.v1.dtos.HistoricalRecordResponse;
import org.example.finalproject.entity.HistoricalRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoricalRecordMapper {

    public HistoricalRecord toHistoricalRecord(HistoricalRecordRequest historicalRecordRequest);
    public HistoricalRecordResponse toHistoricalRecordResponse(HistoricalRecord historicalRecord);

}
