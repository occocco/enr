package com.enr.task.api.department;

import com.enr.task.api.department.dto.DepartEmpDto;
import com.enr.task.api.department.dto.DepartmentDto;
import com.enr.task.api.department.dto.SalaryIncreaseRequest;
import com.enr.task.domain.Department;
import com.enr.task.domain.Employee;
import com.enr.task.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.enr.task.api.department.dto.DepartmentDto.fromEntity;
import static com.enr.task.util.EntityUtils.convertEntityListToDtoList;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DepartmentRestController {

    private final DepartmentService departmentService;

    @GetMapping("/api/departments")
    public ResponseEntity<List<DepartmentDto>> findAllDepartment() {
        List<Department> departments = departmentService.findAllDepartment();
        List<DepartmentDto> departmentDtos = convertEntityListToDtoList(departments, DepartmentDto::fromEntity);
        return ResponseEntity.ok().body(departmentDtos);
    }

    @GetMapping("/api/departments/{department_id}")
    public ResponseEntity<DepartmentDto> findDepartment(@PathVariable("department_id") Integer id) {
        Department department = departmentService.findDepartmentById(id);
        DepartmentDto departmentDto = fromEntity(department);
        return ResponseEntity.ok().body(departmentDto);
    }

    // 부서에 속한 직원 조회
    @GetMapping("/api/departments/{department_id}/employees")
    public ResponseEntity<List<DepartEmpDto>> findEmployeesOfDepartment(@PathVariable("department_id") Integer id) {
        List<Employee> employees = departmentService.findEmployeesOfDepartment(id);
        List<DepartEmpDto> employeeDtos = convertEntityListToDtoList(employees, DepartEmpDto::fromEntity);
        return ResponseEntity.ok().body(employeeDtos);
    }

    /**
     * 부서에 속한 직원의 급여 인상 메서드
     * @return 변경된 사원의 정보를 리턴
     */
    @PatchMapping("/api/departments/{department_id}/employees/salary-increase")
    public ResponseEntity<List<DepartEmpDto>> increaseSalaryOfEmployeesByRate(@PathVariable("department_id") Integer id,
                                                                              @RequestBody SalaryIncreaseRequest request) {
        List<Employee> employees = departmentService.increaseSalaryOfDepartment(id, request.getIncreaseRate());
        List<DepartEmpDto> employeeDtos = convertEntityListToDtoList(employees, DepartEmpDto::fromEntity);
        return ResponseEntity.ok().body(employeeDtos);
    }
}
