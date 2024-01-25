package com.app.service;

import com.app.dto.AppResponse;
import com.app.dto.CleanerDTO;
import com.app.dto.LoginRequest;
import com.app.dto.SignupRequest;
import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.util.CommonUtil;
import com.app.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public AppResponse authenticateUser(LoginRequest loginRequest) {
        try {
            if(!RequestValidator.isLoginUpRequestValid(loginRequest)){
                return new AppResponse(false, "Invalid value provided !");
            }

            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if(userOptional.isEmpty()){
                return new AppResponse(false, "Wrong Credentials !");
            }

            if(!CommonUtil.isPasswordValid(loginRequest.getPassword(), userOptional.get().getPassword())){
                return new AppResponse(false, "Wrong Credentials !");
            }

            return new AppResponse(true, "Login Success.");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Login Failed !");
        }
    }

    public AppResponse doRegistration(SignupRequest signupRequest) {
        try {
            if(!RequestValidator.isSignUpRequestValid(signupRequest)){
                return new AppResponse(false, "Invalid value provided !");
            }

            Optional<User> existingUser = userRepository.findByEmail(signupRequest.getEmail());
            if(existingUser.isPresent()) {
                return new AppResponse(false, "Email already exists !");
            }

            User user = new User();
            user.setFirstName(signupRequest.getFirstName());
            user.setLasName(signupRequest.getLastName());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(CommonUtil.getEncodedPassword(signupRequest.getPassword()));
            user.setGender(signupRequest.getGender());

            if(signupRequest.getRegistrationType().equals("USER")){
                user.setRole("ROLE_USER");
                user.setStatus(1);
            }
            else {
                user.setRole("ROLE_CLEANER");
                user.setStatus(1);
            }
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            return new AppResponse(true, "Registration Success.");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Registration Failed !");
        }
    }

    public User getUserByEmail(String email) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            return userOptional.orElse(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CleanerDTO> getActiveCleanerList() {
        try {
            List<User> users = userRepository.findByRoleAndStatus("ROLE_CLEANER", 1);
            List<CleanerDTO> cleanerList = new ArrayList<CleanerDTO>();
            cleanerList.add(new CleanerDTO("", "--select one--"));
            for(User user : users) {
                CleanerDTO cleanerDTO = new CleanerDTO();
                cleanerDTO.setId(String.valueOf(user.getUserId()));
                cleanerDTO.setName(CommonUtil.getFormattedName(user));
                cleanerList.add(cleanerDTO);
            }
            return cleanerList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
