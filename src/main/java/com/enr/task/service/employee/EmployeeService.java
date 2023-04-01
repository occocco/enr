package com.enr.task.service.employee;

import com.enr.task.domain.Employee;
import com.enr.task.domain.JobHistory;
import com.enr.task.domain.view.EmpDetailsView;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployee();

    List<EmpDetailsView> findAllEmployeeDetails();

    Employee findEmployeeById(Integer id);

    EmpDetailsView findEmployeeDetailsById(Integer id);

    List<JobHistory> findJobHistories(Integer employeeId);

}
