package com.enr.task.api.dto.department;

import com.enr.task.domain.Department;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentDto {

    private Integer departmentId;

    private String departmentName;

    private Integer managerId;

    private String managerName;

    private String managerEmail;

    private String managerPhoneNum;

    private Integer locationId;

    private String location;

    private String countryId;

    public static DepartmentDto fromEntity(Department department) {
        return new DepartmentDto(
                department.getId(),
                department.getName(),
                department.getManager() == null ? null : department.getManager().getId(),
                department.getManager() == null ? null : department.getManager().getEmployeeFullName(),
                department.getManager() == null ? null : department.getManager().getEmail(),
                department.getManager() == null ? null : department.getManager().getPhoneNumber(),
                department.getLocation().getId(),
                department.getLocation().getFullAddress(),
                department.getLocation().getCountry().getId()
        );
    }
}
