package com.enr.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@IdClass(JobHistoryId.class)
@Table(name = "job_history")
public class JobHistory {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @Column(name = "start_date", columnDefinition = "date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public void disconnectEmployee() {
        this.employee = null;
    }
}

