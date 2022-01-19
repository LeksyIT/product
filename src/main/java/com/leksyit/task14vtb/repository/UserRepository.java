package com.leksyit.task14vtb.repository;

import com.leksyit.task14vtb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByLogin(String username);
}
