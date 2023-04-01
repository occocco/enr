package com.enr.task.service.employee;

import com.enr.task.domain.Employee;
import com.enr.task.domain.Job;
import com.enr.task.domain.JobHistory;
import com.enr.task.repository.employee.EmpDetailsViewRepository;
import com.enr.task.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmpDetailsViewRepository empDetailsViewRepository;

    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    void init() {
        employeeService = new EmployeeServiceImpl(employeeRepository, empDetailsViewRepository);
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
        // given
        List<Employee> expectedEmployees = List.of(
                employee, employee
        );

        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        // when
        List<Employee> employees = employeeService.findAllEmployee();

        // then
        assertThat(employees).isEqualTo(expectedEmployees);
        assertThat(employees.size()).isEqualTo(expectedEmployees.size());
    }

    @Test
    void findAllEmployeeTest_WhenListIsEmpty() {

        //given
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> employeeService.findAllEmployee());
        String exMessage = e.getMessage();

        //then
        assertThat(exMessage).isEqualTo("사원 정보가 존재하지 않습니다.");

    }

    @Test
    void findEmployeeTest() {

        //given
        int id = 1;
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        //when
        Employee result = employeeService.findEmployeeById(id);

        //then
        assertEquals(employee, result);
        verify(employeeRepository, times(1)).findById(id);

    }

    @Test
    void findEmployeeTest_WhenNotFound() {

        //given
        int id = 1;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        //when
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> employeeService.findEmployeeById(id));

        //then
        String exMessage = e.getMessage();
        assertThat(exMessage).isEqualTo("사원을 찾을 수 없습니다.");

    }

    @Test
    void findJobHistoriesTest() {

        //given
        int id = 1;
        JobHistory jobHistory = new JobHistory(null, null, LocalDate.of(1999, 11, 11), null, null);
        employee.addJobHistory(jobHistory);
        when(employeeRepository.findByIdFetchJobHistories(1)).thenReturn(Optional.of(employee));

        //when
        List<JobHistory> result = employeeService.findJobHistories(id);

        //then
        assertEquals(employee.getJobHistories().size(), 1);
        assertEquals(employee.getJobHistories().size(), result.size());
        assertEquals(employee.getJobHistories(), result);

    }

    @Test
    void findJobHistoriesTest_WhenNoHistories() {

        //given
        int id = 1;
        when(employeeRepository.findByIdFetchJobHistories(1)).thenReturn(Optional.of(employee));

        //when
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> employeeService.findJobHistories(id));

        //then
        String exMessage = e.getMessage();
        assertThat(exMessage).isEqualTo("해당 사원의 이력이 존재하지 않습니다.");

    }
}
