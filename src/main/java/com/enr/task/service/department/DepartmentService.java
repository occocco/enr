package com.enr.task.service.department;

import com.enr.task.domain.Department;
import com.enr.task.domain.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface DepartmentService {

    List<Department> findAllDepartment();

    Department findDepartmentById(Integer departmentId);

    List<Employee> findEmployeesOfDepartment(Integer departmentId);

    List<Employee> increaseSalaryOfDepartment(Integer departmentId, BigDecimal increaseRate);

}
