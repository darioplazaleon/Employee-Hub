package com.example.employessytem.repository;

import com.example.employessytem.entity.Role;
import com.example.employessytem.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  @Query("SELECT u.email FROM User u WHERE u.role IN (:roles)")
  List<String> findEmailsByRoles(@Param("roles") List<Role> roles);

  boolean existsByEmail(String email);

  long countByActive(boolean active);

  @Query("SELECT u.position.name, COUNT(u) FROM User u GROUP BY u.position.name")
  List<Object[]> countEmployeesByPosition();

  @Query(
      "SELECT YEAR(u.createdAt), MONTH(u.createdAt), COUNT(u) FROM User u GROUP BY YEAR(u.createdAt), MONTH(u.createdAt)")
  List<Object[]> countEmployeesByJoinDate();
}
