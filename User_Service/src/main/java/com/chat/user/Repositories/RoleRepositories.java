package com.chat.user.Repositories;

import com.chat.user.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositories extends JpaRepository<Role,Integer> {
}
