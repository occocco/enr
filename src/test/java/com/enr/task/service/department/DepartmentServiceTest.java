package com.enr.task.service.department;

import com.enr.task.domain.Department;
import com.enr.task.domain.Employee;
import com.enr.task.domain.Job;
import com.enr.task.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;
    private DepartmentService departmentService;

    @BeforeEach
    public void init() {
        departmentService = new DepartmentServiceImpl(departmentRepository);
    }

    @Test
    void findAllDepartmentTest() {
        //given
        List<Department> departments = List.of(getDepartment(1, null));
        when(departmentRepository.findAll()).thenReturn(departments);
        //when
        List<Department> result = departmentService.findAllDepartment();
        //then
        assertEquals(departments, result);
    }

    @Test
    void findDepartmentByIdTest() {
        //given
        Department department = getDepartment(1, null);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        //when
        Department result = departmentService.findDepartmentById(department.getId());
        //then
        assertEquals(department.getName(), result.getName());
    }

    @Test
    void findEmployeesOfDepartmentTest() {
        //given
        Employee employeeA = getEmployee(1, new BigDecimal("1000.00"));
        Employee employeeB = getEmployee(2, new BigDecimal("1000.00"));
        List<Employee> employees = List.of(employeeA, employeeB);
        Department department = getDepartment(1, employees);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        //when
        List<Employee> result = departmentService.findEmployeesOfDepartment(department.getId());
        //then
        assertNotNull(result);
        assertEquals(employees, result);
    }

    @Test
    void increaseSalaryOfDepartmentTest() {
        //given
        BigDecimal increaseRate = new BigDecimal("1.1");
        Employee employeeA = getEmployee(1, new BigDecimal("2000.00"));
        BigDecimal employeeASalary = employeeA.getSalary();
        Employee employeeB = getEmployee(2, new BigDecimal("3000.00"));
        BigDecimal employeeBSalary = employeeB.getSalary();
        List<Employee> employees = List.of(employeeA, employeeB);
        Department department = getDepartment(1, employees);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        //when
        List<Employee> result = departmentService.increaseSalaryOfDepartment(department.getId(), increaseRate);
        //then
        BigDecimal expectedResult = employeeASalary.multiply(increaseRate).add(employeeBSalary.multiply(increaseRate));
        BigDecimal actualResult = result.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(expectedResult, actualResult);
    }

    private Employee getEmployee(Integer id, BigDecimal salary) {
        return new Employee(
                id,
                "TestA",
                "Doe",
                "TestA@example.com",
                null,
                LocalDate.of(2022, 3, 29),
                new Job("AC_MGR", "Accounting Manager", new BigDecimal("1000.00"), new BigDecimal("10000.00"), null),
                salary,
                null,
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    private Department getDepartment(Integer id, List<Employee> employees) {
        return new Department(id, "TestDepart", null, null, employees);
    }
}