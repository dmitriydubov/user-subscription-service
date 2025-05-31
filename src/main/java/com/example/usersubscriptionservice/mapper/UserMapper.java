package com.example.usersubscriptionservice.mapper;

import com.example.usersubscriptionservice.dto.UserDTO;
import com.example.usersubscriptionservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
