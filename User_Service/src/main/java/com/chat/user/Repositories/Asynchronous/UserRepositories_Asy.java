package com.chat.user.Repositories.Asynchronous;

import com.chat.user.Model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepositories_Asy extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String username);

    Mono<User> findByEmail(String email);

    Mono<User> findByUsernameAndPassword(String username, String password);

    Flux<User> findByUsernameOrUsernameStartsWith(String username, String usernameStartWith);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);
}
