package com.enr.task.service.employee;

import com.enr.task.api.dto.employee.EmployeeUpdateDto;
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

    @Test
    void updateEmployeeTest() {
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeUpdateDto employeeUpdateDto =
                getEmployeeUpdateDto(
                        "JUN",
                        "test@test.com",
                        new BigDecimal("1000.00"),
                        new BigDecimal("0.2"));
        //when
        Employee updatedEmployee = employeeService.updateEmployee(savedEmployee.getId(), employeeUpdateDto);
        //then
        assertEquals(employeeUpdateDto.getLastName(), updatedEmployee.getLastName());
        assertEquals(employeeUpdateDto.getEmail(), updatedEmployee.getEmail());
        assertEquals(employeeUpdateDto.getSalary(), updatedEmployee.getSalary());
        assertEquals(employeeUpdateDto.getCommissionPct(), updatedEmployee.getCommissionPct());
    }

    // NOT NULL 컬럼 예외 테스트
    @Test
    void updateEmployeeTest_RequiredValidation() {
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeUpdateDto updateDtoLastNameIdBlank = getEmployeeUpdateDto(
                "",
                "test@test.com",
                new BigDecimal("1000.00"),
                new BigDecimal("0.2"));
        EmployeeUpdateDto updateDtoSalaryIsNull = getEmployeeUpdateDto(
                "",
                "test@test.com",
                new BigDecimal("1000.00"),
                new BigDecimal("0.2"));
        //when
        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployee(savedEmployee.getId(), updateDtoLastNameIdBlank));
        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployee(savedEmployee.getId(), updateDtoSalaryIsNull));
    }

    // 범위에서 벗어난 커미션 비율, 예외 테스트
    @Test
    void updateEmployeeTest_CommissionPctOverRange() {
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        BigDecimal requestCommissionPct = new BigDecimal("1.1");
        EmployeeUpdateDto updateDto = getEmployeeUpdateDto(
                "Jae",
                "test@test.com",
                new BigDecimal("1000.00"),
                requestCommissionPct);
        //when
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> employeeService.updateEmployee(savedEmployee.getId(), updateDto));
        //then
        assertEquals(ex.getMessage(), "커미션 비율은 0 이상 1 이하여야 합니다.");
    }
    // 음수 값의 급여 예외 테스트
    @Test
    void updateEmployeeTest_RequestNegativeSalary(){
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        BigDecimal requestSalary = new BigDecimal("-1000.00");
        EmployeeUpdateDto updateDto = getEmployeeUpdateDto(
                "Jae",
                "test@test.com",
                requestSalary,
                new BigDecimal("1.1"));
        //when
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> employeeService.updateEmployee(savedEmployee.getId(), updateDto));
        //then
        assertEquals(ex.getMessage(), "급여는 음수일 수 없습니다.");
    } 

    private EmployeeUpdateDto getEmployeeUpdateDto(String lastName, String email, BigDecimal salary, BigDecimal commissionPct) {
        return new EmployeeUpdateDto(
                "KIM",
                lastName,
                email,
                "101.234.1234",
                salary,
                commissionPct);
    }

}