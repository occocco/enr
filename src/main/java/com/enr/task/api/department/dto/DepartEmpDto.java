package com.enr.task.api.department.dto;

import com.enr.task.domain.Department;
import com.enr.task.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DepartEmpDto {

    private Integer employeeId;

    private String employeeName;

    private String phoneNumber;

    private LocalDate hireDate;

    private BigDecimal salary;

    private BigDecimal commissionPct;

    public static DepartEmpDto fromEntity(Employee employee) {
        return new DepartEmpDto(
                employee.getId(),
                employee.getEmployeeFullName(),
                employee.getPhoneNumber(),
                employee.getHireDate(),
                employee.getSalary(),
                employee.getCommissionPct()
        );
    }
}
