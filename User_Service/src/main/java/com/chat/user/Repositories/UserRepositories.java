package com.chat.user.Repositories;

import com.chat.user.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

    List<User> findByUsernameOrUsernameStartsWith(String username, String usernameStartWith);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


}
