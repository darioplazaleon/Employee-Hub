package com.example.employessytem.service.impl;

import com.example.employessytem.dto.employee.EmployeeAdd;
import com.example.employessytem.dto.employee.EmployeeDTO;
import com.example.employessytem.dto.employee.EmployeeListDTO;
import com.example.employessytem.dto.employee.EmployeeResponse;
import com.example.employessytem.entity.Position;
import com.example.employessytem.entity.Role;
import com.example.employessytem.entity.User;
import com.example.employessytem.repository.PositionRepository;
import com.example.employessytem.repository.UserRepository;
import com.example.employessytem.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final IEmailService emailService;
  private final PositionRepository positionRepository;

  @Override
  public EmployeeResponse registerEmployee(EmployeeAdd employeeAdd) {

    if (userRepository.existsByEmail(employeeAdd.email())) {
      throw new RuntimeException("User with this email already exists");
    }

    Position position = positionRepository.findById(employeeAdd.positionId()).orElseThrow();

    String randomPassword = generateRandomPassword();

    var user =
        User.builder()
            .email(employeeAdd.email())
            .position(position)
            .name(employeeAdd.name())
            .role(employeeAdd.role())
            .salary(employeeAdd.salary())
            .password(passwordEncoder.encode(randomPassword))
            .active(true)
            .vacationRequests(new ArrayList<>())
            .build();

    var savedUser = userRepository.save(user);

    try {
      emailService.sendCredentialsEmail(employeeAdd.email(), employeeAdd.email(), randomPassword);
    } catch (MessagingException e) {
      // Handle the exception, e.g., log the error
      e.printStackTrace();
    }

    return new EmployeeResponse(savedUser, randomPassword);
  }

  @Override
  public EmployeeDTO getEmployee(Long id) {
    User user = userRepository.findById(id).orElseThrow();
    return new EmployeeDTO(user);
  }

  @Override
  public Page<EmployeeListDTO> getAllEmployees(Pageable pageable) {
    return userRepository.findAll(pageable).map(EmployeeListDTO::new);
  }

  @Override
  public EmployeeResponse updateEmployee(Long id, EmployeeAdd employeeAdd) {
    var foundUser = userRepository.findById(id);

    if (foundUser.isEmpty()) {
      throw new EntityNotFoundException("User not found");
    }

    Position position = positionRepository.findById(employeeAdd.positionId()).orElseThrow();

    var user = foundUser.get();

    user.updateInfo(employeeAdd, position);
    userRepository.save(user);

    return new EmployeeResponse(user, null);
  }

  @Override
  public void deleteEmployee(Long id) {
    var foundUser = userRepository.findById(id);

    if (foundUser.isEmpty()) {
      throw new EntityNotFoundException("User not found");
    } else {
      userRepository.deleteById(id);
    }
  }

  public EmployeeDTO registerAdmin() {

    Position position = positionRepository.findById(1L).orElseThrow();

    var user =
        User.builder()
            .email("darioalessandrop@gmail.com")
            .position(position)
            .role(Role.ADMIN)
            .salary(100000L)
            .name("Dario")
            .active(true)
            .password(passwordEncoder.encode("4102002"))
            .build();

    userRepository.save(user);

    return new EmployeeDTO(user);
  }

  public boolean adminExists() {
    return userRepository.existsByEmail("darioalessandrop@gmail.com");
  }

  private static String generateRandomPassword() {
    String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    Random rnd = new Random();

    StringBuilder sb = new StringBuilder(8);
    for (int i = 0; i < 8; i++) {
      sb.append(AB.charAt(rnd.nextInt(AB.length())));
    }
    return sb.toString();
  }

  public List<String> findEmailsByRoles() {
    return userRepository.findEmailsByRoles(List.of(Role.ADMIN, Role.MANAGER));
  }
}
