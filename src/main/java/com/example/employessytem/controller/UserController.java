package com.example.employessytem.controller;

import com.example.employessytem.dto.employee.EmployeeAdd;
import com.example.employessytem.dto.employee.EmployeeDTO;
import com.example.employessytem.dto.employee.EmployeeListDTO;
import com.example.employessytem.dto.employee.EmployeeResponse;
import com.example.employessytem.service.impl.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeAdd employeeAdd) {
    return ResponseEntity.ok(userService.registerEmployee(employeeAdd));
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getEmployee(id));
  }

  @GetMapping("/all")
  public ResponseEntity<Page<EmployeeListDTO>> getAllEmployees(Pageable pageable) {
    return ResponseEntity.ok(userService.getAllEmployees(pageable));
  }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeResponse> updateEmployee(
      @PathVariable Long id, @RequestBody EmployeeAdd employeeAdd) {
    return ResponseEntity.ok(userService.updateEmployee(id, employeeAdd));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
    userService.deleteEmployee(id);
    return ResponseEntity.noContent().build();
  }
}
