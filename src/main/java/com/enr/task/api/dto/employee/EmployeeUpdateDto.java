package com.enr.task.api.dto.employee;

import com.enr.task.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private BigDecimal salary;
    private BigDecimal commissionPct;

    public static EmployeeUpdateDto fromEntity(Employee employee) {
        return new EmployeeUpdateDto(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getSalary(),
                employee.getCommissionPct()
        );
    }
}
