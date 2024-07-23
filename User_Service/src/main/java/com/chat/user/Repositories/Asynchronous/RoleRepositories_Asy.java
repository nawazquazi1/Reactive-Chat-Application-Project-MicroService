package com.chat.user.Repositories.Asynchronous;

import com.chat.user.Model.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepositories_Asy extends ReactiveCrudRepository<Role,Integer> {
}
