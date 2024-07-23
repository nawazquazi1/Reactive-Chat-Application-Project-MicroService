package com.chat.user.Service.Asynchronous;

import com.chat.user.Model.User;
import com.chat.user.Payload.UpdateUserDetails;
import com.chat.user.Payload.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("AsyncService")
@Qualifier("AsyncService")
public interface UserService_Asynchronous {

    Mono<UserDto> registerUser(UserDto userDto);

    Mono<UserDto> updateUser(UpdateUserDetails updateUserDetails, Long id);

    Flux<UserDto> findAllUsers();

    Mono<UserDto> findUserById(long id);

    Flux<UserDto> searchUsersByUsername(String username);


   User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);

}
