package com.college.cms.modules.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.college.cms.modules.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}