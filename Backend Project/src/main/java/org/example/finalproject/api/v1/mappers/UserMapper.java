package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.UserRequest;
import org.example.finalproject.api.v1.dtos.UserResponse;
import org.example.finalproject.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public User toUser(UserRequest userRequest);
    public UserResponse toUserResponse(User user);
}
