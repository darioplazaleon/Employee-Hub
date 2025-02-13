package com.example.employessytem.entity;

import com.example.employessytem.dto.employee.EmployeeAdd;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String position;
    private Date createdAt;
    private Long salary;

    private Role role;

    public void updateInfo(EmployeeAdd employeeAdd) {
        this.name = employeeAdd.name();
        this.email = employeeAdd.email();
        this.position = employeeAdd.position();
        this.salary = employeeAdd.salary();
    }
}
