package com.example.employessytem.service;

import com.example.employessytem.dto.TokenResponse;
import com.example.employessytem.dto.employee.EmployeeAdd;
import com.example.employessytem.dto.employee.EmployeeLogin;
import com.example.employessytem.dto.employee.EmployeeResponse;

public interface AuthService {
    EmployeeResponse registerEmployee (EmployeeAdd employeeAdd);
    TokenResponse login(EmployeeLogin employeeLogin);
}
