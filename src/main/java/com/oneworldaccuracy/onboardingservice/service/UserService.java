package com.oneworldaccuracy.onboardingservice.service;

import com.oneworldaccuracy.onboardingservice.model.User;
import com.oneworldaccuracy.onboardingservice.repository.UserRepository;
import com.oneworldaccuracy.onboardingservice.repository.impl.UserRepositoryImpl;
import com.oneworldaccuracy.onboardingservice.util.Constants;
import com.oneworldaccuracy.onboardingservice.util.Utils;
import com.oneworldaccuracy.onboardingservice.vo.UserDto;
import com.oneworldaccuracy.onboardingservice.vo.enums.Status;
import com.oneworldaccuracy.onboardingservice.vo.request.RegisterRequest;
import com.oneworldaccuracy.onboardingservice.vo.request.UpdateRequest;
import com.oneworldaccuracy.onboardingservice.vo.response.ErrorResponse;
import com.oneworldaccuracy.onboardingservice.vo.response.ServiceResponse;
import com.oneworldaccuracy.onboardingservice.vo.response.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public ServiceResponse registerUser(RegisterRequest request, HttpServletRequest req) {

        try {
            if (repository.emailExist(request.getEmail())) {
                return new ErrorResponse(Constants.EMAIL_EXIST);
            }

            UserDto user = new UserDto();
            user.setTitle(request.getTitle());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setMobile(request.getMobile());
            user.setPassword(encoder.encode(request.getPassword()));
            String verificationCode = Utils.generateVerificationCode();
            user.setVerificationCode(verificationCode);

            repository.addUser(user);
            String verifyUrl = Utils.getUrl(req).concat("/verify?code=").concat(verificationCode);

            String content = Utils.verificationEmailTemplate().replace("#name#", request.getFirstName()).replace("#url#", verifyUrl);
            ;


            emailService.sendEmail(Constants.SENDER_EMAIL, request.getEmail(), Constants.SENDER_NAME, Constants.VERIFY_SUBJECT, content);
            return new SuccessResponse(Constants.REGISTRATION_SUCCESS, null);

        } catch (Exception ex) {
            logger.error("Error occurred - registerUser : ", ex);
            return new ErrorResponse("");
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

            if (user.isDeleted()) {
                return new ErrorResponse(Constants.DEACTIVATED_MESSAGE);
            }


            if (user != null) {
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setEmail(request.getEmail());
                user.setMobile(request.getMobile());
                user.setId(id);

                userRepository.save(user);

                return new SuccessResponse(Constants.OPERATION_SUCCESS, null);
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

            if (user.isDeleted()) {
                return new ErrorResponse(Constants.DEACTIVATED_MESSAGE);
            }

            if (user != null) {
                repository.deactivateUser(id);

                String content = Utils.welcomeOnboardEmailTemplate().replace("#name#", user.getFirstName());

                emailService.sendEmail(Constants.SENDER_EMAIL, user.getEmail(), Constants.SENDER_NAME, Constants.OFF_BORDING, content);
                return new SuccessResponse(Constants.OPERATION_SUCCESS, null);
            } else {
                return new ErrorResponse(Constants.USER_NOT_FOUND);
            }

        } catch (Exception ex) {
            logger.error("Error occurred - deactivateUser : ", ex);
            return new ErrorResponse(Constants.ERROR_PROCESSING);
        }
    }

    public ServiceResponse verifyUser(String verificationCode) {
        try {
            User user = userRepository.findByVerificationCode(verificationCode);

            if (user == null || user.isVerified()) {
                return new ErrorResponse(Constants.USER_ALREADY_VERIFIED);
            } else {
                user.setVerified(true);
                user.setVerificationCode(null);
                user.setStatus(Status.VERIFIED);
                userRepository.save(user);
                String content = Utils.welcomeOnboardEmailTemplate().replace("#name#", user.getFirstName());
                emailService.sendEmail(Constants.SENDER_EMAIL, user.getEmail(), Constants.SENDER_NAME, Constants.WELCOME_SUBJECT, content);

                return new SuccessResponse(Constants.VERIFED_SUCCESS, null);
            }

        } catch (Exception ex) {
            logger.error("Error occurred - deactivateUser : ", ex);
            return new ErrorResponse(Constants.ERROR_PROCESSING);
        }
    }
}
