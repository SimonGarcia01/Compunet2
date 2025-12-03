package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.MessageRequest;
import org.example.finalproject.api.v1.dtos.MessageResponse;
import org.example.finalproject.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    public Message toMessage(MessageRequest messageRequest);
    public MessageResponse toMessageResponse(Message message);

}
