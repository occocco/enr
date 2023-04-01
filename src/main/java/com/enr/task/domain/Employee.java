package com.enr.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"subordinates", "jobHistories"})
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "last_name", length = 25, nullable = false)
    private String lastName;

    @Column(name = "email", length = 25, nullable = false)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "hire_date", columnDefinition = "date", nullable = false)
    private LocalDate hireDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "salary", columnDefinition = "decimal(8,2)", nullable = false)
    private BigDecimal salary;

    @Column(name = "commission_pct", columnDefinition = "decimal(2,2)")
    private BigDecimal commissionPct;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = ALL)
    private List<JobHistory> jobHistories = new ArrayList<>();

    // 급여 인상 메서드
    public void increaseSalary(BigDecimal increaseRate) {
        if (increaseRate.compareTo(BigDecimal.ONE) < 0) {
            throw new IllegalArgumentException("인상 값은 1 이상이어야 합니다.");
        }
        this.salary = salary.multiply(increaseRate);
    }

    // 사원과 부서의 연관관계를 끊는 메서드
    public void disconnectDepartment() {
        if (department != null) {
            if (department.getManager() != null) {
                department.disconnectManager();
            }
        }
    }

    // Employee에 Jobhistory를 추가하는 메서드.
    public void addJobHistory(JobHistory... jobHistories) {
        Collections.addAll(this.jobHistories, jobHistories);
    }

    public void removeJobHistory() {
        jobHistories.clear();
    }

    public void connectDepartment(Department department) {
        this.department = department;
    }

    public String getEmployeeFullName() {
        return firstName + " " + lastName;
    }

}
