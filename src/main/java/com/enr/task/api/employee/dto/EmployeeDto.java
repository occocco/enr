package com.enr.task.api.employee.dto;

import com.enr.task.domain.Department;
import com.enr.task.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeDto {

    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String jobTitle;
    private BigDecimal salary;
    private BigDecimal commissionPct;
    private String managerName;
    private String managerEmail;
    private String managerPhoneNum;
    private String departmentName;
    private String departmentLocation;

    // 클라이언트가 단순 조회만을 한다고 상정하여 작성.
    public static EmployeeDto fromEntity(Employee employee) {
        Employee manager = employee.getManager();
        Department department = employee.getDepartment();

        return new EmployeeDto(
                employee.getEmployeeFullName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getHireDate(),
                employee.getJob().getTitle(),
                employee.getSalary(),
                employee.getCommissionPct() == null ? BigDecimal.ZERO : employee.getCommissionPct(),
                manager == null ? "매니저가 없습니다." : manager.getFirstName() + " " + manager.getLastName(),
                manager == null ? "" : manager.getEmail(),
                manager == null ? "" : manager.getPhoneNumber(),
                department == null ? "현재 소속된 부서가 없습니다." : department.getName(),
                department == null ? "현재 소속된 부서가 없습니다." : department.getLocation().getFullAddress());
    }


}
