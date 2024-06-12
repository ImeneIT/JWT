package com.example.JWTProject.repo;

import com.example.JWTProject.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser,Long> {
AppUser findByUsername(String username);

}
