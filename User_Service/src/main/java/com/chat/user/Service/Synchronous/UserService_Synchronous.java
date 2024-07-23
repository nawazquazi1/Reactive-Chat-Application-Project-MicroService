package com.chat.user.Service.Synchronous;

import com.chat.user.Model.User;
import com.chat.user.Payload.UpdateUserDetails;
import com.chat.user.Payload.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;



public interface UserService_Synchronous {

    UserDto registerUser(UserDto userDto);

    UserDto updateUser(UpdateUserDetails updateUserDetails, Long id);

    List<UserDto> findAllUsers();

    UserDto findUserById(long id);

    List<UserDto> searchUsersByUsername(String username);

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);
}

