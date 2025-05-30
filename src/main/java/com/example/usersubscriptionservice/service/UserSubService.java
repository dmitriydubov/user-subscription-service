package com.example.usersubscriptionservice.service;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.dto.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

public interface UserSubService {

    UserDTO createUser(RequestUserDTO requestUserDTO);

    void deleteUser(Long id);

    UserDTO getUserInfo(Long id);

    UserDTO updateUser(Long id, RequestUserDTO requestUserDTO);
}
