package com.example.employessytem.entity;

import com.example.employessytem.dto.employee.EmployeeAdd;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String password;
  private String position;
  private Date createdAt;
  private Long salary;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<VacationRequest> vacationRequests = new ArrayList<>();

  public void updateInfo(EmployeeAdd employeeAdd) {
    this.name = employeeAdd.name();
    this.email = employeeAdd.email();
    this.position = employeeAdd.position();
    this.salary = employeeAdd.salary();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getUsername() {
    return email;
  }
}
