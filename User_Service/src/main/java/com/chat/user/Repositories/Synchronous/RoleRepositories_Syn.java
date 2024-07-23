package com.chat.user.Repositories.Synchronous;

import com.chat.user.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositories_Syn extends JpaRepository<Role,Integer> {
}
