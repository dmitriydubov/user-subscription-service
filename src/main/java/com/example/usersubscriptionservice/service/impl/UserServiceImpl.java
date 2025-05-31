package com.example.usersubscriptionservice.service.impl;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.dto.UserDTO;
import com.example.usersubscriptionservice.error.exception.UserAlreadyExistsException;
import com.example.usersubscriptionservice.error.exception.UserNotFoundException;
import com.example.usersubscriptionservice.mapper.UserMapper;
import com.example.usersubscriptionservice.model.User;
import com.example.usersubscriptionservice.dao.UserRepositoryService;
import com.example.usersubscriptionservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryService repositoryService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDTO createUser(RequestUserDTO request) {
        validateEmail(request.email());
        var user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email().toLowerCase().trim());

        return userMapper.toDTO(repositoryService.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        validateById(id);
        repositoryService.deleteUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserInfo(Long id) {
        User user = repositoryService.getUser(id).orElseThrow(() ->
                new UserNotFoundException("Пользователь с id " + id + " не зарегистрирован")
        );

        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, RequestUserDTO request) {
        User existingUser = repositoryService.getUser(id).orElseThrow(() ->
                new UserNotFoundException("Пользователь с id " + id + " не зарегистрирован")
        );

        if (!existingUser.getEmail().equalsIgnoreCase(request.email().trim())) {
            validateEmail(request.email());
        }

        existingUser.setFirstName(request.firstName());
        existingUser.setLastName(request.lastName());
        existingUser.setEmail(request.email().toLowerCase().trim());

        return userMapper.toDTO(repositoryService.save(existingUser));
    }

    private void validateEmail(String email) {
        if (repositoryService.existsByEmail(email.toLowerCase().trim())) {
            throw new UserAlreadyExistsException("Пользователь с email " + email + " уже зарегистрирован");
        }
    }

    private void validateById(Long id) {
        if (!repositoryService.existsById(id)) {
            throw new UserNotFoundException("Пользователь с id " + id + " не зарегистрирован");
        }
    }
}
