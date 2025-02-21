package com.example.employessytem.repository;

import com.example.employessytem.entity.Role;
import com.example.employessytem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u.email FROM User u WHERE u.role IN (:roles)")
    List<String> findEmailsByRoles(@Param("roles")List<Role> roles);

    boolean existsByEmail(String email);

    long countByActive(boolean active);
}
