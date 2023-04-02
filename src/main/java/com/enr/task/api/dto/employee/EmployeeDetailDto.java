package com.enr.task.api.dto.employee;

import com.enr.task.api.dto.department.DepartmentDto;
import com.enr.task.api.dto.jobhistory.JobHistoryDto;
import com.enr.task.api.dto.location.LocationDto;
import com.enr.task.api.dto.region.RegionDto;
import com.enr.task.domain.Department;
import com.enr.task.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailDto {

    private EmployeeDto employee;
    private DepartmentDto department;
    private LocationDto location;
    private RegionDto region;
    private List<EmployeeDto> subordinates;
    private List<JobHistoryDto> jobHistories;

    public static EmployeeDetailDto fromEntity(Employee employee) {
        Department findDepartment = employee.getDepartment();
        return new EmployeeDetailDto(
                EmployeeDto.fromEntity(employee),
                findDepartment == null ? null : DepartmentDto.fromEntity(findDepartment),
                findDepartment == null ? null : LocationDto.fromEntity(employee.getDepartment().getLocation()),
                findDepartment == null ? null : RegionDto.fromEntity(employee.getDepartment().getLocation().getCountry().getRegion()),
                getEmployeeDtos(employee),
                getJobHistoryDtos(employee)
        );
    }

    private static List<EmployeeDto> getEmployeeDtos(Employee employee) {
        return employee.getSubordinates()
                .stream()
                .map(EmployeeDto::fromEntity)
                .collect(Collectors.toList());
    }

    private static List<JobHistoryDto> getJobHistoryDtos(Employee employee) {
        return employee.getJobHistories()
                .stream()
                .map(JobHistoryDto::fromEntity)
                .collect(Collectors.toList());
    }
}
