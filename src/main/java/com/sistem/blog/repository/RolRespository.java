package com.sistem.blog.repository;

import com.sistem.blog.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRespository extends JpaRepository<Rol, Long> {
    public Optional<Rol> findByName(String name);

}
