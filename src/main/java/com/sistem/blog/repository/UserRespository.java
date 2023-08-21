package com.sistem.blog.repository;

import com.sistem.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User,Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsernameOrEmail(String username, String email);
    public Optional<User> findByUsername(String username);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String username);
}
