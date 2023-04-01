package com.enr.task.service.department;

import com.enr.task.domain.Department;
import com.enr.task.domain.Employee;
import com.enr.task.repository.DepartmentRepository;
import com.enr.task.util.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.enr.task.util.EntityUtils.getEntityFromOpt;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAllDepartment() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findDepartmentById(Integer departmentId) {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId);
        return getEntityFromOpt(departmentOpt, "부서 정보를 찾을 수 없습니다.");
    }

    @Override
    public List<Employee> findEmployeesOfDepartment(Integer departmentId) {
        List<Employee> employees = findDepartmentById(departmentId).getEmployees();
        EntityUtils.assertListNotEmpty(employees, "해당 부서에는 사원이 존재하지 않습니다.");
        return employees;
    }

    @Override
    @Transactional
    public List<Employee> increaseSalaryOfDepartment(Integer departmentId, BigDecimal increaseRate) {
        List<Employee> employees = findEmployeesOfDepartment(departmentId);
        employees.forEach(employee -> employee.increaseSalary(increaseRate));
        return employees;
    }

}
