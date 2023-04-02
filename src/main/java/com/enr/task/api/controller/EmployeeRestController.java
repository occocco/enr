package com.enr.task.api.controller;

import com.enr.task.api.dto.employee.*;
import com.enr.task.api.dto.jobhistory.JobHistoryDto;
import com.enr.task.domain.Employee;
import com.enr.task.domain.JobHistory;
import com.enr.task.domain.view.EmpDetailsView;
import com.enr.task.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.enr.task.util.EntityUtils.convertEntityListToDtoList;

/**
 * 사원 조회는 DB에 있는 view로 조회.
 * 사원 상세조회는 부하직원과 잡히스토리 모두 조회.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeService employeeService;
    
    // 전체 사원 조회, view 사용.
    @GetMapping("/api/employees")
    public ResponseEntity<List<EmpDetailsViewDto>> findAllEmployee() {
        List<EmpDetailsView> employees = employeeService.findAllEmployeeUsedView();
        List<EmpDetailsViewDto> employeeProfiles = convertEntityListToDtoList(employees, EmpDetailsViewDto::fromEntity);
        return ResponseEntity.ok().body(employeeProfiles);
    }

    // 전체 사원 상세 조회
    @GetMapping("/api/employees/details")
    public ResponseEntity<List<EmployeeDetailDto>> findAllEmployeeDetails() {
        List<Employee> employees = employeeService.findAllEmployee();
        List<EmployeeDetailDto> employeeDetails = convertEntityListToDtoList(employees, EmployeeDetailDto::fromEntity);
        return ResponseEntity.ok().body(employeeDetails);
    }

    // 사원 정보 업데이트
   @PatchMapping("/api/employees/{employee_id}")
    public ResponseEntity<EmployeeUpdateDto> updateEmployee(@PathVariable("employee_id") Integer id,
                                                            @RequestBody EmployeeUpdateDto requestDto) {
        Employee employee = employeeService.updateEmployee(id, requestDto);
        EmployeeUpdateDto employeeUpdateDto = EmployeeUpdateDto.fromEntity(employee);
        return ResponseEntity.ok().body(employeeUpdateDto);
    }

    // 특정 사원 조회, view 사용.
    @GetMapping("/api/employees/{employee_id}")
    public ResponseEntity<EmpDetailsViewDto> findEmployee(@PathVariable("employee_id") Integer id) {
        EmpDetailsView empDetailsView = employeeService.findEmployeeUsedView(id);
        EmpDetailsViewDto empDetailsViewDto = EmpDetailsViewDto.fromEntity(empDetailsView);
        return ResponseEntity.ok().body(empDetailsViewDto);
    }

    // 특정 사원 상세 조회
    @GetMapping("/api/employees/{employee_id}/details")
    public ResponseEntity<EmployeeDetailDto> findEmployeeDetails(@PathVariable("employee_id") Integer id) {
//        Employee employee = employeeService.findEmployeeById(id);
        Employee employee = employeeService.findEmployeeById(id);
        EmployeeDetailDto employeeDetails = EmployeeDetailDto.fromEntity(employee);
        return ResponseEntity.ok().body(employeeDetails);
    }

    // 특정 사원의 이력 조회
    @GetMapping("/api/employees/{employee_id}/jobHistories")
    public ResponseEntity<List<JobHistoryDto>> findJobHistoryByEmployeeId(@PathVariable("employee_id") Integer id) {
        List<JobHistory> jobHistories = employeeService.findJobHistories(id);
        List<JobHistoryDto> jobHistoryDtos = convertEntityListToDtoList(jobHistories, JobHistoryDto::fromEntity);
        return ResponseEntity.ok().body(jobHistoryDtos);
    }

}
