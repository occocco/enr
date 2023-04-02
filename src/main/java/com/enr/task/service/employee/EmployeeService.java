package com.enr.task.service.employee;

import com.enr.task.api.dto.employee.EmployeeUpdateDto;
import com.enr.task.domain.Employee;
import com.enr.task.domain.JobHistory;
import com.enr.task.domain.view.EmpDetailsView;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployee();

    List<EmpDetailsView> findAllEmployeeUsedView();

    Employee findEmployeeById(Integer id);

    EmpDetailsView findEmployeeUsedView(Integer id);

    List<JobHistory> findJobHistories(Integer employeeId);

    Employee updateEmployee(Integer id, EmployeeUpdateDto requestDto);


}
