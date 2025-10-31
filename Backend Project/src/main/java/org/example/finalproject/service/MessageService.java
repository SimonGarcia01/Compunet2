package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.HistoricalRecordRequest;
import org.example.finalproject.api.v1.dtos.HistoricalRecordResponse;
import org.example.finalproject.api.v1.dtos.MessageRequest;
import org.example.finalproject.api.v1.dtos.MessageResponse;

import java.util.List;

public interface MessageService {

    MessageResponse findById(Integer id);
    List<MessageResponse> getAllMessages();
    void createMessage(MessageRequest messageRequest);
    void updateMessage(Integer id, MessageRequest messageRequest);
    void deleteMessage(Integer id);

}
