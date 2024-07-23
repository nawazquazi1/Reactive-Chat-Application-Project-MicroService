package com.chat.user.Repositories.Synchronous;

import com.chat.user.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositories_Syn extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByUsernameAndPassword(String username, String password);

    List<User> findByUsernameOrUsernameStartsWith(String username, String usernameStartWith);

 Boolean existsByUsername(String username);

 Boolean existsByEmail(String email);


}
