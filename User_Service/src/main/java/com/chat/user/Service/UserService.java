package com.chat.user.Service;

import com.chat.user.Model.User;
import com.chat.user.Payload.UpdateUserDetails;
import com.chat.user.Payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto registerUser(UserDto userDto);

    UserDto updateUser(UpdateUserDetails updateUserDetails, Long id);

    List<UserDto> findAllUsers();

    UserDto findUserById(long id);

    List<User> searchUsersByUsername(String username);


    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);
}
