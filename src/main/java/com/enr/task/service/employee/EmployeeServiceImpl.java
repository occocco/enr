package com.enr.task.service.employee;

import com.enr.task.domain.Employee;
import com.enr.task.domain.JobHistory;
import com.enr.task.domain.view.EmpDetailsView;
import com.enr.task.repository.employee.EmpDetailsViewRepository;
import com.enr.task.repository.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.enr.task.util.EntityUtils.assertListNotEmpty;
import static com.enr.task.util.EntityUtils.getEntityFromOpt;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmpDetailsViewRepository empDetailsViewRepository;

    @Override
    public List<Employee> findAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        assertListNotEmpty(employees, "사원 정보가 존재하지 않습니다.");
        return employees;
    }

    @Override
    public List<EmpDetailsView> findAllEmployeeDetails() {
        List<EmpDetailsView> employeeDetails = empDetailsViewRepository.findAll();
        assertListNotEmpty(employeeDetails, "사원 정보가 존재하지 않습니다.");
        return employeeDetails;
    }

    @Override
    public Employee findEmployeeById(Integer id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        return getEntityFromOpt(employeeOpt, "사원을 찾을 수 없습니다.");
    }

    @Override
    public EmpDetailsView findEmployeeDetailsById(Integer id) {
        Optional<EmpDetailsView> empDetailsViewOpt = empDetailsViewRepository.findById(id);
        return getEntityFromOpt(empDetailsViewOpt, "사원을 찾을 수 없습니다.");
    }

    @Override
    public List<JobHistory> findJobHistories(Integer id) {
        Optional<Employee> employeeOpt = employeeRepository.findByIdFetchJobHistories(id);
        Employee employee = getEntityFromOpt(employeeOpt, "사원을 찾을 수 없습니다.");
        List<JobHistory> jobHistories = employee.getJobHistories();
        assertListNotEmpty(jobHistories, "해당 사원의 이력이 존재하지 않습니다.");
        return jobHistories;
    }


}
