package com.example.usersubscriptionservice.service.impl;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.dto.UserDTO;
import com.example.usersubscriptionservice.error.UserAlreadyExistsException;
import com.example.usersubscriptionservice.error.UserNotFoundException;
import com.example.usersubscriptionservice.mapper.UserMapper;
import com.example.usersubscriptionservice.mapper.UserSubscriptionMapper;
import com.example.usersubscriptionservice.model.User;
import com.example.usersubscriptionservice.dao.UserRepositoryService;
import com.example.usersubscriptionservice.service.UserSubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSubServiceImpl implements UserSubService {

    private final UserRepositoryService repositoryService;
    private final UserMapper userMapper;
    private final UserSubscriptionMapper userSubscriptionMapper;

    @Override
    @Transactional
    public UserDTO createUser(RequestUserDTO requestUserDTO) {
        validateEmail(requestUserDTO.email());
        var user = new User();
        user.setFirstName(requestUserDTO.firstName());
        user.setLastName(requestUserDTO.lastName());
        user.setEmail(requestUserDTO.email().toLowerCase().trim());

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
    public UserDTO updateUser(Long id, RequestUserDTO requestUserDTO) {
        User existingUser = repositoryService.getUser(id).orElseThrow(() ->
                new UserNotFoundException("Пользователь с id " + id + " не зарегистрирован")
        );

        if (!existingUser.getEmail().equalsIgnoreCase(requestUserDTO.email().trim())) {
            validateEmail(requestUserDTO.email());
        }

        existingUser.setFirstName(requestUserDTO.firstName());
        existingUser.setLastName(requestUserDTO.lastName());
        existingUser.setEmail(requestUserDTO.email().toLowerCase().trim());

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
