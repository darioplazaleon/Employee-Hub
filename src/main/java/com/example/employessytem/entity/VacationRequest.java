package com.example.employessytem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "vacation_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Column(name = "request_date", nullable = false)
  private LocalDate requestDate;

  private LocalDate startDate;
  private LocalDate endDate;
  private String comment;

  @Enumerated(EnumType.STRING)
  private VacationStatus status;

  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private User employee;

  @ManyToOne
  @JoinColumn(name = "approved_by", nullable = true)
  private User approvedBy;

  @Override
  public String toString() {
    return "VacationRequest{id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + "}";
  }

  @PrePersist
  protected void onCreate() {
    this.requestDate = LocalDate.now();
  }
}
