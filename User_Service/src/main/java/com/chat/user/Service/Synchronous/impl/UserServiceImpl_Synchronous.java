package com.chat.user.Service.Synchronous.impl;

import com.chat.user.Config.AppConstants;
import com.chat.user.Exception.EmailAlreadyExistsException;
import com.chat.user.Exception.ResourceNotFoundException;
import com.chat.user.Exception.UserNameAlreadyExistsException;
import com.chat.user.Model.Role;
import com.chat.user.Model.User;
import com.chat.user.Payload.UpdateUserDetails;
import com.chat.user.Payload.UserDto;
import com.chat.user.Repositories.Synchronous.RoleRepositories_Syn;
import com.chat.user.Repositories.Synchronous.UserRepositories_Syn;
import com.chat.user.Service.Synchronous.UserService_Synchronous;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Qualifier("SyncService")
public class UserServiceImpl_Synchronous implements UserService_Synchronous {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl_Synchronous.class);
    private UserRepositories_Syn userRepositories;
    private RoleRepositories_Syn roleRepositories;
    //    private PasswordEncoder PasswordEncoder;
    private ModelMapper modelMapper;

    @Override
    public UserDto registerUser(UserDto userDto) {
        // Check if username already exists
        if (this.userRepositories.existsByUsername(userDto.getUsername())) {
            log.warn("Username {} already exists", userDto.getUsername());
            throw new UserNameAlreadyExistsException(
                    String.format("Username %s already exists", userDto.getUsername()));
        }

        // Check if email already exists
        if (this.userRepositories.existsByEmail(userDto.getEmail())) {
            log.warn("Email {} already exists", userDto.getEmail());
            throw new EmailAlreadyExistsException(
                    String.format("Email %s already exists", userDto.getEmail()));
        }

        // Proceed with role and user creation
        userDto.setActive(true);

        Role role = this.roleRepositories.findById(AppConstants.APP_USER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        userDto.getRoles().add(role);

        User user = dtoToUser(userDto);
        user = this.userRepositories.save(user);

        return this.userToDto(user);
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
    public List<UserDto> searchUsersByUsername(String username) {
        return Collections.singletonList(this.modelMapper.map(this.userRepositories.findByUsernameOrUsernameStartsWith(username, username), UserDto.class));
    }

    @Override
    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
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
