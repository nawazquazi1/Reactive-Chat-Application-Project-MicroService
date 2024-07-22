package com.chat.user.Service.impl;

import com.chat.user.Config.AppConstants;
import com.chat.user.Exception.EmailAlreadyExistsException;
import com.chat.user.Exception.ResourceNotFoundException;
import com.chat.user.Exception.UserNameAlreadyExistsException;
import com.chat.user.Model.Role;
import com.chat.user.Model.User;
import com.chat.user.Payload.UpdateUserDetails;
import com.chat.user.Payload.UserDto;
import com.chat.user.Repositories.RoleRepositories;
import com.chat.user.Repositories.UserRepositories;
import com.chat.user.Service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepositories userRepositories;
    private RoleRepositories roleRepositories;
//    private PasswordEncoder PasswordEncoder;
    private ModelMapper modelMapper;


    @Override
    public UserDto registerUser(UserDto userDto) {
        log.info("registering user {}", userDto.getUsername());
        if (userRepositories.existsByUsername(userDto.getUsername())) {
            printLogWarn(userDto.getUsername());
            throw new UserNameAlreadyExistsException(
                    String.format("username %s already exists", userDto.getUsername()));
        }

        if (userRepositories.existsByEmail(userDto.getEmail())) {
            this.printLogWarn(userDto.getEmail());
            throw new EmailAlreadyExistsException(
                    String.format("email %s already exists", userDto.getEmail()));
        }

        userDto.setActive(true);
//        userDto.setPassword(PasswordEncoder.encode(userDto.getPassword()));
        Role role = this.roleRepositories.findById(AppConstants.APP_USER).orElseThrow();
        userDto.getRoles().add(role);
        User user = this.dtoToUser(userDto);
        return this.userToDto(this.userRepositories.save(user));
    }

    @Override
    public UserDto updateUser(UpdateUserDetails updateUserDetails, Long id) {
        log.info("updating user {}", id);
        if (this.userRepositories.existsByUsername(updateUserDetails.getUserName())) {
            this.printLogWarn(updateUserDetails.getUserName());
            throw new UserNameAlreadyExistsException(
                    String.format("userName %s already exists", updateUserDetails.getUserName())
            );
        }
        User user = this.userRepositories.findById(id).orElseThrow();
        user.setUsername(updateUserDetails.getUserName());
        user.setName(updateUserDetails.getName());
        user.setBio(updateUserDetails.getBio());
        user.setUpdatedAt(Instant.now());
        return this.userToDto(this.userRepositories.save(user));

    }

    @Override
    public List<UserDto> findAllUsers() {
        log.info("retrieving all user ");
        return Collections.singletonList(this.modelMapper.map(this.userRepositories.findAll(), UserDto.class));
    }

    @Override
    public UserDto findUserById(long id) {
        User user = this.userRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        log.info("user {}", user);
        return this.userToDto(user);
    }

    @Override
    public List<User> searchUsersByUsername(String username) {
        return this.userRepositories.findByUsernameOrUsernameStartsWith(username, username);
    }

    @Override
    public User dtoToUser(UserDto userDto) {
        User user= this.modelMapper.map(userDto, User.class);
        user.setCreatedAt(Instant.now());
        return user;
    }

    @Override
    public UserDto userToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }

    private void printLogWarn(String message) {
        log.warn("user {} already exists", message);
    }

}
