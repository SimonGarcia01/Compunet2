package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.MessageRequest;
import org.example.finalproject.api.v1.dtos.MessageResponse;
import org.example.finalproject.api.v1.mappers.MessageMapper;
import org.example.finalproject.entity.Message;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.MessageRepository;
import org.example.finalproject.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    // Beans
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;


    // Find message by ID
    @Override
    public MessageResponse findById(Integer id) {
        return messageRepository.findById(id)
                .map(messageMapper::toMessageResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
    }

    // Get all messages
    @Override
    public List<MessageResponse> getAllMessages() {

        return messageRepository.findAll().stream()
                .map(messageMapper::toMessageResponse)
                .toList();

    }

    // Create a new message
    @Override
    @Transactional
    public void createMessage(MessageRequest request) {

        if (request.getText() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // If the program stay in this point is because the request have all the attributes for create != null
        Message message = messageMapper.toMessage(request);

        messageRepository.save(message);

    }

    // Update an existing message
    @Override
    @Transactional
    public void updateMessage(Integer id, MessageRequest request) {

        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));

        if (request.getText() != null) {
            message.setText(request.getText());
        }

        messageRepository.save(message);

    }

    // Delete a message
    @Override
    public void deleteMessage(Integer id) {

        messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));

        messageRepository.deleteById(id);

    }
}
