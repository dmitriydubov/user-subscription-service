package com.example.usersubscriptionservice.service;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.dto.UserDTO;

public interface UserService {

    UserDTO createUser(RequestUserDTO request);

    void deleteUser(Long id);

    UserDTO getUserInfo(Long id);

    UserDTO updateUser(Long id, RequestUserDTO request);
}
