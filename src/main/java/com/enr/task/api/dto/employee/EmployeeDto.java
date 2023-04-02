package com.enr.task.api.dto.employee;

import com.enr.task.domain.Department;
import com.enr.task.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeDto {
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String jobId;
    private String jobTitle;
    private BigDecimal salary;
    private BigDecimal commissionPct;
    private Integer managerId;
    private String managerName;
    private String managerEmail;
    private String managerPhoneNum;
    private String departmentName;
    private String departmentLocation;

    public static EmployeeDto fromEntity(Employee employee) {
        Employee manager = employee.getManager();
        Department department = employee.getDepartment();

        return new EmployeeDto(
                employee.getId(),
                employee.getEmployeeFullName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getHireDate(),
                employee.getJob().getId(),
                employee.getJob().getTitle(),
                employee.getSalary(),
                employee.getCommissionPct() == null ? BigDecimal.ZERO : employee.getCommissionPct(),
                manager == null ? null : manager.getId(),
                manager == null ? null : manager.getFirstName() + " " + manager.getLastName(),
                manager == null ? null : manager.getEmail(),
                manager == null ? null : manager.getPhoneNumber(),
                department == null ? "현재 소속된 부서가 없습니다." : department.getName(),
                department == null ? "현재 소속된 부서가 없습니다." : department.getLocation().getFullAddress());
    }


}
