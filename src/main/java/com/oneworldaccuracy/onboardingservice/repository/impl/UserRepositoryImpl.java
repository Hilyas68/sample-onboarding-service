package com.oneworldaccuracy.onboardingservice.repository.impl;

import com.oneworldaccuracy.onboardingservice.model.User;
import com.oneworldaccuracy.onboardingservice.repository.UserRepository;
import com.oneworldaccuracy.onboardingservice.vo.UserDto;
import com.oneworldaccuracy.onboardingservice.vo.enums.Roles;
import com.oneworldaccuracy.onboardingservice.vo.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserRepositoryImpl {

    @Autowired
    UserRepository userRepository;

    public List<UserDto> getAllUsers(int pageNumber, int pageSize) {
        List<UserDto> userDto = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<User> users = userRepository.findAll(pageable).toList();
        users.forEach(u -> {
            userDto.add(new UserDto(
                    u.getTitle(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getMobile(), u.getRole(), u.getDateRegistered(), u.getDateVerified(),
                    u.getDateDeactivated(), u.isVerified(), u.getStatus(), u.isDeleted()
            ));
        });
        return userDto;
    }

    public void addUser(UserDto userDto) {
        if (userDto != null) {
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setTitle(userDto.getTitle());
            user.setEmail(userDto.getEmail());
            user.setMobile(userDto.getMobile());

            user.setRole(Roles.USER);
            user.setDateRegistered(LocalDateTime.now());
            user.setVerified(false);
            user.setStatus(Status.REGISTERED);

            userRepository.save(user);
        }
    }

    public void updateUser(UserDto userDto) {
        if (userDto != null) {
            User user = new User();
            user.setId(userDto.getId());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setTitle(userDto.getTitle());
            user.setEmail(userDto.getEmail());
            user.setMobile(userDto.getMobile());

            userRepository.save(user);
        }
    }

    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return;
        }

        user.setDeleted(false);
        userRepository.save(user);

    }

    public boolean emailExist(String email){
        return userRepository.existsByEmail(email);
    }
}
