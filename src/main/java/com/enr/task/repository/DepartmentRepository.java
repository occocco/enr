package com.enr.task.repository;

import com.enr.task.domain.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @EntityGraph(attributePaths = {"location", "location.country", "manager"})
    List<Department> findAll();

    @EntityGraph(attributePaths = {"location", "location.country", "manager", "employees"})
    Optional<Department> findById(Integer id);

}
