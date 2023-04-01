package com.enr.task.api.employee;

import com.enr.task.api.employee.dto.EmpDetailsViewDto;
import com.enr.task.api.employee.dto.EmployeeDto;
import com.enr.task.api.employee.dto.JobHistoryDto;
import com.enr.task.domain.Employee;
import com.enr.task.domain.JobHistory;
import com.enr.task.domain.view.EmpDetailsView;
import com.enr.task.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.enr.task.util.EntityUtils.convertEntityListToDtoList;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeService employeeService;

    // 전체 사원 조회
    @GetMapping("/api/employees")
    public ResponseEntity<List<EmployeeDto>> findAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployee();
        List<EmployeeDto> employeeProfiles = convertEntityListToDtoList(employees, EmployeeDto::fromEntity);
        return ResponseEntity.ok().body(employeeProfiles);
    }
    
    // 전체 사원 상세조회, view 사용.
    @GetMapping("/api/employees/details")
    public ResponseEntity<List<EmpDetailsViewDto>> findAllEmpDetailsView() {
        List<EmpDetailsView> employees = employeeService.findAllEmployeeDetails();
        List<EmpDetailsViewDto> employeeProfiles = convertEntityListToDtoList(employees, EmpDetailsViewDto::fromEntity);
        return ResponseEntity.ok().body(employeeProfiles);
    }

    // 특정 사원 조회
    @GetMapping("/api/employees/{employee_id}")
    public ResponseEntity<EmployeeDto> findEmployee(@PathVariable("employee_id") Integer id) {
        Employee employeeOpt = employeeService.findEmployeeById(id);
        EmployeeDto employeeProfile = EmployeeDto.fromEntity(employeeOpt);
        return ResponseEntity.ok().body(employeeProfile);
    }

    // 특정 사원 상세조회, view 사용.
    @GetMapping("/api/employees/{employee_id}/details")
    public ResponseEntity<EmpDetailsViewDto> findEmployeeDetails(@PathVariable("employee_id") Integer id) {
        EmpDetailsView empDetailsView = employeeService.findEmployeeDetailsById(id);
        EmpDetailsViewDto empDetailsViewDto = EmpDetailsViewDto.fromEntity(empDetailsView);
        return ResponseEntity.ok().body(empDetailsViewDto);
    }

    // 특정 사원의 이력 조회
    @GetMapping("/api/employees/{employee_id}/jobHistories")
    public ResponseEntity<List<JobHistoryDto>> findJobHistoryByEmployeeId(@PathVariable("employee_id") Integer id) {
        List<JobHistory> jobHistories = employeeService.findJobHistories(id);
        List<JobHistoryDto> jobHistoryDtos = convertEntityListToDtoList(jobHistories, JobHistoryDto::fromEntity);
        return ResponseEntity.ok().body(jobHistoryDtos);
    }

}
