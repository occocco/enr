package com.enr.task.api.dto.employee;

import com.enr.task.domain.view.EmpDetailsView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class EmpDetailsViewDto {

    private Integer employeeId;

    private String jobId;

    private Integer managerId;

    private Integer departmentId;

    private Integer locationId;

    private String countryId;

    private String firstName;

    private String lastName;

    private BigDecimal salary;

    private BigDecimal commissionPct;

    private String departmentName;

    private String jobTitle;

    private String city;

    private String stateProvince;

    private String countryName;

    private String regionName;

    public static EmpDetailsViewDto fromEntity(EmpDetailsView empDetailsView) {
        return new EmpDetailsViewDto(
                empDetailsView.getId().getEmployeeId(),
                empDetailsView.getId().getJobId(),
                empDetailsView.getManagerId(),
                empDetailsView.getDepartmentId(),
                empDetailsView.getLocationId(),
                empDetailsView.getCountryId(),
                empDetailsView.getFirstName(),
                empDetailsView.getLastName(),
                empDetailsView.getSalary(),
                empDetailsView.getCommissionPct(),
                empDetailsView.getDepartmentName(),
                empDetailsView.getJobTitle(),
                empDetailsView.getCity(),
                empDetailsView.getStateProvince(),
                empDetailsView.getCountryName(),
                empDetailsView.getRegionName()
        );
    }
}
