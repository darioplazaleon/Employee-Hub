package com.example.employessytem.service;

import com.example.employessytem.dto.employee.EmployeeAdd;
import com.example.employessytem.dto.employee.EmployeeDTO;
import com.example.employessytem.dto.employee.EmployeeListDTO;
import com.example.employessytem.dto.employee.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
  EmployeeResponse registerEmployee(EmployeeAdd employeeAdd);

  EmployeeDTO getEmployee(Long id);

  Page<EmployeeListDTO> getAllEmployees(Pageable pageable);

  EmployeeResponse updateEmployee(Long id, EmployeeAdd employeeAdd);

  void deleteEmployee(Long id);
}
