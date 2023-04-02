package com.enr.task.repository.employee;

import com.enr.task.domain.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @EntityGraph(attributePaths = {
            "job",
            "manager",
            "department.location",
            "department.location.country"})
    List<Employee> findAll();

    @EntityGraph(attributePaths = {
            "job",
            "manager",
            "department.location",
            "department.location.country"})
    Optional<Employee> findById(Integer id);

    /**
     * findById 메서드와 별개로 jobHistories 필드 조회 목적 및 최적화
     * findById에 fetch join을 추가해도 되지만 항상 jobHistories를 필요하지는 않기에 전용 메서드 추가.
     */
    @EntityGraph(attributePaths = {
            "job",
            "manager",
            "manager.job",
            "department.manager",
            "department.location",
            "department.location.country",
            "jobHistories.job",
            "jobHistories.department",
            "jobHistories.department.location"
    })
    @Query("SELECT e FROM Employee e WHERE e.id = :employeeId")
    Optional<Employee> findByIdFetchJobHistories(@Param("employeeId") Integer employeeId);

    @EntityGraph(attributePaths = {
            "job",
            "manager",
            "manager.job",
            "department",
            "department.location",
            "department.location.country",
            "department.location.country.region",
            "jobHistories"})
    Optional<Employee> findEmployeeDetailsById(Integer id);

    @EntityGraph(attributePaths = {
            "job",
            "manager",
            "manager.job",
            "department",
            "department.location",
            "department.location.country",
            "department.location.country.region",
            "jobHistories"})
    @Query("select e from Employee e")
    List<Employee> findAllEmployeeDetails();

}
