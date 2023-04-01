package com.enr.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "department_id")
    private Integer id;

    @Column(name = "department_name", length = 30, nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    // 회원과의 연관관계를 끊기 위한 메서드
    public void disconnectManager() {
        this.manager = null;
    }
}