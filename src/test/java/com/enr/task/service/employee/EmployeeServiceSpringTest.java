package com.enr.task.service.employee;

import com.enr.task.domain.*;
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
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmployeeServiceSpringTest {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeService employeeService;
    private Employee employee;

    @BeforeEach
    public void init() {
        employee = new Employee(
                1,
                "TestA",
                "Doe",
                "TestA@example.com",
                null,
                LocalDate.of(2022, 3, 29),
                new Job("AC_MGR", "Accounting Manager", new BigDecimal("10000"), new BigDecimal("10000"), null),
                new BigDecimal("10000"),
                null,
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );

    }

    @Test
    void findAllEmployeeTest() {
        //given
        employeeRepository.save(employee);
        long totalCount = employeeRepository.count();
        //when
        List<Employee> allEmployee = employeeService.findAllEmployee();
        //then
        assertEquals(totalCount, allEmployee.size());
    }

    @Test
    void findAllEmployeeTest_WhenListIsEmpty() {
        //given
        employeeRepository.save(employee);
        employee.disconnectDepartment();
        employeeRepository.deleteAll();
        //when
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> employeeService.findAllEmployee());
        //then
        assertEquals(e.getMessage(), "사원 정보가 존재하지 않습니다.");
    }

    @Test
    void findEmployeeByIdTest() {
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        //when
        Employee findEmployee = employeeService.findEmployeeById(savedEmployee.getId());
        //then
        assertNotNull(findEmployee);
        assertEquals(savedEmployee, findEmployee);
        assertEquals(savedEmployee.getEmail(), findEmployee.getEmail());
    }

    @Test
    void findEmployeeTest_WhenNotFound() {
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        employee.disconnectDepartment();
        employeeRepository.deleteAll();
        //when
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> employeeService.findEmployeeById(savedEmployee.getId()));
        //then
        assertEquals(e.getMessage(), "사원을 찾을 수 없습니다.");
    }

    @Test
    void findJobHistories() {
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        JobHistory jobHistory = new JobHistory(savedEmployee,
                LocalDate.of(2000, 3, 19),
                LocalDate.of(2002, 3, 19),
                new Job("AC_MGR", "Accounting Manager", new BigDecimal("8200"), new BigDecimal("16000"), null),
                new Department(270, "Payroll", null, null, null)
        );
        savedEmployee.addJobHistory(jobHistory);
        //when
        List<JobHistory> jobHistories = employeeService.findJobHistories(savedEmployee.getId());
        //then
        assertNotNull(jobHistories);
        assertEquals(savedEmployee.getJobHistories(), jobHistories);
    }

    @Test
    void findJobHistoriesTest_WhenNoHistories() {
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        JobHistory jobHistory = new JobHistory(savedEmployee,
                LocalDate.of(2000, 3, 19),
                LocalDate.of(2002, 3, 19),
                new Job("AC_MGR", "Accounting Manager", new BigDecimal("8200"), new BigDecimal("16000"), null),
                new Department(270, "Payroll", null, null, null)
        );
        savedEmployee.addJobHistory(jobHistory);
        savedEmployee.removeJobHistory();
        //when
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> employeeService.findJobHistories(savedEmployee.getId()));
        //then
        assertEquals(e.getMessage(), "해당 사원의 이력이 존재하지 않습니다.");

    }
}