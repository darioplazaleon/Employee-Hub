package com.example.employessytem.entity;

import com.example.employessytem.dto.employee.EmployeeAdd;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

  @ManyToOne
  @JoinColumn(name = "position_id")
  private Position position;

  @Column(name = "created_at", updatable = false)
  private LocalDate createdAt;

  private Long salary;
  private boolean active;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<VacationRequest> vacationRequests = new ArrayList<>();

  public void updateInfo(EmployeeAdd employeeAdd, Position position) {
    this.name = employeeAdd.name();
    this.email = employeeAdd.email();
    this.position = position;
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

  @Override
  public String toString() {
    return "User{id="
        + id
        + ", name='"
        + name
        + "', email='"
        + email
        + "', position='"
        + position
        + "', salary="
        + salary
        + "}";
  }

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDate.now();
  }
}
