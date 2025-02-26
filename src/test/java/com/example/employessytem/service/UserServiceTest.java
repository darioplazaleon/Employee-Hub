package com.example.employessytem.service;

import com.example.employessytem.dto.employee.EmployeeAdd;
import com.example.employessytem.dto.employee.EmployeeListDTO;
import com.example.employessytem.dto.employee.EmployeeResponse;
import com.example.employessytem.entity.Position;
import com.example.employessytem.entity.Role;
import com.example.employessytem.entity.User;
import com.example.employessytem.repository.PositionRepository;
import com.example.employessytem.repository.UserRepository;
import com.example.employessytem.service.impl.IEmailService;
import com.example.employessytem.service.impl.IUserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IEmailService emailService;

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private IUserService userService;

    private EmployeeAdd employeeAdd;
    private Position position;
    private User user;

    @BeforeEach
    void setUp() {
        position = new Position();
        position.setId(1L);
        position.setName("Developer");

        employeeAdd = new EmployeeAdd("Test User", "test@example.com", 1L, 50000L, Role.USER);

        user = User.builder()
                .email(employeeAdd.email())
                .position(position)
                .name(employeeAdd.name())
                .salary(employeeAdd.salary())
                .password("encodedPassword")
                .active(true)
                .build();
    }

    @Test
    void registerEmployee_Success() throws MessagingException {
        when(userRepository.existsByEmail(employeeAdd.email())).thenReturn(false);
        when(positionRepository.findById(employeeAdd.positionId())).thenReturn(Optional.of(position));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        EmployeeResponse response = userService.registerEmployee(employeeAdd);

        assertNotNull(response);
        assertEquals("test@example.com", response.email());
        verify(emailService, times(1)).sendCredentialsEmail(anyString(), anyString(), anyString());
    }

    @Test
    void registerEmployee_FailsWhenEmailExists() {
        when(userRepository.existsByEmail(employeeAdd.email())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerEmployee(employeeAdd);
        });

        assertEquals("User with this email already exists", exception.getMessage());
    }

    @Test
    void getEmployee_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertNotNull(userService.getEmployee(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployee_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getEmployee(1L);
        });
    }

    @Test
    void getAllEmployees_Success() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<User> users = List.of(user);
        Page<User> userPage = new PageImpl<>(users, pageRequest, users.size());

        when(userRepository.findAll(pageRequest)).thenReturn(userPage);

        Page<EmployeeListDTO> result = userService.getAllEmployees(pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void updateEmployee_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(positionRepository.findById(employeeAdd.positionId())).thenReturn(Optional.of(position));
        when(userRepository.save(any(User.class))).thenReturn(user);

        EmployeeResponse response = userService.updateEmployee(1L, employeeAdd);

        assertNotNull(response);
        assertEquals("test@example.com", response.email());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateEmployee_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.updateEmployee(1L, employeeAdd);
        });
    }

    @Test
    void deleteEmployee_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteEmployee(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEmployee_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.deleteEmployee(1L);
        });
    }


}
