package com.enr.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Table(name = "jobs")
public class Job {

    @Id
    @Column(name = "job_id", length = 10)
    private String id;

    @Column(name = "job_title", length = 35, nullable = false)
    private String title;

    @Column(name = "min_salary", columnDefinition = "decimal(8)")
    private BigDecimal minSalary;

    @Column(name = "max_salary", columnDefinition = "decimal(8)")
    private BigDecimal maxSalary;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

}
