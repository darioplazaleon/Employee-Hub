package com.example.employessytem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.employessytem.dto.vacation.VacationAdd;
import com.example.employessytem.dto.vacation.VacationDTO;
import com.example.employessytem.entity.User;
import com.example.employessytem.entity.VacationRequest;
import com.example.employessytem.entity.VacationStatus;
import com.example.employessytem.repository.UserRepository;
import com.example.employessytem.repository.VacationRepository;
import com.example.employessytem.service.impl.IEmailService;
import com.example.employessytem.service.impl.IJwtService;
import com.example.employessytem.service.impl.IVacationService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VacationServiceTest {
  @Mock private VacationRepository vacationRepository;

  @Mock private UserRepository userRepository;

  @Mock private IEmailService emailService;

  @Mock private IJwtService jwtService;

  @InjectMocks private IVacationService vacationService;

  private final Long employeeId = 1L;
  private final Long managerId = 2L;
  private User employee;
  private User manager;

  @BeforeEach
  public void setup() {
    employee = new User();
    employee.setId(employeeId);

    manager = new User();
    manager.setId(managerId);
  }

  @Test
  public void testRequestVacation_success() {
    String token = "dummyToken";
    VacationAdd vacationAdd =
        new VacationAdd(
            "Vacaciones de verano",
            LocalDate.of(2025, 6, 1),
            LocalDate.of(2025, 6, 15),
            "Disfrutar del verano");

    when(jwtService.getUserIdFromToken(token)).thenReturn(employeeId);
    when(userRepository.findById(employeeId)).thenReturn(Optional.of(employee));
    when(vacationRepository.save(any(VacationRequest.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    VacationDTO vacationDTO = vacationService.requestVacation(token, vacationAdd);

    assertNotNull(vacationDTO);
    assertEquals("Vacaciones de verano", vacationDTO.title());
    verify(vacationRepository, times(1)).save(any(VacationRequest.class));
    verify(emailService, times(1)).sendVacationsRequestEmails(employee);
  }

  @Test
  public void testRequestVacation_employeeNotFound() {
    String token = "dummyToken";
    VacationAdd vacationAdd =
        new VacationAdd(
            "Vacaciones de invierno",
            LocalDate.of(2025, 12, 1),
            LocalDate.of(2025, 12, 10),
            "Frío extremo");

    when(jwtService.getUserIdFromToken(token)).thenReturn(employeeId);
    when(userRepository.findById(employeeId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(
            RuntimeException.class, () -> vacationService.requestVacation(token, vacationAdd));
    assertEquals("Employee not found", exception.getMessage());
  }

  @Test
  public void testApproveVacation_success() {
    String token = "dummyToken";
    Long vacationId = 100L;

    VacationRequest vacation =
        VacationRequest.builder()
            .title("Vacaciones aprobadas")
            .employee(employee)
            .startDate(LocalDate.of(2025, 7, 1))
            .endDate(LocalDate.of(2025, 7, 10))
            .comment("Aprobación pendiente")
            .status(VacationStatus.PENDING)
            .build();
    vacation.setId(vacationId);

    when(vacationRepository.findById(vacationId)).thenReturn(Optional.of(vacation));
    when(jwtService.getUserIdFromToken(token)).thenReturn(managerId);
    when(userRepository.findById(managerId)).thenReturn(Optional.of(manager));
    when(vacationRepository.save(any(VacationRequest.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    VacationDTO result = vacationService.approveVacation(vacationId, token);

    assertNotNull(result);
    assertEquals(VacationStatus.APPROVED, vacation.getStatus());
    assertEquals(manager, vacation.getApprovedBy());
    verify(emailService, times(1)).sendVacationApprovedEmail(employee);
    verify(userRepository, times(1)).save(employee);
  }

  @Test
  public void testApproveVacation_vacationNotFound() {
    String token = "dummyToken";
    Long vacationId = 200L;
    when(vacationRepository.findById(vacationId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(
            RuntimeException.class, () -> vacationService.approveVacation(vacationId, token));
    assertEquals("Vacation request not found", exception.getMessage());
  }

  @Test
  public void testApproveVacation_managerNotFound() {
    String token = "dummyToken";
    Long vacationId = 300L;
    VacationRequest vacation = new VacationRequest();
    vacation.setEmployee(employee);

    when(vacationRepository.findById(vacationId)).thenReturn(Optional.of(vacation));
    when(jwtService.getUserIdFromToken(token)).thenReturn(managerId);
    when(userRepository.findById(managerId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(
            RuntimeException.class, () -> vacationService.approveVacation(vacationId, token));
    assertEquals("Manager not found", exception.getMessage());
  }

  @Test
  public void testRejectVacation_success() {
    String token = "dummyToken";
    Long vacationId = 400L;

    VacationRequest vacation =
        VacationRequest.builder()
            .title("Vacaciones rechazadas")
            .employee(employee)
            .startDate(LocalDate.of(2025, 8, 1))
            .endDate(LocalDate.of(2025, 8, 5))
            .comment("Solicitud a revisar")
            .status(VacationStatus.PENDING)
            .build();
    vacation.setId(vacationId);

    when(vacationRepository.findById(vacationId)).thenReturn(Optional.of(vacation));
    when(jwtService.getUserIdFromToken(token)).thenReturn(managerId);
    when(userRepository.findById(managerId)).thenReturn(Optional.of(manager));
    when(vacationRepository.save(any(VacationRequest.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    VacationDTO result = vacationService.rejectVacation(vacationId, token);

    assertNotNull(result);
    assertEquals(VacationStatus.REJECTED, vacation.getStatus());
    assertEquals(manager, vacation.getApprovedBy());
    verify(emailService, times(1)).sendVacationRejectedEmail(employee);
  }

  @Test
  public void testRejectVacation_vacationNotFound() {
    String token = "dummyToken";
    Long vacationId = 500L;
    when(vacationRepository.findById(vacationId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(
            RuntimeException.class, () -> vacationService.rejectVacation(vacationId, token));
    assertEquals("Vacation request not found", exception.getMessage());
  }

  @Test
  public void testRejectVacation_managerNotFound() {
    String token = "dummyToken";
    Long vacationId = 600L;
    VacationRequest vacation = new VacationRequest();
    vacation.setEmployee(employee);

    when(vacationRepository.findById(vacationId)).thenReturn(Optional.of(vacation));
    when(jwtService.getUserIdFromToken(token)).thenReturn(managerId);
    when(userRepository.findById(managerId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(
            RuntimeException.class, () -> vacationService.rejectVacation(vacationId, token));
    assertEquals("Manager not found", exception.getMessage());
  }

  @Test
  public void testGetVacationRequestsByEmployeeId() {
    VacationRequest vacation =
        VacationRequest.builder()
            .title("Vacaciones por empleado")
            .employee(employee)
            .startDate(LocalDate.of(2025, 9, 1))
            .endDate(LocalDate.of(2025, 9, 10))
            .comment("Comentario")
            .status(VacationStatus.PENDING)
            .build();
    when(vacationRepository.findAllByEmployeeId(employeeId))
        .thenReturn(java.util.List.of(vacation));

    List<VacationDTO> vacations = vacationService.getVacationRequests(employeeId);

    assertNotNull(vacations);
    assertEquals(1, vacations.size());
    assertEquals("Vacaciones por empleado", vacations.get(0).title());
  }

  @Test
  public void testGetAllVacationRequests() {
    VacationRequest vacation1 =
        VacationRequest.builder()
            .title("Vacación 1")
            .employee(employee)
            .startDate(LocalDate.of(2025, 10, 1))
            .endDate(LocalDate.of(2025, 10, 5))
            .comment("Comentario 1")
            .status(VacationStatus.PENDING)
            .build();

    VacationRequest vacation2 =
        VacationRequest.builder()
            .title("Vacación 2")
            .employee(manager) // se puede usar otro usuario para simular
            .startDate(LocalDate.of(2025, 11, 1))
            .endDate(LocalDate.of(2025, 11, 5))
            .comment("Comentario 2")
            .status(VacationStatus.PENDING)
            .build();

    when(vacationRepository.findAll()).thenReturn(List.of(vacation1, vacation2));

    List<VacationDTO> vacations = vacationService.getVacationRequests();

    assertNotNull(vacations);
    assertEquals(2, vacations.size());
  }

  @Test
  public void testGetVacationRequest_success() {
    Long vacationId = 700L;
    VacationRequest vacation =
        VacationRequest.builder()
            .title("Vacación individual")
            .employee(employee)
            .startDate(LocalDate.of(2025, 12, 1))
            .endDate(LocalDate.of(2025, 12, 10))
            .comment("Comentario")
            .status(VacationStatus.PENDING)
            .build();
    when(vacationRepository.findById(vacationId)).thenReturn(Optional.of(vacation));

    VacationDTO result = vacationService.getVacationRequest(vacationId);

    assertNotNull(result);
    assertEquals("Vacación individual", result.title());
  }

  @Test
  public void testGetVacationRequest_notFound() {
    Long vacationId = 800L;
    when(vacationRepository.findById(vacationId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(RuntimeException.class, () -> vacationService.getVacationRequest(vacationId));
    assertEquals("Vacation request not found", exception.getMessage());
  }
}
