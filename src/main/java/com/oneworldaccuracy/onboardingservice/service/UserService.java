package com.oneworldaccuracy.onboardingservice.service;

import com.oneworldaccuracy.onboardingservice.model.User;
import com.oneworldaccuracy.onboardingservice.repository.UserRepository;
import com.oneworldaccuracy.onboardingservice.repository.impl.UserRepositoryImpl;
import com.oneworldaccuracy.onboardingservice.util.Constants;
import com.oneworldaccuracy.onboardingservice.util.Utils;
import com.oneworldaccuracy.onboardingservice.vo.UserDto;
import com.oneworldaccuracy.onboardingservice.vo.request.RegisterRequest;
import com.oneworldaccuracy.onboardingservice.vo.request.UpdateRequest;
import com.oneworldaccuracy.onboardingservice.vo.response.ErrorResponse;
import com.oneworldaccuracy.onboardingservice.vo.response.ServiceResponse;
import com.oneworldaccuracy.onboardingservice.vo.response.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepositoryImpl repository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public ServiceResponse registerUser(RegisterRequest request) {

        try {
            if (repository.emailExist(request.getEmail())) {
                return new ErrorResponse(Constants.EMAIL_EXIST);
            }

            UserDto user = new UserDto();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setMobile(request.getMobile());
            user.setPassword(encoder.encode(request.getPassword()));
            user.setVerificationCode(Utils.generateVerificationCode());

            repository.addUser(user);

            //TODO: Send on-boarding mail

            return new SuccessResponse(Constants.REGISTRATION_SUCCESS, null);

        } catch (Exception ex) {
            logger.error("Error occurred - registerUser : ", ex);
            return new ErrorResponse(Constants.ERROR_PROCESSING);
        }
    }

    public ServiceResponse getRegisteredUsers(int pageNumber, int pageSize) {
        try {

            List<UserDto> users = repository.getAllUsers(pageNumber, pageSize);

            return new SuccessResponse(Constants.OPERATION_SUCCESS, users);

        } catch (Exception ex) {
            logger.error("Error occurred - getRegisteredUsers : ", ex);
            return new ErrorResponse(Constants.ERROR_PROCESSING);
        }
    }

    public ServiceResponse updateUser(long id, UpdateRequest request) {
        try {
            User user = userRepository.findById(id).orElse(null);

            if (user != null) {
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setEmail(request.getEmail());
                user.setMobile(request.getMobile());
                user.setId(id);

                userRepository.save(user);

                return new SuccessResponse(Constants.OPERATION_SUCCESS, user);
            } else {
                return new ErrorResponse(Constants.USER_NOT_FOUND);
            }

        } catch (Exception ex) {
            logger.error("Error occurred - updateUser : ", ex);
            return new ErrorResponse(Constants.ERROR_PROCESSING);
        }

    }

    public ServiceResponse deactivateUser(long id) {
        try {
            User user = userRepository.findById(id).orElse(null);

            if (user != null) {
                repository.deactivateUser(id);

                return new SuccessResponse(Constants.OPERATION_SUCCESS, user);
            } else {
                return new ErrorResponse(Constants.USER_NOT_FOUND);
            }

        } catch (Exception ex) {
            logger.error("Error occurred - deactivateUser : ", ex);
            return new ErrorResponse(Constants.ERROR_PROCESSING);
        }
    }
}
