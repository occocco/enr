package com.enr.task.service.department;

import com.enr.task.domain.Department;
import com.enr.task.domain.Employee;
import com.enr.task.domain.Job;
import com.enr.task.repository.DepartmentRepository;
import com.enr.task.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class DepartmentServiceSpringTest {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void init() {
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            department.disconnectManager();
        }
        departmentRepository.deleteAll();
    }

    @Test
    void findAllDepartmentTest() {
        //given
        Department departmentA = getDepartment(1, null);
        Department departmentB = getDepartment(2, null);
        List<Department> departments = departmentRepository.saveAll(List.of(departmentA, departmentB));
        //when
        List<Department> result = departmentRepository.findAll();
        //then
        assertEquals(departments, result);
        assertEquals(departments.size(), result.size());
    }

    @Test
    void findDepartmentByIdTest() {
        //given
        int id = 1;
        Department department = getDepartment(id, null);
        Department savedDepartment = departmentRepository.save(department);
        //when
        Department result = departmentService.findDepartmentById(id);
        //then
        assertEquals(savedDepartment, result);
        assertEquals(savedDepartment.getName(), result.getName());
    }

    @Test
    void findEmployeesOfDepartmentTest() {
        //given
        Employee employeeA = getEmployee(1, new BigDecimal("1000.00"));
        Employee employeeB = getEmployee(2, new BigDecimal("2000.00"));
        Employee savedEmployeeA = employeeRepository.save(employeeA);
        Employee savedEmployeeB = employeeRepository.save(employeeB);
        Department department = getDepartment(1, List.of(employeeA, employeeB));
        Department savedDepartment = departmentRepository.save(department);
        savedEmployeeA.connectDepartment(savedDepartment);
        savedEmployeeB.connectDepartment(savedDepartment);
        //when
        List<Employee> result = departmentService.findEmployeesOfDepartment(savedDepartment.getId());
        //then
        assertEquals(2, result.size());
        assertEquals(employeeA.getId(), result.get(0).getId());
        assertEquals(savedDepartment, result.get(0).getDepartment());
        assertEquals(employeeB.getId(), result.get(1).getId());
        assertEquals(employeeA.getSalary(), result.get(0).getSalary());
        assertEquals(employeeB.getSalary(), result.get(1).getSalary());
    }

    @Test
    void increaseSalaryOfDepartmentTest() {
        //given
        BigDecimal salaryEmployeeA = new BigDecimal("1000.00");
        BigDecimal salaryEmployeeB = new BigDecimal("2000.00");
        Employee employeeA = getEmployee(1, salaryEmployeeA);
        Employee employeeB = getEmployee(2, salaryEmployeeB);
        Employee savedEmployeeA = employeeRepository.save(employeeA);
        Employee savedEmployeeB = employeeRepository.save(employeeB);
        Department department = getDepartment(1, List.of(employeeA, employeeB));
        Department savedDepartment = departmentRepository.save(department);
        savedEmployeeA.connectDepartment(savedDepartment);
        savedEmployeeB.connectDepartment(savedDepartment);
        BigDecimal increaseRate = new BigDecimal("1.1");
        //when
        List<Employee> result = departmentService.increaseSalaryOfDepartment(department.getId(), increaseRate);
        //then
        BigDecimal expectedResult = salaryEmployeeA.multiply(increaseRate).add(salaryEmployeeB.multiply(increaseRate));
        BigDecimal actualResult = result.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(expectedResult, actualResult);
        assertEquals(new BigDecimal("1100.000"), salaryEmployeeA.multiply(increaseRate));
        assertEquals(new BigDecimal("2200.000"), salaryEmployeeB.multiply(increaseRate));
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